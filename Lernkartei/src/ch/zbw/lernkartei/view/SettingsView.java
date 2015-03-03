package ch.zbw.lernkartei.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
	
	public SettingsView()
	{
		this.setLayout(new GridBagLayout());
		this.labelSettings = new JLabel("Einstellungen");
		
		//this.labelRegisterName = new JLabel("Karteiname");
		//this.textfieldRegisterName = new JTextField(1);
		this.labelCardNumber = new JLabel("Karten-Nr.");
		this.textfieldCardNumber = new JTextField(1);

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
		this.add(labelCardNumber, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 2;
		gridBagContraints.ipadx = 100;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldCardNumber.setEnabled(false);
		this.add(textfieldCardNumber, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(20, 0, 10, 0);
		gridBagContraints.gridy = 5;
		gridBagContraints.ipadx = 1;
		this.add(this.labelFront, gridBagContraints);

		gridBagContraints.fill = 0;
		gridBagContraints.insets = new Insets(0, 0, 0, 0);
		gridBagContraints.gridy = 6;
		gridBagContraints.ipadx = 1;
		this.textAreaFront.setPreferredSize(new Dimension(250, 100));
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
		this.textAreaBack.setPreferredSize(new Dimension(250, 100));
		this.textAreaBack.setLineWrap(true);
		this.textAreaBack.setWrapStyleWord(true);
		this.add(this.textAreaBack, gridBagContraints);

		gridBagContraints.insets = new Insets(20, 0, 0, 0);
		gridBagContraints.gridy = 12;
		gridBagContraints.ipadx = 1;
		this.add(panelNav, gridBagContraints);
		
		setMaximumSize(new Dimension(800, 600));
		this.setVisible(true);
	}
	
	public void setTextTextFieldCardNumber(String text)
	{
		this.textfieldCardNumber.setText(text);
	}
	
	public void setNavigationButtonListener(MeinButtonActionListener listener) {
		this.buttonNewCard.addActionListener(listener);
		this.buttonDeleteCard.addActionListener(listener);
		this.buttonSaveCard.addActionListener(listener);
		this.buttonNavBack.addActionListener(listener);
		this.buttonNavForward.addActionListener(listener);
	}
	
	public void initializeSettingsWithData(Register reg) {
		if (reg != null) {
			try {
				this.setTextTextFieldCardNumber(reg.getCards().get(0).getIdCard()+ "");
				this.textAreaBack.setText(reg.getCards().get(0).getBack());
				this.textAreaFront.setText(reg.getCards().get(0).getFront());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		} else {
		}
	}
	
	public void displayCard(Card card, int cardId) {

		this.textAreaFront.setText(card.getFront());
		this.textAreaBack.setText(card.getBack());
		this.textfieldCardNumber.setText(cardId + "");
	}
	
	public Integer getCardid() {

		if (this.textfieldCardNumber.getText() != null) {
			return Integer.parseInt(this.textfieldCardNumber.getText());
		}

		return 0;
	}

	public void setStateDeleteButton(boolean state) {
		this.buttonDeleteCard.setEnabled(state);
	}

	public void setStateSaveButton(boolean state) {
		this.buttonSaveCard.setEnabled(state);
	}

	public void setStateButtonBack(boolean state) {
		this.buttonNavBack.setEnabled(state);
	}

	public void setStateButtonForward(boolean state) {
		this.buttonNavForward.setEnabled(state);
	}
	
	public int showMessageBox(String text) {
		return JOptionPane.showConfirmDialog(this, text);
	}

	public void setDocumentListener(DocumentListener documentListener) {
		this.textAreaFront.getDocument().addDocumentListener(documentListener);
		this.textAreaBack.getDocument().addDocumentListener(documentListener);
	}

	public void setStateButtonNew(boolean state) {
		this.buttonNewCard.setEnabled(state);
	}

	public void setStateNavBackForwardButtons(Register reg, int index) {
		if (index == 0)
			this.buttonNavBack.setEnabled(false);
		else
			this.buttonNavBack.setEnabled(true);
		if (index < reg.getNumberOfCards())
			setStateButtonForward(true);
		else
			setStateButtonForward(false);
	}
	
	public String getFrontText() {
		return this.textAreaFront.getText();
	}

	public String getBackText() {
		return this.textAreaBack.getText();
	}

	public void setInitialFocus() {
		this.textAreaFront.requestFocus();
	}
}
