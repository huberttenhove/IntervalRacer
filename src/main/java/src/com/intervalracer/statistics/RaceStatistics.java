package com.intervalracer.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intervalracer.model.Player;

/**
 * Contains the RaceStatistics.
 */
public class RaceStatistics implements Serializable {

	private static final long serialVersionUID = 6407618237598206606L;

	private Map<Player, PlayerRaceStatistics> raceStatistics;

	private int nrOfFinishedPlayers;

	public RaceStatistics() {
		raceStatistics = new HashMap<Player, PlayerRaceStatistics>();
		nrOfFinishedPlayers = 0;
	}

	public void addPlayerRaceStatisticsFor(Player player) {
		raceStatistics.put(player, new PlayerRaceStatistics(player));
	}

	public PlayerRaceStatistics getPlayerRaceStatistics(Player player) {
		return raceStatistics.get(player);
	}

	public void playerFinished(Player player) {
		nrOfFinishedPlayers++;
		PlayerRaceStatistics playerRaceStatistics = raceStatistics.get(player);
		playerRaceStatistics.setFinishingPlace(nrOfFinishedPlayers);
	}

	/**
	 * Gives back a list of {@linkplain PlayerRaceStatistics}. The statistics
	 * are sorted so the winner is at the top of the list.
	 */
	public List<PlayerRaceStatistics> getAllPlayersStatistics() {
		List<PlayerRaceStatistics> playerStatList = new ArrayList<PlayerRaceStatistics>(raceStatistics.values());
		Collections.sort(playerStatList);
		return playerStatList;
	}
}
