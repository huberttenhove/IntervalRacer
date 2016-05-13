package com.intervalracer.backingbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Managed bean for the RaceConfig.xhtml page.
 */
@ManagedBean
@ViewScoped
public class Config {

	@ManagedProperty(value = "#{RaceView}")
	private RaceView raceView;

	public void setRaceView(RaceView raceView) {
		this.raceView = raceView;
	}

	public int getLaps() {
		return raceView.getRace().getRaceConfig().getLaps();
	}

	public void setLaps(int laps) {
		raceView.getRace().getRaceConfig().setLaps(laps);
	}

	public int getTimeInterval() {
		return raceView.getRace().getRaceConfig().getTimeInterval();
	}

	public void setTimeInterval(int timeInterval) {
		raceView.getRace().getRaceConfig().setTimeInterval(timeInterval);
	}
}
