package ch.zbw.lernkartei.view;
import java.awt.Dimension;

public enum MyDimension {

	TextAreaDimension (250, 100),
	LearningFrontBackDimension (50, 200),
	ButtonCorrectWrongDimension (180, 50),
	MaximumSize (800, 600);
	
	private final Dimension mydimension;
	
	
	MyDimension(int width, int height)
	{
		this.mydimension = new Dimension(width, height);
	}
	
	public Dimension get()
	{
		return this.mydimension;
	}
}
