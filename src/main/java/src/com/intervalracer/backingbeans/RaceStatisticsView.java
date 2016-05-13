package com.intervalracer.backingbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.intervalracer.statistics.PlayerRaceStatistics;

/**
 * Managed bean for the statistics page.
 */
@ManagedBean
@ViewScoped
public class RaceStatisticsView {

	@ManagedProperty(value = "#{RaceView}")
	private RaceView raceView;

	public void setRaceView(RaceView raceView) {
		this.raceView = raceView;
	}

	public List<PlayerRaceStatistics> getStatistics() {
		return raceView.getRace().getRaceStatistics().getAllPlayersStatistics();
	}

	public String redirect() {
		return "Join";
	}
}
