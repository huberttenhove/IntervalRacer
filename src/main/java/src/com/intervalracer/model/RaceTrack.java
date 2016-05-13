package com.intervalracer.model;

import java.awt.Point;

import com.intervalracer.model.trackelements.TrackElement;

/**
 * The racetrack interface. A track has to provide two methodes. One to get al
 * the track pieces and another to determine the location of the track for a
 * given point.
 *
 */
public interface RaceTrack {

	/**
	 * @return The 2 dimenional array that contains the track.
	 */
	public TrackElement[][] getTrackPieces();

	/**
	 * @return Array that contains the starting positions for the track in
	 *         descending order.
	 */
	public Point[] getStartingPostions();

	/**
	 * @return The {@link TrackElement} for the given position on the racetrack
	 */
	public TrackElement getTrackElementForPosition(Point position);
}
