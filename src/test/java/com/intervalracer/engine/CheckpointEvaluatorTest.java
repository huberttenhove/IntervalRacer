package com.intervalracer.engine;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;
import com.intervalracer.model.RaceConfig;
import com.intervalracer.model.RaceTrack;
import com.intervalracer.model.impl.PlayerImpl;
import com.intervalracer.model.impl.RaceCarImpl;
import com.intervalracer.model.trackelements.TrackElement;
import com.intervalracer.statistics.PlayerRaceStatistics;
import com.intervalracer.statistics.RaceStatistics;

public class CheckpointEvaluatorTest {

	private Race raceMock;

	private RaceTrack trackMock;

	private RaceConfig configMock;

	private TrackElement trackElementMock;

	private RaceStatistics raceStatisticsMock;

	private CheckpointEvaluator checkpointEvaluator;

	private PlayerRaceStatistics playerRaceStatistics;

	private Player player;

	@Before
	public void setUp() {
		raceMock = mock(Race.class);
		trackMock = mock(RaceTrack.class);
		configMock = mock(RaceConfig.class);
		trackElementMock = mock(TrackElement.class);
		raceStatisticsMock = mock(RaceStatistics.class);

		checkpointEvaluator = new CheckpointEvaluator(raceMock);

		Point carPosition = new Point(100, 100);
		player = new PlayerImpl("Bowser", new RaceCarImpl(1, carPosition, CarColor.GREEN));
		playerRaceStatistics = new PlayerRaceStatistics(player);
		playerRaceStatistics.startNewLap();
		playerRaceStatistics.passCheckpoint(1);
	}

	@Test
	public void noLapsCountedDuringStartOfRace() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(1);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(1, playerRaceStatistics.getLastCheckpointPassed());
		verifyZeroInteractions(configMock);
	}

	@Test
	public void nothingToCheckWhenCarIsNotRacingOnACheckpoint() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(false);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(1, playerRaceStatistics.getLastCheckpointPassed());
		verifyZeroInteractions(configMock);
	}

	@Test
	public void carIsRacingThrougNewCheckPointNotFinish() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(2);
		when(trackElementMock.isFinish()).thenReturn(false);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(2, playerRaceStatistics.getLastCheckpointPassed());
		verifyZeroInteractions(configMock);
	}

	@Test
	public void carIsRacingInSameCheckPoint() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(1);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(1, playerRaceStatistics.getLastCheckpointPassed());
		verifyZeroInteractions(configMock);
	}

	@Test
	public void carIsRacingSkippedCheckPoint() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(3);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(1, playerRaceStatistics.getLastCheckpointPassed());
		verifyZeroInteractions(configMock);
	}

	@Test
	public void carIsRacingThroughFinishHasMoreLapsToGo() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(2);
		when(trackElementMock.isFinish()).thenReturn(true);
		when(raceMock.getRaceConfig()).thenReturn(configMock);
		when(configMock.getLaps()).thenReturn(2);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		assertEquals(0, playerRaceStatistics.getLastCheckpointPassed());
		assertEquals(CarState.ON_ROAD, player.getCar().getCarState());
	}

	@Test
	public void carIsRacingThroughFinishAndFinishes() {
		when(raceStatisticsMock.getPlayerRaceStatistics(player)).thenReturn(playerRaceStatistics);
		when(raceMock.getRaceTrack()).thenReturn(trackMock);
		when(trackMock.getTrackElementForPosition(player.getCar().getPosition())).thenReturn(trackElementMock);
		when(trackElementMock.isCheckpoint()).thenReturn(true);
		when(trackElementMock.getCheckpointNr()).thenReturn(2);
		when(trackElementMock.isFinish()).thenReturn(true);
		when(raceMock.getRaceConfig()).thenReturn(configMock);
		when(configMock.getLaps()).thenReturn(1);

		checkpointEvaluator.evaluateCheckpointsAndFinish(player, raceStatisticsMock);

		verify(raceStatisticsMock, times(1)).playerFinished(player);
		assertEquals(-1, playerRaceStatistics.getLastCheckpointPassed());
		assertEquals(CarState.FINISHED, player.getCar().getCarState());
	}
}
