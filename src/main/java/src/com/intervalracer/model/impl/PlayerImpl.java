package com.intervalracer.model.impl;

import java.io.Serializable;

import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.RaceCar;

/**
 * Implementation of the player interface
 */
public class PlayerImpl implements Player, Serializable {

	private static final long serialVersionUID = -7740123779500989753L;

	private String name;

	private RaceCar car;

	private RaceCarCommands lastCommand;

	private boolean racing;

	public PlayerImpl(String name, RaceCar car) {
		super();
		this.name = name;
		this.car = car;
		lastCommand = RaceCarCommands.NONE;
		racing = false;
	}

	public String getName() {
		return name;
	}

	public RaceCar getCar() {
		return car;
	}

	public void setLastCommand(String command) {
		lastCommand = RaceCarCommands.valueOf(command.toUpperCase());
	}

	public RaceCarCommands getLastCommand() {
		return lastCommand;
	}

	public void setRacing(boolean racing) {
		this.racing = racing;
	}

	public boolean isRacing() {
		return racing;
	}

	public boolean isStillActiveRacing() {
		return racing && (car.getCarState().equals(CarState.ON_ROAD) || car.getCarState().equals(CarState.ON_GRASS));
	}
}
