package com.intervalracer.engine;

import java.awt.Point;

import com.intervalracer.model.CarState;
import com.intervalracer.model.Player;
import com.intervalracer.model.RaceCar;
import com.intervalracer.model.RaceTrack;
import com.intervalracer.model.impl.RaceCarImpl;
import com.intervalracer.model.trackelements.TrackElement;
import com.intervalracer.model.trackelements.TrackElementDefaults;
import com.intervalracer.model.trackelements.TrackLocationRoadType;

/**
 * Translates the {@link RaceCar} position based on the Speed and Direction of
 * the {@link RaceCar}.
 */
public class PositionTranslator {

	private RaceTrack track;

	public PositionTranslator(RaceTrack track) {
		this.track = track;
	}

	/**
	 * Calculate the new {@link RaceCar} position for a given {@link Player}.
	 * The new position is based on the speed and direction of the car.
	 * 
	 * Due to rounding differences a car can move slightly slower when
	 * cornering. This effect is accepted, we always slow a bit down in corners
	 * ;)
	 */
	public void translatePlayerRaceCarPosition(Player player) {
		RaceCar car = player.getCar();
		CarState carState = car.getCarState();

		double direction = car.getDirection();
		int speed = car.getSpeed();
		Point carPosition = car.getPosition();

		if (carState.equals(CarState.ON_GRASS) && speed > RaceCarImpl.MAX_SPEED_ON_GRASS) {
			speed = RaceCarImpl.MAX_SPEED_ON_GRASS;
		}

		int x = 0;
		int y = 0;
		if (direction == 0) {
			y -= speed;
		} else {
			x = (int) (Math.sin(Math.PI / 180 * direction) * speed);
			y = (int) (Math.cos(Math.PI / 180 * direction) * speed) * -1;
		}

		carPosition.translate(x, y);

		System.out.println("Player: " + player.getName() + " speed: " + speed + " direction: " + direction
		                + " translation; X:" + x + " Y:" + y);

		processCarState(car);
	}

	/**
	 * Based on the road type where the car is racing the {@link CarState} is
	 * updated.
	 */
	private void processCarState(RaceCar car) {
		TrackLocationRoadType currentRoadType = getCurrentRoadType(car.getPosition());

		if (currentRoadType.equals(TrackLocationRoadType.ROAD)) {
			car.setCarState(CarState.ON_ROAD);
		} else if (currentRoadType.equals(TrackLocationRoadType.GRASS)) {
			car.setCarState(CarState.ON_GRASS);
		} else {
			System.out.println("SOMEONE CRASHED! @" + car.getPosition());
			car.setCarState(CarState.CRASHED);
		}
	}

	/**
	 * Determine the roadtype where the car is driving based on the position of
	 * the car. First determine the {@link TrackElement} where the car is
	 * driving. Then determine the roadtyp on the {@link TrackElement}
	 */
	private TrackLocationRoadType getCurrentRoadType(Point carPosition) {
		int xPos = carPosition.x;
		int yPos = carPosition.y;
		Point carLocationOnTrackElement = new Point(xPos % TrackElementDefaults.ELEMENT_SIZE.getValue(),
		                yPos % TrackElementDefaults.ELEMENT_SIZE.getValue());

		TrackElement trackElementForCarPosition = track.getTrackElementForPosition(carPosition);
		return trackElementForCarPosition.getTrackLocationRoadTypeForLocation(carLocationOnTrackElement);
	}

}
