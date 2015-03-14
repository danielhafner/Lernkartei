package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.Controller;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.LearningView;
import ch.zbw.lernkartei.view.MainView;
import ch.zbw.lernkartei.view.EditCardsView;

/**
 * @author Ruel
 *
 */
public class Main {
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
		EditCardsView settingsView = new EditCardsView();
		LearningView learningView = new LearningView();
		Controller controller = new Controller(mainView, settingsView, learningView, register);
		controller.paintMainView();
		mainView.setVisible(true);		
	}
}