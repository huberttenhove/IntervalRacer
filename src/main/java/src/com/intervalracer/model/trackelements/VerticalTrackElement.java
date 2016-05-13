package com.intervalracer.model.trackelements;

import java.awt.Point;

/**
 * This class represents a vertical piece of track. Vertical tracks elements
 * have the road in the center of the TrackElement.
 */
public class VerticalTrackElement implements TrackElement {

	private int xTrackBorderLeft;

	private int xGrassBorderLeft;

	private int xTrackBorderRight;

	private int xGrassBorderRight;

	private TrackElementType type;

	private int checkpointNr;

	public VerticalTrackElement(TrackElementType type, int checkpointNr) {
		this.type = type;
		this.checkpointNr = checkpointNr;
		calculateRoadAndGrassBorders();
	}

	/**
	 * This method calculates the border of the road and the grass. These values
	 * are then used in the determination algorithm for the road type..
	 * 
	 * <pre>
	 *      Center of track
	 *          |
	 *  | |  |	   |  | |
	 *  | |  |  |  |  | |
	 *  | |  |     |  | |
	 *  | |  |  |  |  | |
	 *  | |  |     |  | |
	 *          |
	 *          
	 *       <----->  		Road  (DEFAULT_TRACK_WIDTH)
	 *    <-->     <--> 	Grass (DEFAULT_GRASS_WIDTH)
	 *  <->           <->  	Wall  (rest)
	 *  
	 *  <--------------->	Total (DEFAULT_ELEMENT_SIZE)
	 * </pre>
	 */
	private void calculateRoadAndGrassBorders() {
		int centerOfTrack = TrackElementDefaults.ELEMENT_SIZE.getValue() / 2;

		xTrackBorderLeft = centerOfTrack - TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		xGrassBorderLeft = xTrackBorderLeft - TrackElementDefaults.GRASS_OFFSET.getValue();

		xTrackBorderRight = centerOfTrack + TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		xGrassBorderRight = xTrackBorderRight + TrackElementDefaults.GRASS_OFFSET.getValue();
	}

	/**
	 * Based on the calculated borders and the x location of the point we can
	 * determine the road type on the track.
	 */
	public TrackLocationRoadType getTrackLocationRoadTypeForLocation(Point location) {
		if (location.x < xTrackBorderRight && location.x > xTrackBorderLeft) {
			return TrackLocationRoadType.ROAD;
		} else if (location.x < xGrassBorderRight && location.x > xGrassBorderLeft) {
			return TrackLocationRoadType.GRASS;
		}

		return TrackLocationRoadType.WALL;
	}

	public boolean isCheckpoint() {
		return type != TrackElementType.NORMAL;
	}

	public boolean isFinish() {
		return type == TrackElementType.START_FINISH;
	}

	public int getCheckpointNr() {
		return checkpointNr;
	}
}
