package com.intervalracer.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.Player;

public class PlayerRaceStatisticsTest {

	private PlayerRaceStatistics statistics;

	private Player player;

	@Before
	public void setUp() {
		player = mock(Player.class);
		statistics = new PlayerRaceStatistics(player);
	}

	@Test
	public void startingALapMakesLapStatistics() {
		statistics.startNewLap();
		List<LapStatistics> lapStats = statistics.getLapStats();

		assertNotNull(lapStats);
		assertEquals(1, lapStats.size());
	}

	@Test
	public void passingCheckpointsIsStoredInLapStatistics() {
		statistics.startNewLap();

		int lastCheckpointPassed = statistics.getLastCheckpointPassed();
		assertEquals(0, lastCheckpointPassed);
		statistics.passCheckpoint(1);

		lastCheckpointPassed = statistics.getLastCheckpointPassed();
		assertEquals(1, lastCheckpointPassed);
	}

	@Test
	public void passingTheFinishLineClosesLapStatisticsAndCalculatesLapDuration() {
		statistics.startNewLap();
		statistics.passCheckpoint(1);
		statistics.finishLap();

		assertTrue(statistics.getLapStats().get(0).getLapDuration() >= 0);
	}

	@Test
	public void playerStatisticsCanBeComparedToDetermineRankingOnFinishingPlace() {
		PlayerRaceStatistics statistics1 = new PlayerRaceStatistics(player);
		statistics1.setFinishingPlace(1);
		PlayerRaceStatistics statistics2 = new PlayerRaceStatistics(player);
		statistics2.setFinishingPlace(2);

		assertEquals(-1, statistics1.compareTo(statistics2));
		assertEquals(1, statistics2.compareTo(statistics1));
	}

	@Test
	public void playerStatisticsCanBeComparedToDetermineRankingOnNumberOfFinishedLaps() {
		PlayerRaceStatistics statistics1 = new PlayerRaceStatistics(player);
		statistics1.startNewLap();
		statistics1.finishLap();
		statistics1.startNewLap();
		statistics1.finishLap();

		PlayerRaceStatistics statistics2 = new PlayerRaceStatistics(player);
		statistics2.startNewLap();
		statistics2.finishLap();

		assertEquals(-1, statistics1.compareTo(statistics2));
		assertEquals(1, statistics2.compareTo(statistics1));
	}

	@Test
	public void playerStatisticsCanBeComparedToDetermineRankingOnLastCheckpointPassed() {
		PlayerRaceStatistics statistics1 = new PlayerRaceStatistics(player);
		statistics1.startNewLap();
		statistics1.passCheckpoint(3);
		PlayerRaceStatistics statistics2 = new PlayerRaceStatistics(player);
		statistics2.startNewLap();
		statistics2.passCheckpoint(2);

		assertEquals(-1, statistics1.compareTo(statistics2));
		assertEquals(1, statistics2.compareTo(statistics1));
	}
}
