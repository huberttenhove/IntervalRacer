package com.intervalracer.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.backingbeans.RaceView;
import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;
import com.intervalracer.model.impl.RaceImpl;

public class RaceEngineTest {

	private RaceEngine engine;

	private Race race;

	private RaceView raceViewMock;

	private CommandEvaluator commandEvaluatorMock;

	private PositionTranslator positionTranslatorMock;

	private CheckpointEvaluator checkpointEvaluatorMock;

	@Before
	public void setUp() {
		raceViewMock = mock(RaceView.class);
		race = new RaceImpl();
		race.addPlayer("Mario");
		race.getPlayers().get(0).setRacing(true);

		commandEvaluatorMock = mock(CommandEvaluator.class);
		positionTranslatorMock = mock(PositionTranslator.class);
		checkpointEvaluatorMock = mock(CheckpointEvaluator.class);
		engine = new RaceEngine(race, raceViewMock, commandEvaluatorMock, positionTranslatorMock,
		                checkpointEvaluatorMock);
	}

	@Test
	public void twoTimesStartingEngineHasNoEffects() {
		engine.startEngine();
		engine.startEngine();

		assertTrue(engine.isRaceInProgress());

		engine.stopEngine("TEST");
		assertFalse(engine.isRaceInProgress());
	}

	@Test
	public void engineStartsAndStops() {
		engine.startEngine();
		assertTrue(engine.isRaceInProgress());

		engine.stopEngine("TEST");
		assertFalse(engine.isRaceInProgress());
	}

	@Test
	public void engineDoesNothingWhenAllPlayersAreFinished() {

		Player player = race.getPlayers().get(0);
		player.getCar().setCarState(CarState.FINISHED);
		engine.processClockCycle();

		verify(raceViewMock, times(1)).notifyClientsRaceFinished();
		verifyZeroInteractions(commandEvaluatorMock, positionTranslatorMock, checkpointEvaluatorMock);
	}

	@Test
	public void engineDoesNothingWhenAllPlayersAreCrashed() {

		Player player = race.getPlayers().get(0);
		player.getCar().setCarState(CarState.CRASHED);

		engine.processClockCycle();

		verify(raceViewMock, times(1)).notifyClientsRaceFinished();
		verifyZeroInteractions(commandEvaluatorMock, positionTranslatorMock, checkpointEvaluatorMock);
	}

	@Test
	public void engineProcessesEngineStepsForPlayer() {
		Player player = race.getPlayers().get(0);

		engine.processClockCycle();

		verify(commandEvaluatorMock, times(1)).evaluatePlayersLastCommand(player);
		verify(positionTranslatorMock, times(1)).translatePlayerRaceCarPosition(player);
		verify(checkpointEvaluatorMock, times(1)).evaluateCheckpointsAndFinish(player, race.getRaceStatistics());
		verify(raceViewMock, times(1)).notifyClients();
	}
}
