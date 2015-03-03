package ch.zbw.lernkartei.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.zbw.lernkartei.controller.Controller.MeinButtonActionListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;

public class LearningView extends JPanel{

	private JLabel labelStartLearning;
	
	private JLabel labelFach;
	private JComboBox<String> comboboxFach;
	
	private JLabel labelFront;
	private JButton buttonCardFront;
	private JLabel labelBack;
	private JButton buttonCardBack;

	private JPanel panelAnswer;
	private JButton buttonCorrect;
	private JButton buttonWrong;
	
	private GridBagConstraints gridBagContraints;
	
	public LearningView()
	{
		this.setLayout(new GridBagLayout());
		
		this.labelStartLearning = new JLabel("Lernen starten");
		
		this.labelFach = new JLabel("Fach");
		this.comboboxFach = new JComboBox<String>();
		
		this.labelFront = new JLabel("Vorderseite");
		this.buttonCardFront = new JButton();
		this.labelBack = new JLabel("Rückseite");
		this.buttonCardBack = new JButton();

		this.panelAnswer = new JPanel();		
		this.buttonCorrect = new JButton("Richtig");
		this.buttonWrong = new JButton("Falsch");
		
		this.gridBagContraints = new GridBagConstraints();
		paint();
	}
	
	public void paint()
	{		
		gridBagContraints.insets = new Insets(0, 0, 40, 0);
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 0;
		this.labelStartLearning.setFont(MyFont.Ueberschrift1.getMyFont());
		this.add(labelStartLearning, gridBagContraints);
		
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 1;
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;
		this.labelFach.setFont(MyFont.Ueberschrift2.getMyFont());
		this.add(labelFach, gridBagContraints);
		
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 1;
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;
		this.comboboxFach.setPreferredSize(new Dimension(100, 20));
		this.add(this.comboboxFach, gridBagContraints);
		
		gridBagContraints.insets = new Insets(0, 0, 0, 0);
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 2;
		gridBagContraints.gridwidth = 5;
		this.labelFront.setFont(MyFont.Ueberschrift2.getMyFont());
		this.add(labelFront, gridBagContraints);
		this.labelBack.setFont(MyFont.Ueberschrift2.getMyFont());
		this.add(labelBack, gridBagContraints);
		
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 3;
		gridBagContraints.gridwidth = 5;
		this.buttonCardFront.setPreferredSize(new Dimension(250, 100));
		this.add(buttonCardFront, gridBagContraints);
		this.buttonCardBack.setPreferredSize(new Dimension(250, 100));
		this.add(buttonCardBack, gridBagContraints);
		
		gridBagContraints.insets = new Insets(20, 0, 0, 0);
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 4;
		
		this.panelAnswer.setPreferredSize(new Dimension(400, 100));
		this.panelAnswer.add(this.buttonCorrect);
		this.panelAnswer.add(this.buttonWrong);
		this.add(panelAnswer, gridBagContraints);
		
		this.labelBack.setVisible(false);
		this.buttonCardBack.setVisible(false);
		this.setVisible(true);
	}
	
	public LearningView getLearningView()
	{
		return this;
	}

	public void initializeSettingsWithData(ArrayList boxes, Register register) {			
		try {
			this.comboboxFach = new JComboBox(boxes.toArray());
			this.comboboxFach.setSelectedIndex(0);
			Card c = register.getCardByIndex(0);
			
			this.buttonCardFront.setText(c.getFront());
			this.buttonCardFront.setActionCommand("VORDERSEITE");
			
			this.buttonCardBack.setText(c.getBack());
			this.buttonCardBack.setActionCommand("RÜCKSEITE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.displayErrorMessage(e.getMessage());
		}
	}

	private void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void showAnswer() {
		this.labelFront.setVisible(false);
		this.labelBack.setVisible(true);
		this.buttonCardFront.setVisible(false);
		this.buttonCardBack.setVisible(true);
		this.buttonCorrect.setVisible(true);
		this.buttonWrong.setVisible(true);
	}

	public void ShowQuestion(ArrayList boxes, String front, String back) {
		this.comboboxFach = new JComboBox(boxes.toArray());
		this.comboboxFach.setSelectedIndex(0);
		
		this.labelFront.setVisible(true);
		this.labelBack.setVisible(false);
		this.buttonCardFront.setText(front);
		this.buttonCardBack.setText(back);
		
		this.buttonCardFront.setVisible(true);
		this.buttonCardBack.setVisible(false);
		this.buttonCorrect.setVisible(false);
		this.buttonWrong.setVisible(false);
	}

	public void setButtonFrontBackListener(MeinButtonActionListener l) {
		this.buttonCardFront.addActionListener(l);
		this.buttonCardBack.addActionListener(l);
	}
}
