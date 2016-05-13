package com.intervalracer.model;

import java.io.Serializable;

/**
 * The configuration for a race. The number of laps, timeInterval in
 * milliseconds and maximumRaceDuration in minutes.
 */
public class RaceConfig implements Serializable {

	private static final long serialVersionUID = -267353519917361617L;

	private int laps;

	private int timeInterval;

	private int maximumRaceDuration;

	public RaceConfig(int laps, int timeInterval, int maximumRaceDuration) {
		super();
		this.laps = laps;
		this.timeInterval = timeInterval;
		this.maximumRaceDuration = maximumRaceDuration;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void setMaximumRaceDuration(int maximumRaceDuration) {
		this.maximumRaceDuration = maximumRaceDuration;
	}

	public int getMaximumRaceDuration() {
		return maximumRaceDuration;
	}
}
