package com.intervalracer.model.trackelements.util;

import java.awt.Point;

import com.intervalracer.model.trackelements.CurveTranslationAngle;
import com.intervalracer.model.trackelements.TrackElement;

public final class CurveBasePointAngleTranslator {

	/**
	 * Translate the points for the given angle and elementSize. When there is
	 * no angle, no translation is needed.
	 * 
	 * The basepoint for a curve is located at point 200,200 at the lower left
	 * corner of a {@link TrackElement}.
	 */
	public static Point translateForCurveAngle(Point location, CurveTranslationAngle angle, int elementSize) {
		int x = location.x;
		int y = location.y;

		switch (angle) {
		case ANGLE_90:
			x = location.y;
			y = elementSize - location.x;
			break;
		case ANGLE_180:
			x = elementSize - location.x;
			y = elementSize - location.y;
			break;
		case ANGLE_270:
			x = elementSize - location.y;
			y = location.x;
			break;
		default:
			break;
		}

		return new Point(x, y);
	}
}
