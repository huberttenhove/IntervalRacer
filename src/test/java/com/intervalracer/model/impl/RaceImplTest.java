package com.intervalracer.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.Player;
import com.intervalracer.model.Race;

public class RaceImplTest {

	private Race race;

	private Player player;

	@Before
	public void setUp() {
		race = new RaceImpl();
	}

	@Test
	public void addingPlayerSetsUpPlayer() {
		player = race.addPlayer("Wario");

		assertNotNull(player.getCar());
		assertNotNull(player.getCar().getPosition());
	}

	@Test
	public void addingPlayerAlsoAddsRaceStatistics() {
		player = race.addPlayer("Wario");

		assertNotNull(race.getRaceStatistics().getPlayerRaceStatistics(player));
	}

	@Test
	public void startingTheRaceAlsoStartsTheLapsOfTheJoinedPlayers() {
		player = race.addPlayer("Wario");
		race.startRace();

		assertEquals(0, race.getRaceStatistics().getPlayerRaceStatistics(player).getLastCheckpointPassed());
	}
}
