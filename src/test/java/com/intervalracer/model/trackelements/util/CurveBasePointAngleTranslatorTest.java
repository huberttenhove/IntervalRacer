package com.intervalracer.model.trackelements.util;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import com.intervalracer.model.trackelements.CurveTranslationAngle;

public class CurveBasePointAngleTranslatorTest {

	/**
	 * The following Matrix has a column for each angle. The list below
	 * describes points for a TrackElement in each quardrant of that element.
	 * (Given an element with a width of 200.
	 * 
	 * <pre>
	 *	Q1 	 50,  80
	 * 	Q2 	120,  10
	 *	Q3	180, 170
	 * 	Q4	 80, 190
	 * </pre>
	 */

	@Test
	public void translateFirstQuadrantPoint_50_80() {
		Point translateBase = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(50, 80),
		                CurveTranslationAngle.BASE_ANGLE, 200);
		assertEquals(new Point(50, 80), translateBase);

		Point translate90 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(50, 80),
		                CurveTranslationAngle.ANGLE_90, 200);
		assertEquals(new Point(80, 150), translate90);

		Point translate180 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(50, 80),
		                CurveTranslationAngle.ANGLE_180, 200);
		assertEquals(new Point(150, 120), translate180);

		Point translate270 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(50, 80),
		                CurveTranslationAngle.ANGLE_270, 200);
		assertEquals(new Point(120, 50), translate270);
	}

	@Test
	public void translateSecondQuadrantPoint_120_10() {
		Point translateBase = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(120, 10),
		                CurveTranslationAngle.BASE_ANGLE, 200);
		assertEquals(new Point(120, 10), translateBase);

		Point translate90 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(120, 10),
		                CurveTranslationAngle.ANGLE_90, 200);
		assertEquals(new Point(10, 80), translate90);

		Point translate180 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(120, 10),
		                CurveTranslationAngle.ANGLE_180, 200);
		assertEquals(new Point(80, 190), translate180);

		Point translate270 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(120, 10),
		                CurveTranslationAngle.ANGLE_270, 200);
		assertEquals(new Point(190, 120), translate270);
	}

	@Test
	public void translateThirdQuadrantPoint_180_170() {
		Point translateBase = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(180, 170),
		                CurveTranslationAngle.BASE_ANGLE, 200);
		assertEquals(new Point(180, 170), translateBase);

		Point translate90 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(180, 170),
		                CurveTranslationAngle.ANGLE_90, 200);
		assertEquals(new Point(170, 20), translate90);

		Point translate180 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(180, 170),
		                CurveTranslationAngle.ANGLE_180, 200);
		assertEquals(new Point(20, 30), translate180);

		Point translate270 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(180, 170),
		                CurveTranslationAngle.ANGLE_270, 200);
		assertEquals(new Point(30, 180), translate270);
	}

	@Test
	public void translateFourthQuadrantPoint_80_190() {
		Point translateBase = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(80, 190),
		                CurveTranslationAngle.BASE_ANGLE, 200);
		assertEquals(new Point(80, 190), translateBase);

		Point translate90 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(80, 190),
		                CurveTranslationAngle.ANGLE_90, 200);
		assertEquals(new Point(190, 120), translate90);

		Point translate180 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(80, 190),
		                CurveTranslationAngle.ANGLE_180, 200);
		assertEquals(new Point(120, 10), translate180);

		Point translate270 = CurveBasePointAngleTranslator.translateForCurveAngle(new Point(80, 190),
		                CurveTranslationAngle.ANGLE_270, 200);
		assertEquals(new Point(10, 80), translate270);
	}
}
