package ch.zbw.lernkartei.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import ch.zbw.lernkartei.controller.Controller.MeinButtonActionListener;
import ch.zbw.lernkartei.controller.Controller.MyComboboxItemListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;

/**
 * @author Ruel
 *
 */
public class LearningView extends JPanel{

	private static final long serialVersionUID = -7401960373296431225L;
	private int cardId;
	private JLabel labelStartLearning;
	
	private JLabel labelFach;
	private JComboBox<Integer> comboboxFach;
	
	private JLabel labelFront;
	private JButton buttonCardFront;
	private JLabel labelBack;
	private JButton buttonCardBack;

	private JPanel panelAnswer;
	private JButton buttonCorrect;
	private JButton buttonWrong;
	
	private JPanel panelStatusBar;
	
	private JPanel panelCountCardsOfABox;
	private JLabel labelActualCardOfBox;
	private JLabel labelActualCardOfBoxResult;	
	
	private JPanel panelCountTotalCards;
	private JLabel labelTotalCards;
	private JLabel labelTotalCardsResult;
	
	private JPanel panelQuotaCorrect;
	private JLabel labelQuotaCorrect;
	private JLabel labelQuotaCorrectResult;
	
	private JPanel panelQuotaWrong;
	private JLabel labelQuotaWrong;
	private JLabel labelQuotaWrongResult;
	
	private int targetBox = 0;
	private JProgressBar progressBar;
	private MyRunnableProgressBar paintProgressBar;
	private Thread progressBarThread;
	private int percentProgressBar = 0;
	
	private GridBagConstraints gridBagContraints;
	
	/**
	 * Konstructor
	 */
	public LearningView()
	{
		this.setLayout(new GridBagLayout());
		this.setMaximumSize(MyDimension.MaximumSize.get());
		
		this.labelStartLearning = new JLabel("Lernen starten");
		
		this.labelFach = new JLabel("Fach");
		this.comboboxFach = new JComboBox<Integer>();
		
		this.labelFront = new JLabel("Vorderseite");
		this.buttonCardFront = new JButton();
		this.labelBack = new JLabel("Rückseite");
		this.buttonCardBack = new JButton();

		this.panelAnswer = new JPanel();
		ImageIcon imageIconCorrect = new ImageIcon(this.getClass().getResource("/correct.png"));
		this.buttonCorrect = new JButton("", imageIconCorrect);
		this.buttonCorrect.setActionCommand("Richtig");
		ImageIcon imageIconWrong = new ImageIcon(this.getClass().getResource("/wrong.png"));
		this.buttonWrong = new JButton("", imageIconWrong);
		this.buttonWrong.setActionCommand("Falsch");
		
		this.panelStatusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		this.panelCountCardsOfABox = new JPanel(new BorderLayout());
		this.labelActualCardOfBox = new JLabel("Anzahl Karten   ");
		this.labelActualCardOfBoxResult = new JLabel("-");
		
		this.panelCountTotalCards = new JPanel(new BorderLayout());
		this.labelTotalCards = new JLabel("Total Karten   ");
		this.labelTotalCardsResult = new JLabel("-");
		
		this.panelQuotaCorrect = new JPanel(new BorderLayout());
		this.labelQuotaCorrect = new JLabel("Anteil korrekt   ");
		this.labelQuotaCorrectResult = new JLabel("-");
		
		this.panelQuotaWrong = new JPanel(new BorderLayout());
		this.labelQuotaWrong = new JLabel("Anteil falsch   ");
		this.labelQuotaWrongResult = new JLabel("-");
		
		this.progressBar = new JProgressBar();
		this.paintProgressBar = new MyRunnableProgressBar(progressBar, this);
		this.progressBarThread = new Thread(paintProgressBar, "Mein Fortschritt");
		
		this.gridBagContraints = new GridBagConstraints();
		paint();
	}
	
	/**
	 *  Paints the LearningView
	 */
	public void paint()
	{		
		gridBagContraints.insets = new Insets(0, 0, 30, 0);
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
		
		gridBagContraints.insets = new Insets(0, 0, 20, 0);
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
		this.buttonCardFront.setPreferredSize(MyDimension.LearningFrontBackDimension.get());
		//this.buttonCardFront.setBackground(Color.BLUE);
		this.buttonCardFront.setBorder(new BevelBorder(5, Color.BLUE, Color.BLUE ));
		this.buttonCardFront.setForeground(Color.BLUE);
		this.add(buttonCardFront, gridBagContraints);
		this.buttonCardBack.setPreferredSize(MyDimension.LearningFrontBackDimension.get());
		//this.buttonCardBack.setBackground(new Color(0,100,0));
		this.buttonCardBack.setBorder(new BevelBorder(5, new Color(0,100,0), new Color(0,100,0)));
		this.buttonCardBack.setForeground(new Color(0,100,0));
		this.add(buttonCardBack, gridBagContraints);
		
		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(0, 0, 0, 0);
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 4;
		this.panelAnswer.setPreferredSize(new Dimension(500, 80));		

		this.buttonCorrect.setPreferredSize(MyDimension.ButtonCorrectWrongDimension.get());		
		this.buttonCorrect.setHorizontalAlignment(SwingConstants.CENTER); // Damit Text linksbündig angezeigt wird
		this.panelAnswer.add(this.buttonCorrect);
		this.buttonWrong.setHorizontalAlignment(SwingConstants.CENTER); // Damit Text linksbündig angezeigt wird
		this.buttonWrong.setPreferredSize(MyDimension.ButtonCorrectWrongDimension.get());	
		this.panelAnswer.add(this.buttonWrong);
		this.add(panelAnswer, gridBagContraints);

		this.labelBack.setVisible(false);
		this.buttonCardBack.setVisible(false);
		this.buttonCorrect.setVisible(false);
		this.buttonWrong.setVisible(false);
		
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 5;
		
		this.panelCountCardsOfABox.add(labelActualCardOfBox, BorderLayout.NORTH);
		this.panelCountCardsOfABox.add(labelActualCardOfBoxResult, BorderLayout.SOUTH);
		
		this.panelCountTotalCards.add(labelTotalCards, BorderLayout.NORTH);
		this.panelCountTotalCards.add(labelTotalCardsResult, BorderLayout.SOUTH);
		
		this.panelQuotaCorrect.add(labelQuotaCorrect, BorderLayout.NORTH);
		this.panelQuotaCorrect.add(labelQuotaCorrectResult, BorderLayout.SOUTH);
		
		this.panelQuotaWrong.add(labelQuotaWrong, BorderLayout.NORTH);
		this.panelQuotaWrong.add(labelQuotaWrongResult, BorderLayout.SOUTH);
		
		this.panelStatusBar.add(panelCountCardsOfABox);
		this.panelStatusBar.add(panelCountTotalCards);
		this.panelStatusBar.add(panelQuotaCorrect);
		this.panelStatusBar.add(panelQuotaWrong);
		
		gridBagContraints.gridy = 6;
		this.add(panelStatusBar, gridBagContraints);	
		
		gridBagContraints.gridy = 7;
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.progressBar, gridBagContraints);
		
		this.setVisible(true);
	}
	
	/**
	 * @return Returns the LerningView
	 */
	public LearningView getLearningView()
	{
		return this;
	}

	
	/**Initialize the LearningView with Data
	 * @param boxes
	 * @param register
	 */
	public void initializeWithData(ArrayList<Integer> boxes, Register register) {			
		try {
			java.util.Collections.sort(boxes);
			this.comboboxFach.removeAllItems();
			Iterator<Integer> it = boxes.iterator();	
			while(it.hasNext())
			{
				Integer i = it.next();
				if(i != null)
				{
					this.comboboxFach.addItem(i);
				}
			}		
			Card c = register.getCardByIndex(0);
			this.cardId = c.getIdCard();
			this.buttonCardFront.setText(c.getFront());
			this.buttonCardFront.setActionCommand("VORDERSEITE");
			
			this.buttonCardBack.setText(c.getBack());
			this.buttonCardBack.setActionCommand("RÜCKSEITE");
		} catch (Exception e) {
			this.displayErrorMessage(e.getMessage());
		}
	}

	/** Displays an Error-Message as into a Message Dialog
	 * @param message
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	
	/**
	 * Shows the Answer (Back)
	 */
	public void showAnswer() {
		this.labelFront.setVisible(false);
		this.labelBack.setVisible(true);
		this.buttonCardFront.setVisible(false);
		this.buttonCardBack.setVisible(true);
		this.buttonCorrect.setVisible(true);
		this.buttonWrong.setVisible(true);
		this.setFocus(this.buttonCorrect);
	}

	
	/** Shows the Question (Front)
	 * @param front
	 * @param back
	 * @param cardId
	 */
	public void ShowQuestion(String front, String back, int cardId) {		
		this.cardId = cardId;
		this.labelFront.setVisible(true);
		this.labelBack.setVisible(false);
		this.buttonCardFront.setText(front);
		this.buttonCardBack.setText(back);
		this.buttonCardFront.setVisible(true);
		this.buttonCardBack.setVisible(false);		
		this.buttonCorrect.setVisible(false);
		this.buttonWrong.setVisible(false);
	}

	
	/** Sets the Actionlistener to the Buttons
	 * @param l
	 */
	public void setButtonFrontBackListener(MeinButtonActionListener l) {
		this.buttonCardFront.addActionListener(l);
		this.buttonCardBack.addActionListener(l);
		this.buttonCorrect.addActionListener(l);
		this.buttonWrong.addActionListener(l);
	}
	
	/** Sets the ItemListener to the Combobox
	 * @param m
	 */
	public void setComboboxItemListener(MyComboboxItemListener m)
	{
		this.comboboxFach.addItemListener(m);  // Der Einfachheit halber wird für die Combobox der MeinButtonActionListener angehängt...
	}
	

	/** Removes the ActionListener of the Answer (Back)
	 * @param l
	 */
	public void removeButtonFrontBackListener(MeinButtonActionListener l)
	{
		this.buttonCardBack.removeActionListener(l);
	}
	

	/**
	 * @return Returns the card-Id
	 */
	public int getCardId()
	{
		return this.cardId;
	}


	/** Sets the Card-Id
	 * @param id
	 */
	public void setCardId(int id)
	{
		this.cardId = id;
	}
	
	/** Gets the Selected Combobox-Item
	 * @return Value (1,2,3...) as a String
	 */
	public String getComboBoxSelectedItem()
	{
		return this.comboboxFach.getSelectedItem().toString();
	}

	/** Refreshs the Combobox with the Data
	 * @param boxes
	 */
	public void refreshComboboxFachWithData(ArrayList<Integer> boxes) {
		if(boxes != null)
		{
			// Zuletzt selektiertes Fach zwischenspeichern
			Object o = this.comboboxFach.getSelectedItem();
			this.comboboxFach.removeAllItems();
			Iterator<Integer> it = boxes.iterator();
			while(it.hasNext())
			{
				Integer i = it.next();
				if(i != null)
				{
					this.comboboxFach.addItem(i);
				}
			}
			if (o != null)
			{
				comboboxFach.setSelectedItem(o);
			}
		}
	}
	
	/** Sets the Statistics
	 * @param totalCardsOfBox
	 * @param totalCards
	 * @param quotaCorrect
	 * @param quotaWrong
	 */
	public void setStatistics(int totalCardsOfBox, int totalCards, int quotaCorrect, int quotaWrong)
	{
		this.labelActualCardOfBoxResult.setText(totalCardsOfBox + "");
		this.labelTotalCardsResult.setText(totalCards + "");
		this.labelQuotaCorrectResult.setText(quotaCorrect + "%");
		this.labelQuotaWrongResult.setText(quotaWrong + "%");
	}
	
	/**
	 * @return gets the Box-Number
	 */
	public int getBox()
	{
		return (Integer.parseInt(this.comboboxFach.getSelectedItem().toString()));
	}
	
	/** Sets the target box
	 * @param targetBox
	 */
	public void setTargetBox(int targetBox)
	{
		this.targetBox = targetBox;
	}
	
	/**
	 * @return Gets the target Box
	 */
	public int getTargetBox()
	{
		return this.targetBox;
	}
	
	/** Sets percent of the progress bar
	 * @param percent
	 */
	public void setPercentProgressBar(int percent)
	{
		this.percentProgressBar = percent;
	}
	
	/**
	 * @return Gets percent of the progress bar
	 */
	public int getPercentProgressBar()
	{
		return this.percentProgressBar;
	}
	
	/**
	 * Activates the progress bar thread
	 */
	public void activateProgressBarThread(){
		
		if(!this.progressBarThread.isAlive())
		{
			progressBarThread = new Thread(paintProgressBar, "Mein Fortschritt");
			progressBarThread.start();
		}
	}
	
	/**
	 * Disables the progress bar thread
	 */
	public void disableProgressBarThread()
	{
		this.progressBar.repaint();
	}
	
	/** Sets the focus on a component
	 * @param component
	 */
	public void setFocus(Component component)
	{
		if(component != null && component.isFocusable())
		{
			component.requestFocus();
		}
	}

	/**
	 * @return Gets the Button Card Front
	 */
	public JButton getButtonCardFront() {
		return this.buttonCardFront;
	}

	/**
	 * @return Gets the Button Correct
	 */
	public JButton getButtonCorrect() {
		return this.buttonCorrect;
	}
}