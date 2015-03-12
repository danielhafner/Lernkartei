package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.Controller;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.LearningView;
import ch.zbw.lernkartei.view.MainView;
import ch.zbw.lernkartei.view.SettingsView;

public class Main {
	
	private static Controller c;

	/*
	 * Einstiegspunkt des Programms
	 */
	public static void main(String[] args) {		
		run();
	}
	
	public static void run()
	{
		MainView mainView = new MainView();
		Register register = new Register();
		SettingsView settingsView = new SettingsView();
		LearningView learningView = new LearningView();
		Controller controller = new Controller(mainView, settingsView, learningView, register);
		controller.paintMainView();
		mainView.setVisible(true);		
	}
}