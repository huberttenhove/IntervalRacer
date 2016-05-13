package com.intervalracer.model.trackelements;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class HorizontalTrackElementTest {

	private HorizontalTrackElement track;

	@Before
	public void setUp() {
		track = new HorizontalTrackElement(TrackElementType.NORMAL, -1);
	}

	@Test
	public void pointIsOnRoad() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(100, 100));
		assertEquals(type, TrackLocationRoadType.ROAD);
	}

	@Test
	public void pointOnGrassUpperSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(10, 40));
		assertEquals(type, TrackLocationRoadType.GRASS);
	}

	@Test
	public void pointOnGrassLowerSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(100, 151));
		assertEquals(type, TrackLocationRoadType.GRASS);
	}

	@Test
	public void pointOnWallUpperSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(15, 15));
		assertEquals(type, TrackLocationRoadType.WALL);
	}

	@Test
	public void pointOnWallLowerSide() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(199, 199));
		assertEquals(type, TrackLocationRoadType.WALL);
	}

	@Test
	public void pointOutsideBorderDefaultsToWall() {
		TrackLocationRoadType type = track.getTrackLocationRoadTypeForLocation(new Point(310, 340));
		assertEquals(type, TrackLocationRoadType.WALL);
	}
}
