package ch.zbw.lernkartei.view;
import javax.swing.JProgressBar;

public class PaintProgressBar implements Runnable{
	private JProgressBar bar;
	private LearningView learningView;
	
	public PaintProgressBar(JProgressBar bar, LearningView learningView)
	{
		this.bar = bar;
		this.learningView = learningView;
	}
	
	public void run() {		
	int zahl = 0;	
	try {
		while(zahl < 100)
		{
		zahl = learningView.getZahl();
		bar.setValue(zahl);
		bar.setToolTipText(zahl + "%");
		System.out.println(zahl + "");
		Thread.sleep(500);
		}
		Thread.sleep(10000);
		bar.repaint();
		learningView.setZahl(0);
		bar.setValue(0);
		bar.setToolTipText(0 + "%");
		learningView.disableProgressBarThread();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}					
}