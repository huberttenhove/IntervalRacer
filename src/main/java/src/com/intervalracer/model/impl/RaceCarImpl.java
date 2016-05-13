package com.intervalracer.model.impl;

import java.awt.Point;
import java.io.Serializable;

import com.intervalracer.model.CarColor;
import com.intervalracer.model.CarState;
import com.intervalracer.model.RaceCar;

/**
 * Implementation of the RaceCar interface. Defines default values that apply to
 * the RaceCar.
 */
public class RaceCarImpl implements RaceCar, Serializable {

	private static final long serialVersionUID = 7263496512254814737L;

	public static final int MAX_SPEED_ON_GRASS = 30;

	public static final int MIN_SPEED = -30;

	public static final int MAX_SPEED = 60;

	public static final int SPEED_STEP = 30;

	public static final int MAX_DIRECTION = 360;

	public static final int DIRECTION_CHANGE = 30;

	private int startNr;

	private Point position;

	private int direction;

	private int speed;

	private CarColor color;

	private CarState carState;

	public RaceCarImpl(int startNr, Point startingPosition, CarColor color) {
		this.startNr = startNr;
		position = startingPosition;
		this.color = color;

		direction = 0;
		speed = 0;
		carState = CarState.ON_ROAD;
	}

	public Point getPosition() {
		return position;
	}

	public int getDirection() {
		return direction;
	}

	public void turnLeft() {
		direction = (direction - DIRECTION_CHANGE) % MAX_DIRECTION;
	}

	public void turnRight() {
		direction = (direction + DIRECTION_CHANGE) % MAX_DIRECTION;
	}

	public int getSpeed() {
		return speed;
	}

	public void increaseSpeed() {
		int newSpeed = speed + SPEED_STEP;

		if (newSpeed > MAX_SPEED) {
			speed = MAX_SPEED;
		} else {
			speed = newSpeed;
		}
	}

	public void decreaseSpeed() {
		int newSpeed = speed - SPEED_STEP;

		if (newSpeed < MIN_SPEED) {
			speed = MIN_SPEED;
		} else {
			speed = newSpeed;
		}
	}

	public CarColor getColor() {
		return color;
	}

	public int getStartNr() {
		return startNr;
	}

	public CarState getCarState() {
		return carState;
	}

	public void setCarState(CarState carState) {
		this.carState = carState;
	}

	public void resetToPosition(Point startingPosition) {
		position = startingPosition;
		direction = 0;
	}
}
