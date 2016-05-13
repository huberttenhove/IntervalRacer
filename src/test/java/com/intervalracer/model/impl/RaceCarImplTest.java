package com.intervalracer.model.impl;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.CarState;
import com.intervalracer.model.RaceCar;

public class RaceCarImplTest {

	private RaceCar raceCar;

	@Before
	public void setUp() {
		raceCar = new RaceCarImpl(1, new Point(100, 100), CarColor.WHITE);
		raceCar.setCarState(CarState.ON_ROAD);
	}

	@Test
	public void turnLeftChangesDirection() {
		raceCar.turnLeft();
		assertEquals(0 - RaceCarImpl.DIRECTION_CHANGE, raceCar.getDirection());
	}

	@Test
	public void turnRightChangesDirection() {
		raceCar.turnRight();
		assertEquals(0 + RaceCarImpl.DIRECTION_CHANGE, raceCar.getDirection());
	}

	@Test
	public void increaseSpeedChangesSpeed() {
		raceCar.increaseSpeed();
		;
		assertEquals(0 + RaceCarImpl.SPEED_STEP, raceCar.getSpeed());
	}

	@Test
	public void slowDownDecreasesSpeed() {
		raceCar.decreaseSpeed();
		assertEquals(0 - RaceCarImpl.SPEED_STEP, raceCar.getSpeed());
	}

	@Test
	public void increaseSpeedChangesSpeedNotHigherThenMax() {
		raceCar.increaseSpeed();
		raceCar.increaseSpeed();
		raceCar.increaseSpeed();
		assertEquals(RaceCarImpl.MAX_SPEED, raceCar.getSpeed());
	}

	@Test
	public void slowDownDecreasesSpeedNotHigherThenMin() {
		raceCar.decreaseSpeed();
		raceCar.decreaseSpeed();
		assertEquals(RaceCarImpl.MIN_SPEED, raceCar.getSpeed());
	}
}
