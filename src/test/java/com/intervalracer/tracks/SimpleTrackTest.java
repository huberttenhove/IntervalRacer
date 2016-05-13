package com.intervalracer.tracks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.intervalracer.model.trackelements.TrackElement;
import com.intervalracer.model.trackelements.TrackLocationRoadType;

public class SimpleTrackTest {

	private SimpleTrack track;

	@Before
	public void setUp() {
		track = new SimpleTrack();
	}

	@Test
	public void getTrackLocationRoadTypeForLocationTypeIsRoad() {
		Point position = new Point(100, 100);
		TrackLocationRoadType roadType = track.getTrackElementForPosition(position)
		                .getTrackLocationRoadTypeForLocation(position);
		assertEquals(TrackLocationRoadType.ROAD, roadType);
	}

	@Test
	public void getTrackLocationRoadTypeForLocationTypeIsGrass() {
		Point position = new Point(25, 320);
		TrackLocationRoadType roadType = track.getTrackElementForPosition(position)
		                .getTrackLocationRoadTypeForLocation(position);
		assertEquals(TrackLocationRoadType.GRASS, roadType);
	}

	@Test
	public void getTrackLocationRoadTypeForLocationTypeIsWall() {
		Point position = new Point(400, 15);
		TrackLocationRoadType roadType = track.getTrackElementForPosition(position)
		                .getTrackLocationRoadTypeForLocation(position);
		assertEquals(TrackLocationRoadType.WALL, roadType);
	}

	@Test
	public void getTrackElementForPositionGiveElement() {
		Point position = new Point(50, 220);
		TrackElement trackElementForPosition = track.getTrackElementForPosition(position);
		assertTrue(trackElementForPosition.isFinish());
	}

	@Test
	public void getTrackElementForPositionGivesNullIfNoElementPresent() {
		track.getTrackElementForPosition(new Point(500, 500));
	}
}
