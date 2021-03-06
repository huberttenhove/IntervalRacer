package com.intervalracer.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

		assertEquals(car.getPosition(), new Point(carX, carY - RaceCarImpl.SPEED_STEP));
	}

	@Test
	public void translateCarFullSpeedStraighAhead() {
		RaceCar car = player.getCar();
		car.increaseSpeed();
		car.increaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY - (RaceCarImpl.SPEED_STEP * 2)));
	}

	@Test
	public void translateCarFullSpeedReverse() {
		RaceCar car = player.getCar();
		car.decreaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY + RaceCarImpl.SPEED_STEP));
	}

	@Test
	public void translateCarFullSpeedLeft() {
		RaceCar car = player.getCar();
		Point startingPosition = new Point(car.getPosition().x, car.getPosition().y);
		car.increaseSpeed();
		car.increaseSpeed();
		car.turnLeft();
		translator.translatePlayerRaceCarPosition(player);

		// Check if the calculated speed is the same as the speed of the car,
		// Rounding differences of 15% accepted
		int carSpeed = car.getSpeed();
		double calcSpeed = calculatedSpeed(startingPosition, player.getCar().getPosition());
		assertTrue((carSpeed / calcSpeed) % 1 < 0.15);
	}

	@Test
	public void translateCarHalfSpeedRight() {
		RaceCar car = player.getCar();
		Point startingPosition = new Point(car.getPosition().x, car.getPosition().y);
		car.increaseSpeed();
		car.turnRight();
		translator.translatePlayerRaceCarPosition(player);

		// Check if the calculated speed is the same as the speed of the car,
		// Rounding differences of 15% accepted
		int carSpeed = car.getSpeed();
		double calcSpeed = calculatedSpeed(startingPosition, player.getCar().getPosition());
		assertTrue((carSpeed / calcSpeed) % 1 < 0.15);
	}

	// Based on the translation of the car calculate the speed.
	// Using: x^2 + y^2 = speed^2
	private double calculatedSpeed(Point oldPosition, Point newPosition) {

		int x = oldPosition.x - newPosition.x;
		int y = oldPosition.y - newPosition.y;

		double speed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return Math.abs(speed);
	}

	@Test
	public void carSlowsDownOnGrass() {
		RaceCar car = player.getCar();
		car.setCarState(CarState.ON_GRASS);
		car.increaseSpeed();
		car.increaseSpeed();
		translator.translatePlayerRaceCarPosition(player);

		assertEquals(car.getPosition(), new Point(carX, carY - RaceCarImpl.MAX_SPEED_ON_GRASS));
	}

	@Test
	public void carDrivesFullSpeedIntoGrassThenSlowsDownAndThenCrashes() {
		RaceCar car = player.getCar();
		car.resetToPosition(new Point(60, 100));
		car.increaseSpeed();
		car.increaseSpeed();

		assertEquals(car.getCarState(), CarState.ON_ROAD);
		translator.translatePlayerRaceCarPosition(player);
		assertEquals(car.getPosition(), new Point(60, 100 - (RaceCarImpl.SPEED_STEP * 2)));
		assertEquals(car.getCarState(), CarState.ON_GRASS);
		car.decreaseSpeed();
		translator.translatePlayerRaceCarPosition(player);
		assertEquals(car.getPosition(), new Point(60, 100 - (RaceCarImpl.SPEED_STEP * 3)));
		assertEquals(car.getCarState(), CarState.CRASHED);
	}

	public void carCrashesInWall() {

	}

}
