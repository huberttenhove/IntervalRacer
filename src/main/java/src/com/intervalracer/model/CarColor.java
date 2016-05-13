package com.intervalracer.model;

/**
 * The available car colors.
 */
public enum CarColor {

	YELLOW(1),

	GREEN(2),

	RED(3),

	BLUE(4),

	ORANGE(5),

	WHITE(6);

	private final int colorNr;

	private CarColor(int colorNr) {
		this.colorNr = colorNr;
	}

	public static CarColor getColor(int nr) {
		return values()[nr];
	}

	public int getColorNr() {
		return colorNr;
	}
}
