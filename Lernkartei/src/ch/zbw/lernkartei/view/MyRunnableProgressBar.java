package ch.zbw.lernkartei.view;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.TranslationDataSet;

public class MyRunnableProgressBar implements Runnable{
	private JProgressBar bar;
	private LearningView learningView;
	private TranslationDataSet translation;
	
	public MyRunnableProgressBar(JProgressBar bar, LearningView learningView)
	{
		this.bar = bar;
		this.learningView = learningView;
		this.translation = new TranslationDataSet();
	}
	
	public void run() {
	int percent = 0;
	try {
		while(percent < 100)
		{
		percent = learningView.getPercentProgressBar();
		bar.setValue(percent);
		bar.setToolTipText(percent + "%");
		Thread.sleep(500);
		}
		JOptionPane.showMessageDialog(learningView, translation.getTranslatedText("Du hast dein Lernziel erreicht!", Language.Deutsch, Language.English));
		learningView.setPercentProgressBar(0);
		bar.setValue(0);
		bar.setToolTipText(0 + "%");
		bar.repaint();
		learningView.disableProgressBarThread();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}					
}