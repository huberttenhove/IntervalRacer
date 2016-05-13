package com.intervalracer.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
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

	private long finishTimeFirstPlayer;

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
		finishTimeFirstPlayer = 0;
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
			finishTimeFirstPlayer = 0;
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
			boolean hasPlayerFinished = checkpointEvaluator.evaluateCheckpointsAndFinish(player,
			                race.getRaceStatistics());
			checkRaceDuration(hasPlayerFinished);
		}

		if (noOneIsRacing) {
			stopEngine("All players finished or crashed.");
		} else {
			notifyClients();
		}
	}

	private void checkRaceDuration(boolean hasPlayerFinished) {
		if (hasPlayerFinished && finishTimeFirstPlayer == 0) {
			finishTimeFirstPlayer = System.currentTimeMillis();
		}

		long raceDurationInMinutes = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTimeOfRace);
		long raceDurationAfterFirstFinishInMinutes = TimeUnit.MILLISECONDS
		                .toMinutes(System.currentTimeMillis() - finishTimeFirstPlayer);

		if (raceDurationInMinutes > race.getRaceConfig().getMaximumRaceDuration()
		                || (hasPlayerFinished && raceDurationAfterFirstFinishInMinutes > MAX_TIME_AFTER_FIRST_FINISH)) {
			System.out.println("STOPPING Race Engine, race duration exceeded. Duration: " + raceDurationInMinutes
			                + " DurationafterFirstFinish: " + raceDurationAfterFirstFinishInMinutes);
			stopEngine("Race Duration passed.");
		}
	}

	public void stopEngine(String stopReason) {
		timer.cancel();
		timer.purge();
		raceInProgress = false;
		engineRunning = false;
		raceView.notifyClientsRaceFinished();
		saveStatistics();
	}

	private void saveStatistics() {
		String statisticsJson = new Gson().toJson(race.getRaceStatistics().getAllPlayersStatistics());
		Path file = Paths.get("Statistics-" + System.currentTimeMillis() + ".stats");
		try {
			Files.write(file, statisticsJson.getBytes());
		} catch (IOException e) {
			System.out.println("Error writing statistics to file: " + e);
		}
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
