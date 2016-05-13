package com.intervalracer.model.impl;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;
import com.intervalracer.model.RaceConfig;
import com.intervalracer.model.RaceTrack;
import com.intervalracer.statistics.RaceStatistics;
import com.intervalracer.tracks.SimpleTrack;

/**
 * Implementation of the Race interface.
 */
public class RaceImpl implements Race, Serializable {

	private static final long serialVersionUID = 9200564648468516380L;

	private List<Player> players;

	private RaceConfig raceConfig;

	private RaceTrack raceTrack;

	private RaceStatistics raceStatistics;

	public RaceImpl() {
		players = new ArrayList<Player>();
		raceConfig = new RaceConfig(3, 500, 15);
		raceTrack = new SimpleTrack();
		raceStatistics = new RaceStatistics();
	}

	/**
	 * Adds a new player to the game. The starting nr of a player is based on
	 * the game join order. Synchronized so no two playerss get assigned the
	 * same startNr.
	 */
	public synchronized Player addPlayer(String name) {
		int startNr = players.size();
		Point startingPosition = raceTrack.getStartingPostions()[startNr];
		Player newPlayer = new PlayerImpl(name, new RaceCarImpl(startNr, startingPosition, CarColor.getColor(startNr)));
		players.add(newPlayer);
		raceStatistics.addPlayerRaceStatisticsFor(newPlayer);

		return newPlayer;
	}

	public void startRace() {
		for (Player player : players) {
			raceStatistics.getPlayerRaceStatistics(player).startNewLap();
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public RaceConfig getRaceConfig() {
		return raceConfig;
	}

	public RaceTrack getRaceTrack() {
		return raceTrack;
	}

	public RaceStatistics getRaceStatistics() {
		return raceStatistics;
	}
}
