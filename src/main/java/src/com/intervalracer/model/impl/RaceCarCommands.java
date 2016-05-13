package com.intervalracer.model.impl;

import java.io.Serializable;

/**
 * Enum of the different accepted player commands.
 */
public enum RaceCarCommands implements Serializable {

	FASTER("F"),

	SLOWER("S"),

	LEFT("L"),

	RIGHT("R"),

	NONE("");

	private String command;

	private RaceCarCommands(String command) {
		this.command = command;
	}

	public static RaceCarCommands fromString(String command) {
		for (RaceCarCommands c : RaceCarCommands.values()) {
			if (c.command.equalsIgnoreCase(command)) {
				return c;
			}
		}
		return NONE;
	}
}
