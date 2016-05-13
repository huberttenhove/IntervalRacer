package com.intervalracer.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.Player;

public class RaceStatisticsTest {

	private RaceStatistics statistics;

	private Player player;

	@Before
	public void setUp() {
		player = mock(Player.class);
		statistics = new RaceStatistics();
	}

	@Test
	public void addingPlayerMakesDefaultPlayerStatistics() {
		statistics.addPlayerRaceStatisticsFor(player);
		PlayerRaceStatistics playerRaceStatistics = statistics.getPlayerRaceStatistics(player);

		assertNotNull(playerRaceStatistics);
		assertEquals(Integer.MAX_VALUE, playerRaceStatistics.getFinishingPlace());
	}

	@Test
	public void finishingTheRaceAssignsRankingToPlayer() {
		statistics.addPlayerRaceStatisticsFor(player);
		statistics.playerFinished(player);
		PlayerRaceStatistics playerRaceStatistics = statistics.getPlayerRaceStatistics(player);
		assertEquals(playerRaceStatistics.getFinishingPlace(), 1);
	}

	@Test
	public void finishingTheRaceGivesListOfRanking() {
		statistics.addPlayerRaceStatisticsFor(player);
		Player player2 = mock(Player.class);
		statistics.addPlayerRaceStatisticsFor(player2);

		statistics.playerFinished(player);
		statistics.playerFinished(player2);

		PlayerRaceStatistics playerRaceStatistics = statistics.getPlayerRaceStatistics(player);
		assertEquals(playerRaceStatistics.getFinishingPlace(), 1);

		List<PlayerRaceStatistics> allPlayersStatistics = statistics.getAllPlayersStatistics();
		assertEquals(2, allPlayersStatistics.size());
		assertEquals(1, allPlayersStatistics.get(0).getFinishingPlace());
		assertEquals(2, allPlayersStatistics.get(1).getFinishingPlace());
	}
}
