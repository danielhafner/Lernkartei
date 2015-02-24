package ch.zbw.lernkartei.view;

import java.awt.Font;

public enum MyFont {
	
	Ueberschrift1 (new Font("Verdana", Font.BOLD, 20)),
	Ueberschrift2 (new Font("Verdana", Font.BOLD, 15)),
	Ueberschrift3 (new Font("Verdana", Font.PLAIN, 12));
	
	private final Font font;
	
	MyFont (Font font)
	{		
		this.font = font;
	}
	
	public Font getMyFont()
	{
		return this.font;
	}
}