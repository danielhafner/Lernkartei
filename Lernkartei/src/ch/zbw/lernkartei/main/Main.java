package ch.zbw.lernkartei.main;

import ch.zbw.lernkartei.controller.controller;

public class Main {
	
	private static controller c;

	public static void main(String[] args) {
		
		c = new controller();
		c.startApplication();
	}
}