package com.intervalracer.backingbeans;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 * The waitroom is a viewscoped managed bean. It contains a countdown timer
 * which does a countdown for the race start. When a player joins the timer is
 * reset.
 */
@ManagedBean
@ViewScoped
public class WaitRoom {

	@ManagedProperty(value = "#{RaceView}")
	private RaceView raceView;

	@Inject
	@Push(channel = "countdownToRaceStart")
	private PushContext push;

	private static int secondsUntilRaceStart = 10;

	private static Timer countDownToRaceStart = new Timer();

	public void setRaceView(RaceView raceView) {
		this.raceView = raceView;
	}

	public void startRace() {
		try {
			killTimer();
			raceView.startRace();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/IntervalRacer/faces/RaceView.xhtml");
		} catch (IOException e) {
			System.out.println("ERROR: Unable to forward clients to the race. " + e);
		}
	}

	/**
	 * Player joins waitroom when navigation to the page. The timer is then
	 * reset.
	 */
	public void joinWaitRoom() {
		if (raceView.getRace().getPlayers().size() > 1) {
			setSecondsUntilRaceStart(10);
			startTimer();
		}
	}

	public PushContext getPush() {
		return push;
	}

	private void startTimer() {
		countDownToRaceStart.cancel();
		countDownToRaceStart.purge();
		countDownToRaceStart = new Timer();
		CountdownToStartRaceTimerTask countdownToStartRaceTimerTask = new CountdownToStartRaceTimerTask();
		countdownToStartRaceTimerTask.setWaitRoom(this);
		countDownToRaceStart.scheduleAtFixedRate(countdownToStartRaceTimerTask, 500, 1000);
	}

	@PreDestroy
	private void killTimer() {
		countDownToRaceStart.cancel();
		countDownToRaceStart.purge();
	}

	public int getSecondsUntilRaceStart() {
		return secondsUntilRaceStart;
	}

	public static void setSecondsUntilRaceStart(int secondsUntilRaceStart) {
		WaitRoom.secondsUntilRaceStart = secondsUntilRaceStart;
	}

	class CountdownToStartRaceTimerTask extends TimerTask {

		private WaitRoom wr;

		private int countDownFrom = 10;

		@Override
		public void run() {
			countDownFrom--;
			WaitRoom.setSecondsUntilRaceStart(countDownFrom);
			wr.getPush().send(countDownFrom);
		}

		public void setWaitRoom(WaitRoom waitRoom) {
			wr = waitRoom;
		}
	}
}
