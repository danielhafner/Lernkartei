package ch.zbw.lernkartei.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LearningView extends JPanel{

	private JLabel labelStartLearning;
	
	public LearningView()
	{
		this.labelStartLearning = new JLabel("Lernen starten");
		paint();
	}
	
	public void paint()
	{
		this.labelStartLearning.setFont(MyFont.Ueberschrift1.getMyFont());
		this.add(labelStartLearning);
		this.setVisible(true);
	}
	
	public LearningView getLearningView()
	{
		return this;
	}
}
