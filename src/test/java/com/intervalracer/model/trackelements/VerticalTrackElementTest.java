package com.intervalracer.model.trackelements;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class VerticalTrackElementTest {

	private VerticalTrackElement track;

	@Before
	public void setUp() {
		track = new VerticalTrackElement(TrackElementType.NORMAL, -1);
	}

	@Test
	public void pointIsOnRoad() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(100, 100));
		assertEquals(type, TrackLocationRoadType.ROAD);
	}

	@Test
	public void pointOnGrassLeftSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(40, 10));
		assertEquals(type, TrackLocationRoadType.GRASS);
	}

	@Test
	public void pointOnGrassRightSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(151, 100));
		assertEquals(type, TrackLocationRoadType.GRASS);
	}

	@Test
	public void pointOnWallLeftSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(15, 15));
		assertEquals(type, TrackLocationRoadType.WALL);
	}

	@Test
	public void pointOnWallRightSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(199, 199));
		assertEquals(type, TrackLocationRoadType.WALL);
	}

	@Test
	public void pointOutsideBorderDefaultsToWall() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(321, 340));
		assertEquals(type, TrackLocationRoadType.WALL);
	}
}
