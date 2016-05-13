package com.intervalracer.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.intervalracer.model.Player;

/**
 * Contains the player race statistics.
 */
public class PlayerRaceStatistics implements Comparable<PlayerRaceStatistics>, Serializable {

	private static final long serialVersionUID = -3984829097502734701L;

	private Player player;

	private LapStatistics currentLap;

	private List<LapStatistics> lapStats;

	private int finishingPlace;

	public PlayerRaceStatistics(Player player) {
		this.player = player;
		lapStats = new ArrayList<LapStatistics>();
		finishingPlace = Integer.MAX_VALUE;
	}

	public void startNewLap() {
		currentLap = new LapStatistics();
		lapStats.add(currentLap);
	}

	public int finishLap() {
		currentLap.finish();
		return lapStats.size();
	}

	public int getLastCheckpointPassed() {
		return currentLap.getLastCheckpointPassed();
	}

	public void passCheckpoint(int checkpoint) {
		currentLap.passCheckpoint(checkpoint);
	}

	public int getFinishingPlace() {
		return finishingPlace;
	}

	public void setFinishingPlace(int finishingPlace) {
		this.finishingPlace = finishingPlace;
	}

	public Player getPlayer() {
		return player;
	}

	public List<LapStatistics> getLapStats() {
		return lapStats;
	}

	public int compareTo(PlayerRaceStatistics otherStats) {
		if (otherStats.finishingPlace != this.finishingPlace) {
			return this.finishingPlace - otherStats.finishingPlace;
		} else if (this.lapStats.size() != otherStats.lapStats.size()) {
			return otherStats.lapStats.size() - this.lapStats.size();
		} else {
			return otherStats.getLastCheckpointPassed() - this.getLastCheckpointPassed();
		}
	}
}
