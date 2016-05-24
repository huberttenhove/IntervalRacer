package com.intervalracer.engine;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.Player;
import com.intervalracer.model.impl.PlayerImpl;
import com.intervalracer.model.impl.RaceCarCommands;
import com.intervalracer.model.impl.RaceCarImpl;

public class CommandEvaluatorTest {

	private CommandEvaluator commandEvaluator;

	private Player player;

	private int carX = 100;

	private int carY = 100;

	@Before
	public void setUp() {
		player = new PlayerImpl("Luigi", new RaceCarImpl(1, new Point(carX, carY), CarColor.ORANGE));
		commandEvaluator = new CommandEvaluator();
	}

	@Test
	public void playerHasNoCommand() {
		player.setLastCommand(RaceCarCommands.NONE.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), 0);
		assertEquals(player.getCar().getSpeed(), 0);
	}

	@Test
	public void playerSpeedsUp() {
		player.setLastCommand(RaceCarCommands.FASTER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), 0);
		assertEquals(player.getCar().getSpeed(), RaceCarImpl.SPEED_STEP);
	}

	@Test
	public void playerSlowsDown() {
		player.setLastCommand(RaceCarCommands.SLOWER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), 0);
		assertEquals(player.getCar().getSpeed(), -RaceCarImpl.SPEED_STEP);
	}

	@Test
	public void playerTurnsLeft() {
		player.setLastCommand(RaceCarCommands.LEFT.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), -RaceCarImpl.DIRECTION_CHANGE);
		assertEquals(player.getCar().getSpeed(), 0);
	}

	@Test
	public void playerTurnsRight() {
		player.setLastCommand(RaceCarCommands.RIGHT.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), RaceCarImpl.DIRECTION_CHANGE);
		assertEquals(player.getCar().getSpeed(), 0);
	}

	@Test
	public void playerTurnsRightAndSpeedsUp() {
		player.setLastCommand(RaceCarCommands.RIGHT.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		player.setLastCommand(RaceCarCommands.FASTER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), RaceCarImpl.DIRECTION_CHANGE);
		assertEquals(player.getCar().getSpeed(), RaceCarImpl.SPEED_STEP);
	}

	@Test
	public void playerCannotGoSlowerThanMinimum() {
		player.setLastCommand(RaceCarCommands.SLOWER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		player.setLastCommand(RaceCarCommands.SLOWER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), 0);
		assertEquals(player.getCar().getSpeed(), RaceCarImpl.MIN_SPEED);
	}

	@Test
	public void playerCannotGoFasterThanMaximum() {
		player.setLastCommand(RaceCarCommands.FASTER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		player.setLastCommand(RaceCarCommands.FASTER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		player.setLastCommand(RaceCarCommands.FASTER.toString());
		commandEvaluator.evaluatePlayersLastCommand(player);

		assertEquals(RaceCarCommands.NONE, player.getLastCommand());
		assertEquals(player.getCar().getDirection(), 0);
		assertEquals(player.getCar().getSpeed(), RaceCarImpl.MAX_SPEED);
	}
}
