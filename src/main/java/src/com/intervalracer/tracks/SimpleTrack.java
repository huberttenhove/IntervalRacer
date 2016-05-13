package com.intervalracer.tracks;

import java.awt.Point;
import java.io.Serializable;

import com.intervalracer.model.RaceTrack;
import com.intervalracer.model.trackelements.CurveTranslationAngle;
import com.intervalracer.model.trackelements.HorizontalTrackElement;
import com.intervalracer.model.trackelements.SoftCurveTrackElement;
import com.intervalracer.model.trackelements.TrackElement;
import com.intervalracer.model.trackelements.TrackElementDefaults;
import com.intervalracer.model.trackelements.TrackElementType;
import com.intervalracer.model.trackelements.VerticalTrackElement;

/**
 * This class represents a simple racetrack. A racetrack is build op using
 * TrackElements.
 */
public class SimpleTrack implements RaceTrack, Serializable {

	private static final long serialVersionUID = -2416329117984051742L;

	private TrackElement[][] track = new TrackElement[4][4];

	public SimpleTrack() {
		createTrack();
	}

	public TrackElement[][] getTrackPieces() {
		return track;
	}

	/**
	 * The track has the following layout.
	 * 
	 * <pre>
	 * 	C--H--H--C
	 *  |        | 
	 *  V  C--H--C
	 *  |  |
	 *  C--C
	 *  
	 *  H: HorizontalTrackElement
	 *  C: SoftCurveTrackElement
	 *  V:  VerticalTrackElement
	 * </pre>
	 */
	private void createTrack() {
		track[0][0] = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_180, TrackElementType.NORMAL, -1);
		track[1][0] = new HorizontalTrackElement(TrackElementType.NORMAL, -1);
		track[2][0] = new HorizontalTrackElement(TrackElementType.CHECKPOINT, 1);
		track[3][0] = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_270, TrackElementType.NORMAL, -1);
		track[3][1] = new SoftCurveTrackElement(CurveTranslationAngle.BASE_ANGLE, TrackElementType.NORMAL, -1);
		track[2][1] = new HorizontalTrackElement(TrackElementType.NORMAL, -1);
		track[1][1] = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_180, TrackElementType.CHECKPOINT, 2);
		track[1][2] = new SoftCurveTrackElement(CurveTranslationAngle.BASE_ANGLE, TrackElementType.NORMAL, -1);
		track[0][2] = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_90, TrackElementType.NORMAL, -1);
		track[0][1] = new VerticalTrackElement(TrackElementType.START_FINISH, 3);
	}

	/**
	 * Uiteindelijk moeten de staring positions berekend kunnen worden op basis
	 * van het {@link TrackElement} dat de start / finish bevat. Voorlopig gaan
	 * we hier hardcoded uit van track[1][0].
	 */
	public Point[] getStartingPostions() {
		int xFirstCar = 70;
		int yFirstCar = 220;

		Point[] startingPostions = new Point[10];
		Point point = new Point(xFirstCar, yFirstCar);
		startingPostions[0] = point;

		for (int i = 1; i < 6; i++) {
			if (i % 2 == 0) {
				yFirstCar += 60;
				xFirstCar -= 40;
			} else {
				xFirstCar += 40;
			}

			point = new Point(xFirstCar, yFirstCar);

			startingPostions[i] = point;
		}
		return startingPostions;
	}

	public TrackElement getTrackElementForPosition(Point position) {
		int xPos = position.x;
		int yPos = position.y;

		int elementXLoc = (xPos - 1) / TrackElementDefaults.ELEMENT_SIZE.getValue();
		int elementYLoc = (yPos - 1) / TrackElementDefaults.ELEMENT_SIZE.getValue();

		return track[elementXLoc][elementYLoc];
	}
}
