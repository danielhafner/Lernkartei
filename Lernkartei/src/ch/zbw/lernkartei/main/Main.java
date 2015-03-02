package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.Controller;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.GUI;
import ch.zbw.lernkartei.view.SettingsView;

public class Main {
	
	private static Controller c;

	public static void main(String[] args) {		
		
		GUI gui = new GUI();
		Card card = new Card();
		Register register = new Register();
		SettingsView settingsView = new SettingsView();
		
		Controller controller = new Controller(gui, settingsView, register);
		controller.startApplication();
		gui.setVisible(true);
	}
}