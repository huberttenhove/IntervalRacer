package com.intervalracer.backingbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.intervalracer.model.Player;

/**
 * Managed Backing Bean for the join page.
 */
@ManagedBean
@ViewScoped
public class Join {

	private static final int MAX_PLAYERS = 6;

	private String name;

	@ManagedProperty(value = "#{RaceView}")
	private RaceView raceView;

	@ManagedProperty(value = "#{PlayerManager}")
	private PlayerManager playerManager;

	public void setRaceView(RaceView raceView) {
		this.raceView = raceView;
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public Player getPlayer() {
		return playerManager.getPlayer();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String joinRace() {
		if (raceView.getRace().getPlayers().size() < MAX_PLAYERS) {
			Player player = raceView.getRace().addPlayer(name);
			playerManager.setPlayer(player);
			return "WaitRoom";
		} else {
			return "RaceFull";
		}
	}

	/**
	 * Accepts new commands from player. Returns redirect=true so after reload a
	 * command is not given again.
	 */
	public String setNewCommand(String command) {
		playerManager.getPlayer().setLastCommand(command);
		return "RaceView?faces-redirect=true";
	}

	/**
	 * Checks if the race is in progress.
	 */
	public boolean isRaceStarted() {
		return raceView.isRaceInProgress();
	}
}
