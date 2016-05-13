package com.intervalracer.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.RaceCar;

public class PlayerImplTest {

	private Player player;

	private RaceCar raceCar;

	@Before
	public void setUp() {
		raceCar = new RaceCarImpl(1, new Point(100, 100), CarColor.WHITE);
		player = new PlayerImpl("Koopa", raceCar);
		raceCar.setCarState(CarState.ON_ROAD);
	}

	@Test
	public void isNotActiveRacingWhenRacingIsFalse() {
		player.setRacing(false);
		assertFalse(player.isStillActiveRacing());
	}

	@Test
	public void isNotActiveRacingWhenCarIsCrashed() {
		player.setRacing(true);
		raceCar.setCarState(CarState.CRASHED);
		assertFalse(player.isStillActiveRacing());
	}

	@Test
	public void isNotActiveRacingWhenCarIsFinished() {
		player.setRacing(true);
		raceCar.setCarState(CarState.FINISHED);
		assertFalse(player.isStillActiveRacing());
	}

	@Test
	public void isActiveRacingWhenCarIsOnRoad() {
		player.setRacing(true);
		assertTrue(player.isStillActiveRacing());
	}

	@Test
	public void isActiveRacingWhenCarIsOnGrass() {
		player.setRacing(true);
		raceCar.setCarState(CarState.ON_GRASS);
		assertTrue(player.isStillActiveRacing());
	}
}
