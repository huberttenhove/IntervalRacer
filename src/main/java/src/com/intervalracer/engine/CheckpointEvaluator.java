package com.intervalracer.engine;

import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;
import com.intervalracer.model.trackelements.TrackElement;
import com.intervalracer.statistics.PlayerRaceStatistics;
import com.intervalracer.statistics.RaceStatistics;

/**
 * Evaluates if a {@link Player} drives throug a checkpoint or the finish.
 * 
 */
public class CheckpointEvaluator {

	private Race race;

	public CheckpointEvaluator(Race race) {
		this.race = race;
	}

	/**
	 * evaluates if a car drives through a checkpoint or the finish. Checkpoints
	 * need to be passed in order, the finish is a special kind of checkpoint.
	 * 
	 * @return true if a player has finished this turn.
	 */
	public boolean evaluateCheckpointsAndFinish(Player player, RaceStatistics raceStatistics) {
		PlayerRaceStatistics playerRaceStatistics = raceStatistics.getPlayerRaceStatistics(player);
		TrackElement trackElementForCarPosition = race.getRaceTrack()
		                .getTrackElementForPosition(player.getCar().getPosition());

		if (trackElementForCarPosition.isCheckpoint()) {
			int lastCheckpointPassed = playerRaceStatistics.getLastCheckpointPassed();
			int checkpointNr = trackElementForCarPosition.getCheckpointNr();
			if (lastCheckpointPassed + 1 == checkpointNr) {
				playerRaceStatistics.passCheckpoint(checkpointNr);

				return checkIfPlayerFinished(player, raceStatistics, trackElementForCarPosition);
			}
		}

		return false;
	}

	/**
	 * Checks if the {@link TrackElement} that is passed is the finish. If so
	 * set state of car to finished. otherwise start a new lap.
	 * 
	 * @return true if a player has finished;
	 */
	private boolean checkIfPlayerFinished(Player player, RaceStatistics raceStatistics,
	                TrackElement trackElementForCarPosition) {
		if (trackElementForCarPosition.isFinish()) {
			PlayerRaceStatistics playerRaceStatistics = raceStatistics.getPlayerRaceStatistics(player);
			int finishedLap = playerRaceStatistics.finishLap();

			if (finishedLap == race.getRaceConfig().getLaps()) {
				raceStatistics.playerFinished(player);
				player.getCar().setCarState(CarState.FINISHED);
				return true;
			} else {
				playerRaceStatistics.startNewLap();
			}
		}
		return false;
	}
}
