package com.intervalracer.model.trackelements;

import java.awt.Point;

/**
 * Interface for the TrackElements.
 * 
 * Track Elements are described in a 2 dimensional space. Where the point in the
 * upper left corner is 0,0; Track elements must know what the RoadType is for a
 * given location.
 */
public interface TrackElement {

	/**
	 * @return The {@link TrackLocationRoadType} for the given location.
	 */
	public TrackLocationRoadType getTrackLocationRoadTypeForLocation(Point location);

	/**
	 * @return True, if the track element is a checkpoint, a finish is a special
	 *         kind of checkpoint.
	 */
	public boolean isCheckpoint();

	/**
	 * @return The checkpoint nr of the track element.
	 */
	public int getCheckpointNr();

	/**
	 * @return True, if the track element is a finish.
	 */
	public boolean isFinish();
}
