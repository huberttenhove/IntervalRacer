package com.intervalracer.model.trackelements;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

public class SofCurveTrackElementTest {

	@Test
	public void giveRoadTypeForBaseAngle() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.BASE_ANGLE,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(50, 80));
		assertEquals(TrackLocationRoadType.ROAD, type1);
	}

	@Test
	public void giveRoadTypeWhenDrivingOnGrass() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_90,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(150, 150));
		assertEquals(TrackLocationRoadType.GRASS, type1);
	}

	@Test
	public void giveRoadTypeWhenDrivingIntoTheWall() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_90,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(80, 180));
		assertEquals(TrackLocationRoadType.WALL, type1);
	}

	@Test
	public void giveRoadTypeForAngle90() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_90,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(50, 80));
		assertEquals(TrackLocationRoadType.GRASS, type1);
	}

	@Test
	public void giveRoadTypeForAngle180() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_180,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(50, 80));
		assertEquals(TrackLocationRoadType.WALL, type1);
	}

	@Test
	public void giveRoadTypeForAngle180Road() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_180,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(70, 190));
		assertEquals(TrackLocationRoadType.ROAD, type1);
	}

	@Test
	public void giveRoadTypeForAngle270() {
		SoftCurveTrackElement element = new SoftCurveTrackElement(CurveTranslationAngle.ANGLE_270,
		                TrackElementType.NORMAL, -1);
		TrackLocationRoadType type1 = element.getTrackLocationRoadTypeForLocation(new Point(50, 80));
		assertEquals(TrackLocationRoadType.ROAD, type1);
	}
}
