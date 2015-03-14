package ch.zbw.lernkartei.view;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.TranslationDataSet;

public class PaintProgressBar implements Runnable{
	private JProgressBar bar;
	private LearningView learningView;
	private TranslationDataSet translation;
	
	public PaintProgressBar(JProgressBar bar, LearningView learningView)
	{
		this.bar = bar;
		this.learningView = learningView;
		this.translation = new TranslationDataSet();
	}
	
	public void run() {		
	int zahl = 0;	
	try {
		while(zahl < 100)
		{
		zahl = learningView.getZahl();
		bar.setValue(zahl);
		bar.setToolTipText(zahl + "%");
		Thread.sleep(1000);
		}
		JOptionPane.showMessageDialog(learningView, translation.getTranslatedText("Du hast dein Lernziel erreicht!", Language.Deutsch, Language.English));
		learningView.setZahl(0);
		bar.setValue(0);
		bar.setToolTipText(0 + "%");
		bar.repaint();
		learningView.disableProgressBarThread();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}					
}