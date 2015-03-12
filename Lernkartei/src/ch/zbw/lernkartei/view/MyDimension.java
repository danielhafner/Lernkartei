package ch.zbw.lernkartei.view;
import java.awt.Dimension;

/**
 * @author Ruel
 *
 */
public enum MyDimension {

	TextAreaDimension (250, 100),
	LearningFrontBackDimension (50, 200),
	ButtonCorrectWrongDimension (180, 50),
	MaximumSize (800, 600);
	
	private final Dimension mydimension;
	
	
	/** Sets the Enum-Dimension defined by width and height
	 * @param width
	 * @param height
	 */
	MyDimension(int width, int height)
	{
		this.mydimension = new Dimension(width, height);
	}
	
	/** Returns the Dimension of the Enum
	 * @return
	 */
	public Dimension get()
	{
		return this.mydimension;
	}
}
