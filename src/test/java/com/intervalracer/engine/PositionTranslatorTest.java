package com.intervalracer.engine;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.RaceCar;
import com.intervalracer.model.impl.PlayerImpl;
import com.intervalracer.model.impl.RaceCarImpl;
import com.intervalracer.tracks.SimpleTrack;

public class PositionTranslatorTest {

	private PositionTranslator translator;

	private Player player;

	private int carX = 100;

	private int carY = 300;

	@Before
	public void setUp() {
		player = new PlayerImpl("Toad", new RaceCarImpl(1, new Point(carX, carY), CarColor.ORANGE));
		translator = new PositionTranslator(new SimpleTrack());
	}

	@Test
	public void translateNothingWhenCarHasNoSpeed() {
		RaceCar car = player.getCar();
		car.turnLeft();
		;
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY));
	}

	@Test
	public void translateCarHalfSpeedStraighAhead() {
		RaceCar car = player.getCar();
		car.increaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY - 30));
	}

	@Test
	public void translateCarFullSpeedStraighAhead() {
		RaceCar car = player.getCar();
		car.increaseSpeed();
		car.increaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY - 60));
	}

	@Test
	public void translateCarFullSpeedReverse() {
		RaceCar car = player.getCar();
		car.decreaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY + 30));
	}

	@Test
	public void translateCarFullSpeedLeft() {
		RaceCar car = player.getCar();
		car.increaseSpeed();
		car.increaseSpeed();
		car.turnLeft();
		translator.translatePlayerRaceCarPosition(player);

		// 29^2 + 51^2 = speed^2 --> speed = 58.7 (should be 60, Rounding
		// differences accepted)
		assertEquals(car.getPosition(), new Point(71, 249));
	}

	@Test
	public void translateCarHalfSpeedRight() {
		RaceCar car = player.getCar();
		car.increaseSpeed();
		car.turnRight();
		translator.translatePlayerRaceCarPosition(player);

		// 14^2 + 25^2 = speed^2 --> speed = 28.7 (should be 30, Rounding
		// differences accepted)
		assertEquals(car.getPosition(), new Point(114, 275));
	}

	@Test
	public void carSlowsDownOnGrass() {
		RaceCar car = player.getCar();
		car.setCarState(CarState.ON_GRASS);
		car.increaseSpeed();
		car.increaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY - 30));
	}

	@Test
	public void carDrivesFullSpeedIntoGrassThenSlowsDownAndThenCrashes() {
		RaceCar car = player.getCar();
		car.resetToPosition(new Point(60, 140));
		car.increaseSpeed();
		car.increaseSpeed();

		assertEquals(car.getCarState(), CarState.ON_ROAD);
		translator.translatePlayerRaceCarPosition(player);
		assertEquals(car.getPosition(), new Point(60, 140 - 60));
		assertEquals(car.getCarState(), CarState.ON_GRASS);
		car.decreaseSpeed();
		translator.translatePlayerRaceCarPosition(player);
		assertEquals(car.getPosition(), new Point(60, 140 - 90));
		assertEquals(car.getCarState(), CarState.CRASHED);
	}

	public void carCrashesInWall() {

	}

}
