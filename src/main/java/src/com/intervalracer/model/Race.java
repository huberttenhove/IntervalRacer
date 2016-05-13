package com.intervalracer.model;

import java.util.List;

import com.intervalracer.statistics.RaceStatistics;

/**
 * The interface for the Race Model
 *
 */
public interface Race {

	/**
	 * Add a new {@link Player} to the Race.
	 */
	public Player addPlayer(String name);

	/**
	 * @return The {@link Player}'s participating in the race.
	 */
	public List<Player> getPlayers();

	/**
	 * @return The {@link RaceConfig} of the Race.
	 */
	public RaceConfig getRaceConfig();

	/**
	 * @return The {@link RaceTrack} of the Race.
	 */
	public RaceTrack getRaceTrack();

	/**
	 * @return The {@link RaceStatistics} of the Race.
	 */
	public RaceStatistics getRaceStatistics();

	/**
	 * Starts the Race.
	 */
	public void startRace();
}
