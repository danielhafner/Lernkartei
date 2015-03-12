package ch.zbw.lernkartei.view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import ch.zbw.lernkartei.controller.Controller.MeinButtonActionListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Register;

public class SettingsView extends JPanel {

	private JLabel labelSettings;
	private JPanel panelNav;

	//private JLabel labelRegisterName;
	//private JTextField textfieldRegisterName;
	private JLabel labelCardNumber;
	private JTextField textfieldCardNumber;
	
	private JLabel labelBoxNumber;
	private JTextField textfieldBoxNumber;

	private JLabel labelFront;
	private JTextArea textAreaFront;
	private JLabel labelBack;
	private JTextArea textAreaBack;

	private JButton buttonNewCard;
	private JButton buttonDeleteCard;
	private JButton buttonSaveCard;
	private JButton buttonNavBack;
	private JButton buttonNavForward;
	
	private GridBagConstraints gridBagContraints;
	
	/**
	 * Konstructor
	 */
	public SettingsView()
	{
		this.setLayout(new GridBagLayout());
		this.labelSettings = new JLabel("Einstellungen");
		
		//this.labelRegisterName = new JLabel("Karteiname");
		//this.textfieldRegisterName = new JTextField(1);
		this.labelCardNumber = new JLabel("Karten-Nr.");
		this.textfieldCardNumber = new JTextField(1);
		
		this.labelBoxNumber = new JLabel("Fach");
		this.textfieldBoxNumber = new JTextField(1);
		
		this.labelFront = new JLabel("Vorderseite");
		this.labelFront.setFont(MyFont.Ueberschrift2.getMyFont());
		this.textAreaFront = new JTextArea(4, 1);
		
		this.labelBack = new JLabel("Rückseite");
		this.labelBack.setFont(MyFont.Ueberschrift2.getMyFont());
		this.textAreaBack = new JTextArea(4, 1);

		// Navigation: Neu, Löschen, Speichern, Zurück, Vorwärts
		this.panelNav = new JPanel(new FlowLayout());
		this.buttonNewCard = new JButton("Neu");
		this.buttonDeleteCard = new JButton("Löschen");
		this.buttonSaveCard = new JButton("Speichern");
		this.buttonNavBack = new JButton("<<<");
		this.buttonNavForward = new JButton(">>>");
		
		this.gridBagContraints = new GridBagConstraints();
		paint();
	}
	
	/**
	 *  Paints the SettingView
	 */
	public void paint(){
		
		this.panelNav.add(this.buttonNewCard);
		this.panelNav.add(this.buttonDeleteCard);
		this.panelNav.add(this.buttonSaveCard);
		this.panelNav.add(this.buttonNavBack);
		this.panelNav.add(this.buttonNavForward);

		gridBagContraints.insets = new Insets(0, 0, 20, 0);
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;

		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 0;
		this.labelSettings.setFont(MyFont.Ueberschrift1.getMyFont());
		this.add(labelSettings, gridBagContraints);

		/* Registername vorläufig nicht implementieren */
		/*gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 1;
		this.add(labelRegisterName, gridBagContraints);
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 1;
		gridBagContraints.ipadx = 210;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldRegisterName.setEnabled(false);
		this.add(textfieldRegisterName, gridBagContraints);*/

		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 2;
		gridBagContraints.ipadx = 50;
		gridBagContraints.anchor = GridBagConstraints.WEST; //Damit das Label ganz links steht...
		this.labelCardNumber.setFont(MyFont.Ueberschrift3.getMyFont());
		this.add(labelCardNumber, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 2;
		gridBagContraints.ipadx = 100;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldCardNumber.setEnabled(false);
		this.add(textfieldCardNumber, gridBagContraints);
		
	
		// Label Box-Number
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 3;
		gridBagContraints.ipadx = 50;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.labelBoxNumber.setFont(MyFont.Ueberschrift3.getMyFont());
		this.add(labelBoxNumber, gridBagContraints);
	
		// Textfiel Box-Number
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 3;
		gridBagContraints.ipadx = 100;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldBoxNumber.setEnabled(false);
		this.add(textfieldBoxNumber, gridBagContraints);
		

		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(20, 0, 10, 0);
		gridBagContraints.gridy = 5;
		gridBagContraints.ipadx = 1;
		this.add(this.labelFront, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(0, 0, 0, 0);
		gridBagContraints.gridy = 6;
		gridBagContraints.ipadx = 1;
		this.textAreaFront.setPreferredSize(MyDimension.TextAreaDimension.get());
		this.textAreaFront.setLineWrap(true);
		this.textAreaFront.setWrapStyleWord(true);
		this.add(this.textAreaFront, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(20, 0, 10, 0);
		gridBagContraints.gridy = 7;
		gridBagContraints.ipadx = 1;
		this.add(this.labelBack, gridBagContraints);

		gridBagContraints.insets = new Insets(0, 0, 0, 0);
		gridBagContraints.gridy = 8;
		gridBagContraints.ipadx = 1;
		this.textAreaBack.setPreferredSize(MyDimension.TextAreaDimension.get());
		this.textAreaBack.setLineWrap(true);
		this.textAreaBack.setWrapStyleWord(true);
		this.add(this.textAreaBack, gridBagContraints);

		gridBagContraints.insets = new Insets(20, 0, 0, 0);
		gridBagContraints.gridy = 12;
		gridBagContraints.ipadx = 1;
		this.add(panelNav, gridBagContraints);
		
		setMaximumSize(MyDimension.MaximumSize.get());
		this.setVisible(true);
	}
	
	/** Sets the Card-No. Textfield
	 * @param text
	 */
	public void setTextTextFieldCardNumber(String text)
	{
		this.textfieldCardNumber.setText(text);
	}
	
	/** Sets the ActionListeners for all Buttons
	 * @param listener
	 */
	public void setNavigationButtonListener(MeinButtonActionListener listener) {
		this.buttonNewCard.addActionListener(listener);
		this.buttonDeleteCard.addActionListener(listener);
		this.buttonSaveCard.addActionListener(listener);
		this.buttonNavBack.addActionListener(listener);
		this.buttonNavForward.addActionListener(listener);
	}
	
	/** Displays a card
	 * @param card
	 */
	public void displayCard(Card card) {
		this.textAreaFront.setText(card.getFront());
		this.textAreaBack.setText(card.getBack());
		this.textfieldCardNumber.setText(card.getIdCard() + "");
	}
	
	/** Return the Id of the Card
	 * @return
	 */
	public Integer getCardid() {

		if (this.textfieldCardNumber.getText() != null) {
			return Integer.parseInt(this.textfieldCardNumber.getText());
		}
		return 0;
	}

	/** Sets the Button-State of the Delete-Button
	 * @param state
	 */
	public void setStateDeleteButton(boolean state) {
		this.buttonDeleteCard.setEnabled(state);
	}

	/** Sets the State of the Save-Button
	 * @param state
	 */
	public void setStateSaveButton(boolean state) {
		this.buttonSaveCard.setEnabled(state);
	}

	/** Sets the State of the Back-Button
	 * @param state
	 */
	public void setStateButtonBack(boolean state) {
		this.buttonNavBack.setEnabled(state);
	}

	/** Sets the State of the Forward-Button
	 * @param state
	 */
	public void setStateButtonForward(boolean state) {
		this.buttonNavForward.setEnabled(state);
	}
	
	/** Shows a Message-Box
	 * @param text to be displayd
	 * @return
	 */
	public int showMessageBox(String text) {
		return JOptionPane.showConfirmDialog(this, text);
	}

	/** Sets the DocumentListener for the Textfields Front and Back
	 * @param documentListener
	 */
	public void setDocumentListener(DocumentListener documentListener) {
		this.textAreaFront.getDocument().addDocumentListener(documentListener);
		this.textAreaBack.getDocument().addDocumentListener(documentListener);
	}

	/** Sets the State of the New-Button
	 * @param state
	 */
	public void setStateButtonNew(boolean state) {
		this.buttonNewCard.setEnabled(state);
	}

	/** Sets state of the Navigation Buttions (Back <<< and Forward >>>)
	 * @param reg
	 * @param index
	 */
	public void setStateNavBackForwardButtons(Register reg, int index) {
		if (index == 0)
			this.buttonNavBack.setEnabled(false);
		else
			this.buttonNavBack.setEnabled(true);
		if (index < reg.getNumberOfCards() - 1)
			setStateButtonForward(true);
		else
			setStateButtonForward(false);
	}
	
	/** 
	 * @return Text of the Fontside (Question)
	 */
	public String getFrontText() {
		return this.textAreaFront.getText();
	}

	/**
	 * @return Text of the Backside (Answer)
	 */
	public String getBackText() {
		return this.textAreaBack.getText();
	}

	/**
	 * Sets the initial Focus to the Textarea Front
	 */
	public void setInitialFocus() {
		this.textAreaFront.requestFocus();
	}
	
	/** Sets the TextArea Front with a certain text
	 * @param s
	 */
	public void setTextAreaFront(String s)
	{
		this.textAreaFront.setText(s);
	}
	
	/** Sets the TextArea Back with a certain text
	 * @param s
	 */
	public void setTextAreaBack(String s)
	{
		this.textAreaBack.setText(s);
	}
	
	/** Sets the BoxNumber with a certain Text
	 * @param s
	 */
	public void setBoxNumber(String s)
	{
		this.textfieldBoxNumber.setText(s);
	}
	
	/**
	 * @return the Box Number
	 */
	public int getBoxNumber()
	{
		if(!this.textfieldBoxNumber.getText().equals(""))
		{
			return Integer.parseInt(this.textfieldBoxNumber.getText() + "");
		}
		else
		{
			return 1;
		}
	}
}
