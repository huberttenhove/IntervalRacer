package com.intervalracer.engine;

import com.intervalracer.model.Player;
import com.intervalracer.model.RaceCar;
import com.intervalracer.model.impl.RaceCarCommands;

/**
 * Class that evaluates players commands. The last command of the {@link Player}
 * is then applied to the {@link RaceCar}.
 */
public class CommandEvaluator {

	public CommandEvaluator() {
	}

	/**
	 * Processes the last command that this player has given in the timeframe
	 * for a clockcycle.
	 */
	public void evaluatePlayersLastCommand(Player player) {
		RaceCarCommands command = player.getLastCommand();
		switch (command) {
		case FASTER:
			player.getCar().increaseSpeed();
			break;
		case SLOWER:
			player.getCar().decreaseSpeed();
			break;
		case LEFT:
			player.getCar().turnLeft();
			break;
		case RIGHT:
			player.getCar().turnRight();
			break;
		default:
			break;
		}
		player.setLastCommand(RaceCarCommands.NONE.toString());
	}
}