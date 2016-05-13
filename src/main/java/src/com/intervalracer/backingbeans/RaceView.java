package com.intervalracer.backingbeans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import com.google.gson.Gson;
import com.intervalracer.engine.CheckpointEvaluator;
import com.intervalracer.engine.CommandEvaluator;
import com.intervalracer.engine.PositionTranslator;
import com.intervalracer.engine.RaceEngine;
import com.intervalracer.model.Player;
import com.intervalracer.model.Race;
import com.intervalracer.model.impl.RaceImpl;

/**
 * Managed bean for the race view page. It is application scoped so all clients
 * look at the same race instance. On accessing the page it starts the race
 * engine to process clock cycles. The {@link PushContext} is used to notify
 * clients when the engine processes another clock cycle and when the race is
 * finished.
 */
@ManagedBean(name = "RaceView")
@ApplicationScoped
public class RaceView {

	@Inject
	@Push(channel = "raceHeartBeat")
	private PushContext push;

	private Race race;

	private RaceEngine raceEngine;

	public RaceView() {
		super();
		race = new RaceImpl();
		raceEngine = new RaceEngine(race, this, new CommandEvaluator(), new PositionTranslator(race.getRaceTrack()),
		                new CheckpointEvaluator(race));
	}

	public void notifyClients() {
		push.send(getPlayersInJson());
	}

	public void notifyClientsRaceFinished() {
		push.send("raceFinished");
	}

	public String showStatistics() {
		return "RaceStatisticsView";
	}

	public Race getRace() {
		return race;
	}

	public synchronized void startRace() {
		if (!isRaceInProgress()) {
			raceEngine = new RaceEngine(race, this, new CommandEvaluator(), new PositionTranslator(race.getRaceTrack()),
			                new CheckpointEvaluator(race));

			for (Player player : race.getPlayers()) {
				player.setLastCommand("none");
				player.setRacing(true);
				player.getCar().resetToPosition(
				                race.getRaceTrack().getStartingPostions()[player.getCar().getStartNr()]);
			}

			raceEngine.startEngine();
		}
	}

	public boolean isRaceInProgress() {
		return raceEngine.isRaceInProgress();
	}

	public String getPlayersInJson() {
		return new Gson().toJson(race.getPlayers());
	}
}
