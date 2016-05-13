package com.intervalracer.statistics;

import java.io.Serializable;

/**
 * Contains the statistics for a lap on the track.
 */
public class LapStatistics implements Serializable {

	private static final long serialVersionUID = 7412214612737555469L;

	private long lapStartTime = -1;

	private long lapDuration = -1;

	private int lastCheckpointPassed;

	public LapStatistics() {
		lastCheckpointPassed = 0;
		lapStartTime = System.currentTimeMillis();
	}

	public void passCheckpoint(int checkpoint) {
		lastCheckpointPassed = checkpoint;
	}

	public int getLastCheckpointPassed() {
		return lastCheckpointPassed;
	}

	public void finish() {
		lastCheckpointPassed = -1;
		lapDuration = System.currentTimeMillis() - lapStartTime;
	}

	public long getLapDuration() {
		return lapDuration;
	}
}
