package controller;

import java.awt.event.ActionListener;

public class CommandFactory {

	public static ActionListener createCommand(String commandType) {
		
		switch(commandType) {
		case "Search":
			return new Search();
		default:
			throw new IllegalArgumentException("Action \"" + commandType + "\" is not supported.");
		}
	}
}
