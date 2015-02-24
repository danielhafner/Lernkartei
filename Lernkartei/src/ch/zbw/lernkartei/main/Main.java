package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.Controller;

public class Main {
	
	private static Controller c;

	public static void main(String[] args) {		
		c = new Controller();
		c.startApplication();
	}
}