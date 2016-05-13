package com.intervalracer.model.trackelements;

import java.awt.Point;

/**
 * This class represents a horizontal piece of track. Horizontal tracks elements
 * have the road in center of the TrackElement.
 */
public class HorizontalTrackElement implements TrackElement {

	private int yTrackBorderAbove;

	private int yGrassBorderAbove;

	private int yTrackBorderBelow;

	private int yGrassBorderBelow;

	private TrackElementType type;

	private int checkpointNr;

	public HorizontalTrackElement(TrackElementType type, int checkpointNr) {
		this.type = type;
		this.checkpointNr = checkpointNr;
		calculateRoadAndGrassBorders();
	}

	/**
	 * This method calculates the border of the road and the grass. These values
	 * are then used in the determination algorithm for the road type.
	 * 
	 * <pre>
	 *          
	 *	  	-------------------         |  Wall
	 *	  	-------------------     |   |   
	 *	                            |
	 *	  	-------------------  |  | Grass
	 *	  		 			  	 |
	 *						   	 | Road
	 *    - - - - - - - - - - - -| - - - -  Center of track
	 *  						 |
	 *  						 |
	 *  	-------------------	 |  |
	 *    	                        |
	 * 		-------------------     |  |
	 *  	-------------------        |
	 * 
	 * 
	 * </pre>
	 */
	private void calculateRoadAndGrassBorders() {
		int centerOfTrack = TrackElementDefaults.ELEMENT_SIZE.getValue() / 2;

		yTrackBorderAbove = centerOfTrack - TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		yGrassBorderAbove = yTrackBorderAbove - TrackElementDefaults.GRASS_OFFSET.getValue();

		yTrackBorderBelow = centerOfTrack + TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		yGrassBorderBelow = yTrackBorderBelow + TrackElementDefaults.GRASS_OFFSET.getValue();
	}

	/**
	 * Based on the calculated borders and the y location of the point we can
	 * determine the road type on the track.
	 */
	public TrackLocationRoadType getTrackLocationRoadTypeForLocation(Point location) {
		if (location.y < yTrackBorderBelow && location.y > yTrackBorderAbove) {
			return TrackLocationRoadType.ROAD;
		} else if (location.y < yGrassBorderBelow && location.y > yGrassBorderAbove) {
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
