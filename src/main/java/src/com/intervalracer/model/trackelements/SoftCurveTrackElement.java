package com.intervalracer.model.trackelements;

import java.awt.Point;

import com.intervalracer.model.trackelements.util.CurveBasePointAngleTranslator;

/**
 * This TrackElement describes a soft curve. By setting the translation angle
 * all type of curves can be constructed.
 * 
 * The default curve has the track starting in the upper center en exiting in de
 * left center. Setting the {@link CurveTranslationAngle} affects this.
 */
public class SoftCurveTrackElement implements TrackElement {

	private CurveTranslationAngle curveTranslationAngle;

	private int roadInnerRadius;

	private int roadOuterRadius;

	private int grassInnerRadius;

	private int grassOuterRadius;

	private TrackElementType type;

	private int checkpointNr;

	public SoftCurveTrackElement(CurveTranslationAngle curveTranslationAngle, TrackElementType type, int checkpointNr) {
		this.curveTranslationAngle = curveTranslationAngle;
		this.type = type;
		this.checkpointNr = checkpointNr;
		calculateRadiusses();
	}

	/**
	 * This method calculates the border (radius) of the road and the grass.
	 * These values are then used in the determination algorithm for the road
	 * type.
	 */
	private void calculateRadiusses() {
		roadInnerRadius = TrackElementDefaults.ELEMENT_SIZE.getValue() / 2
		                - TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		roadOuterRadius = TrackElementDefaults.ELEMENT_SIZE.getValue() / 2
		                + TrackElementDefaults.TRACK_WIDTH.getValue() / 2;
		grassInnerRadius = roadInnerRadius - TrackElementDefaults.GRASS_OFFSET.getValue();
		grassOuterRadius = roadOuterRadius + TrackElementDefaults.GRASS_OFFSET.getValue();
	}

	/**
	 * Based on the calculated borders and the length of the line between the
	 * base corner and the locationwe can determine the road type on the track.
	 * The location is first translated based on the
	 * {@link CurveTranslationAngle}.
	 */
	public TrackLocationRoadType getTrackLocationRoadTypeForLocation(Point location) {
		Point translatedLocation = CurveBasePointAngleTranslator.translateForCurveAngle(location, curveTranslationAngle,
		                TrackElementDefaults.ELEMENT_SIZE.getValue());
		int lengthFromBasePoint = (int) Math.sqrt(
		                (translatedLocation.x * translatedLocation.x) + (translatedLocation.y * translatedLocation.y));

		if (lengthFromBasePoint > roadInnerRadius && lengthFromBasePoint < roadOuterRadius) {
			return TrackLocationRoadType.ROAD;
		} else if (lengthFromBasePoint > grassInnerRadius && lengthFromBasePoint < grassOuterRadius) {
			return TrackLocationRoadType.GRASS;
		} else {
			return TrackLocationRoadType.WALL;
		}
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
