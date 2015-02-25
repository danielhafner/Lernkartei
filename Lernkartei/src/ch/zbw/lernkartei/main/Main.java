package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.Controller;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.GUI;

public class Main {
	
	private static Controller c;

	public static void main(String[] args) {		
		
		GUI gui = new GUI();
		Card card = new Card();
		Register register = new Register();
		
		Controller controller = new Controller(gui, register);
		controller.startApplication();
		gui.setVisible(true);
		
		/*
		c = new Controller();
		c.startApplication();
		*/
	}
}