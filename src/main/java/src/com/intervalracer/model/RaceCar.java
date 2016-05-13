package com.intervalracer.model;

import java.awt.Point;

/**
 * The interface for the car model.
 */
public interface RaceCar {

	/**
	 * @return The current position of the RaceCar.
	 */
	public Point getPosition();

	/**
	 * @return The current direction of the RaceCar in degrees.
	 */
	public int getDirection();

	/**
	 * @return The current speed of the RaceCar.
	 */
	public int getSpeed();

	/**
	 * @return The {@link CarColor} of the RaceCar.
	 */
	public CarColor getColor();

	/**
	 * Makes the RaceCar turn left by changing its direction.
	 */
	public void turnLeft();

	/**
	 * Makes the RaceCar turn right by changing its direction.
	 */
	public void turnRight();

	/**
	 * Increases the speed of the car.
	 */
	public void increaseSpeed();

	/**
	 * Decreases the speed of the car.
	 */
	public void decreaseSpeed();

	/**
	 * @return The starting number of the RaceCar.
	 */
	public int getStartNr();

	/**
	 * @param state
	 *            Sets the current {@link CarState} of the car.
	 */
	public void setCarState(CarState state);

	/**
	 * @return Get the current {@link CarState}.
	 */
	public CarState getCarState();

	/**
	 * Resets the car location to a given position.
	 */
	public void resetToPosition(Point position);
}
