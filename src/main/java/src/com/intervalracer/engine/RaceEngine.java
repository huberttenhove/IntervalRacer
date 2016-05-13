package com.intervalracer.engine;

import java.util.Timer;
import java.util.TimerTask;

import com.intervalracer.backingbeans.RaceView;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;

/**
 * This is the game engine of Interval Racer. The RaceConfig is used to setup
 * the engine for the race.
 * 
 * Each Time interval the engine calculates the new position of each players car
 * according to their last command.
 */
public class RaceEngine extends TimerTask {

	private static final int MAX_TIME_AFTER_FIRST_FINISH = 5;

	private Race race;

	private boolean raceInProgress;

	private Timer timer;

	private RaceView raceView;

	private boolean engineRunning;

	private CommandEvaluator commandEvaluator;

	private PositionTranslator positionTranslator;

	private CheckpointEvaluator checkpointEvaluator;

	private long startTimeOfRace;

	public RaceEngine(Race race, RaceView raceView, CommandEvaluator commandEvaluator,
	                PositionTranslator positionTranslator, CheckpointEvaluator checkpointEvaluator) {
		this.race = race;
		this.raceView = raceView;
		this.commandEvaluator = commandEvaluator;
		this.positionTranslator = positionTranslator;
		this.checkpointEvaluator = checkpointEvaluator;

		timer = new Timer();
		engineRunning = false;
		raceInProgress = false;
	}

	/**
	 * Starts the game eninge processing. The speed of processing is defined by
	 * the time interval. There is a three seconds delay for the start.
	 * 
	 * The processing is done using scheduleAtFixedRate, this ensures that
	 * intervals that are smaller than a process cycle are not colliding with
	 * one another.
	 */
	public void startEngine() {
		if (!engineRunning) {
			raceInProgress = true;
			engineRunning = true;
			timer.scheduleAtFixedRate(this, 1000, (long) race.getRaceConfig().getTimeInterval());
			startTimeOfRace = System.currentTimeMillis();
			race.startRace();
		}
	}

	/**
	 * Timertask called method.
	 */
	@Override
	public void run() {
		processClockCycle();
	}

	/**
	 * Processes the different steps of a clockcycle of the game engine. Players
	 * that are no longer racing are not processed. After the processing all
	 * clients are notified for update. When there are no more players racing
	 * the race engine is shutdown
	 */
	public void processClockCycle() {
		boolean noOneIsRacing = true;

		for (Player player : race.getPlayers()) {
			if (!player.isStillActiveRacing()) {
				continue;
			}
			noOneIsRacing = false;
			commandEvaluator.evaluatePlayersLastCommand(player);
			positionTranslator.translatePlayerRaceCarPosition(player);
			checkpointEvaluator.evaluateCheckpointsAndFinish(player, race.getRaceStatistics());
			checkRaceDuration();
		}

		if (noOneIsRacing) {
			stopEngine("All players finished or crashed.");
		} else {
			notifyClients();
		}
	}

	private void checkRaceDuration() {
		long raceDurationInMinutes = (System.currentTimeMillis() - startTimeOfRace) / 1000 / 60;
		if (raceDurationInMinutes > race.getRaceConfig().getMaximumRaceDuration()
		                || raceDurationInMinutes > MAX_TIME_AFTER_FIRST_FINISH) {
			stopEngine("Race Duration passed.");
		}
	}

	public void stopEngine(String stopReason) {
		timer.cancel();
		timer.purge();
		raceInProgress = false;
		engineRunning = false;
		raceView.notifyClientsRaceFinished();
	}

	/**
	 * This pushes a notification to the connected clients through a websocket.
	 * The clients reload the page with the new state of the race after the
	 * engine calculated a new clock cycle.
	 */
	private void notifyClients() {
		raceView.notifyClients();
	}

	public boolean isRaceInProgress() {
		return raceInProgress;
	}
}
