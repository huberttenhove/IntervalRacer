package com.intervalracer.model;

import com.intervalracer.model.impl.RaceCarCommands;

/**
 * The interface for the player model
 */
public interface Player {

	/**
	 * @return The name of the Player.
	 */
	public String getName();

	/**
	 * @return The {@link RaceCar} of the player.
	 */
	public RaceCar getCar();

	/**
	 * Sets the last command that the player has given.
	 */
	public void setLastCommand(String command);

	/**
	 * @return The last command the player has given.
	 */
	public RaceCarCommands getLastCommand();

	/**
	 * @param racing
	 *            Boolean value that indicates if the player is currently
	 *            racing.
	 */
	public void setRacing(boolean racing);

	/**
	 * @return True if the player is racing.
	 */
	public boolean isRacing();

	/**
	 * @return True if the player is racing and not crashed of finished.
	 */
	public boolean isStillActiveRacing();
}
