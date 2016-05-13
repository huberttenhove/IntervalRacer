package com.intervalracer.model.trackelements;

/**
 * Default values that must be used when making track elements.
 */
public enum TrackElementDefaults {

    /**
     * Width and Height of a trak element.
     */
	ELEMENT_SIZE(200),

	/**
	 * Width of the track (Road).
	 */
	TRACK_WIDTH(100),

	/**
	 * Grass offset next to the road.
	 */
	GRASS_OFFSET(35);

	private int value;

	private TrackElementDefaults(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
