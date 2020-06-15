package controller;

import view.MainWindow;

public class MainWindowController {

	protected MainWindow mainWindow;
	
	private static MainWindowController mwc = null;
	
	private MainWindowController() {

		mainWindow = new MainWindow();
	}
	
	public static MainWindowController getInstance() {
		
		if (mwc == null)
			mwc =  new MainWindowController();
		
		return mwc;
	}
	
	public MainWindow getMainWindow() {
		
		return mainWindow;
	}
}
