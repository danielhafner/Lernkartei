package ch.zbw.lernkartei.view;

import java.awt.Font;

/**
 * @author Ruel
 *
 */
public enum MyFont {
	
	Ueberschrift1 (new Font("Verdana", Font.BOLD, 20)),
	Ueberschrift2 (new Font("Verdana", Font.BOLD, 15)),
	Ueberschrift3 (new Font("Verdana", Font.PLAIN, 12)),
	Ueberschrift4 (new Font("Verdana", Font.ITALIC, 15));
	
	private final Font font;
	
	/** Sets the Font of this Enum
	 * @param font
	 */
	MyFont (Font font)
	{		
		this.font = font;
	}
	
	/**
	 * @return Gets the Font of this Enum
	 */
	public Font getMyFont()
	{
		return this.font;
	}
}