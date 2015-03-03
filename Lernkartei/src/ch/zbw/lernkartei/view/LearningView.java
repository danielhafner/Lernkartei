package ch.zbw.lernkartei.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.zbw.lernkartei.model.Register;

public class LearningView extends JPanel{

	private JLabel labelStartLearning;
	
	private JLabel labelBox;
	private JComboBox<String> comboboxBox;
	
	private JButton buttonCardFront;
	private JButton buttonCardBack;

	private JPanel panelAnswer;
	private JButton buttonCorrect;
	private JButton buttonWrong;
	
	public LearningView()
	{
		this.labelStartLearning = new JLabel("Lernen starten");
		
		this.labelBox = new JLabel("Fach");
		this.comboboxBox = new JComboBox<String>();
		
		this.buttonCardFront = new JButton();
		this.buttonCardBack = new JButton();

		this.panelAnswer = new JPanel();		
		this.buttonCorrect = new JButton("Richtig");
		this.buttonWrong = new JButton("Falsch");
		paint();
	}
	
	public void paint()
	{
		this.labelStartLearning.setFont(MyFont.Ueberschrift1.getMyFont());
		this.add(labelStartLearning);
		this.add(comboboxBox);		
		
		this.panelAnswer.add(this.buttonCorrect);
		this.panelAnswer.add(this.buttonWrong);
		this.panelAnswer.setVisible(false);
		
		this.add(panelAnswer);
		this.setVisible(true);
	}
	
	public LearningView getLearningView()
	{
		return this;
	}

	public void initializeSettingsWithData(Register register) {	
		String [] s = {"todo Elias: Methode getBoxes in Klasse Register hinzufügen", "2", "3", "4"};
		this.comboboxBox = new JComboBox<String>(s);
		this.comboboxBox.setSelectedIndex(0);
	}
}
