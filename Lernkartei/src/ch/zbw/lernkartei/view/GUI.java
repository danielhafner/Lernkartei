package ch.zbw.lernkartei.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.zbw.lernkartei.controller.Controller.MeinButtonActionListener;
import ch.zbw.lernkartei.controller.Controller.MeinMenuActionListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.model.TranslationDataSet;

public class GUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private TranslationDataSet translationArrayList;
	private Language language;
	private Language previousLanguage;
	
	private JLabel labelImageIconBackground;
	
	private JMenu menuDatei;
	private JMenu menuSprache;
	private JMenuBar menuBar;
	
	private JMenuItem menuItemStartLearning;
	private JMenuItem menuItemSettings;
	private JMenuItem menuItemImport;
	private JMenuItem menuItemExport;
	private JMenuItem menuItemClose;
	private JMenuItem menuItemDeutsch;
	private JMenuItem menuItemFranzoesisch;
	private JMenuItem menuItemItalienisch;
	private JMenuItem menuItemEnglisch;
	
	private JPanel mainPanel;
	private JPanel panelSettings;
	private JLabel labelSettings;
	private JPanel panelStartLearning;
	private JLabel labelStartLearning;
	
	private JPanel panelNav;
	
	private JLabel labelRegisterName;
	private JTextField textfieldRegisterName;
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
	
	private Toolkit toolKit;	
	private JFileChooser jfImportFile;
	
	private int x = 0, y = 0, width = 800, heigth = 600;
	
	private GridBagConstraints gridBagContraints;
	
	private String fileExportPath;
	
	public GUI()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.gridBagContraints = new GridBagConstraints();
		this.mainPanel = new JPanel(new GridLayout(1, 1));
		this.menuBar = new JMenuBar();
		
		//Menu
		this.menuDatei = new JMenu("Datei");
		this.menuItemStartLearning = new JMenuItem("Lernen starten");
		this.menuItemSettings = new JMenuItem("Einstellungen");
		this.menuItemImport = new JMenuItem("Import");
		this.menuItemExport = new JMenuItem("Export");
		this.menuItemClose = new JMenuItem("Beenden");
		this.menuSprache = new JMenu("Sprache");
		this.menuItemDeutsch = new JMenuItem("Deutsch");
		this.menuItemFranzoesisch = new JMenuItem("Französisch");
		this.menuItemItalienisch = new JMenuItem("Italienisch");
		this.menuItemEnglisch = new JMenuItem("Englisch");
		
		this.translationArrayList = new TranslationDataSet();
		
		this.panelStartLearning = new JPanel();
		
		this.panelSettings = new JPanel(new GridBagLayout());
		this.labelSettings = new JLabel("Einstellungen");
		
		this.labelRegisterName = new JLabel("Karteiname");
		this.textfieldRegisterName = new JTextField(1);
		this.labelCardNumber = new JLabel("Karten-Nr.");
		this.textfieldCardNumber = new JTextField(1);

		this.labelFront = new JLabel("Vorderseite");
		this.labelFront.setFont(MyFont.Ueberschrift2.getMyFont());
		
		this.textAreaFront = new JTextArea(4, 20);
		this.labelBack = new JLabel("Rückseite");
		this.labelBack.setFont(MyFont.Ueberschrift2.getMyFont());
		this.textAreaBack = new JTextArea(4, 20);
		this.textAreaBack.setSize(300, 100);
		
		// Navigation: Neu, Löschen, Speichern, Zurück, Vorwärts
		this.panelNav = new JPanel(new FlowLayout());
		this.buttonNewCard = new JButton("Neu");
		this.buttonDeleteCard = new JButton("Löschen");
		this.buttonSaveCard = new JButton("Speichern");
		this.buttonNavBack = new JButton("<<<");
		this.buttonNavForward = new JButton(">>>");
		
		this.jfImportFile = new JFileChooser();		
		
		ImageIcon imageIconBackground = new ImageIcon("src/bg-sea.jpg");
		imageIconBackground.setDescription("Isch das en Scheiss");
		labelImageIconBackground = new JLabel(imageIconBackground);
	}
	
	public void paint()
	{		
		this.language = Language.Deutsch;
		setTitle("Vokabeltrainer R. Holderegger | E. Schwarz | D. Hafner");
		setIconImage(new ImageIcon("src/zbw.jpg").getImage());
		
		this.setJMenuBar(this.menuBar);	
		menuBar.add(menuDatei);
		menuBar.add(menuSprache);
		menuDatei.add(menuItemStartLearning);
		menuDatei.add(menuItemSettings);
		menuDatei.add(menuItemImport);
		menuDatei.add(menuItemExport);
		menuDatei.add(menuItemClose);
		menuSprache.add(menuItemDeutsch);
		menuSprache.add(menuItemFranzoesisch);
		menuSprache.add(menuItemItalienisch);
		menuSprache.add(menuItemEnglisch);
				
		this.panelNav.add(this.buttonNewCard);
		this.panelNav.add(this.buttonDeleteCard);
		this.panelNav.add(this.buttonSaveCard);
		this.panelNav.add(this.buttonNavBack);
		this.panelNav.add(this.buttonNavForward);
		
		gridBagContraints.insets = new Insets(0,0,20,0);
		gridBagContraints.fill = GridBagConstraints.HORIZONTAL;		
		
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 0;
		this.panelSettings.setBackground(Color.getColor("blue"));
		this.labelSettings.setFont(MyFont.Ueberschrift1.getMyFont());
		this.panelSettings.add(labelSettings, gridBagContraints);
		
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 1;
		this.panelSettings.add(labelRegisterName, gridBagContraints);
		
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 1;
		gridBagContraints.ipadx = 210;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldRegisterName.setEnabled(false);
		this.panelSettings.add(textfieldRegisterName, gridBagContraints);
		
		gridBagContraints.gridx = 0;
		gridBagContraints.gridy = 2;
		gridBagContraints.ipadx = 50;
		this.panelSettings.add(labelCardNumber, gridBagContraints);		
		
		gridBagContraints.fill = 0;
		gridBagContraints.gridx = 1;
		gridBagContraints.gridy = 2;
		gridBagContraints.ipadx = 100;
		gridBagContraints.anchor = GridBagConstraints.WEST;
		this.textfieldCardNumber.setEnabled(false);
		this.panelSettings.add(textfieldCardNumber, gridBagContraints);
		
		gridBagContraints.insets = new Insets(20,0,10,0);
		gridBagContraints.gridy = 5;
		gridBagContraints.ipadx = 1;
		this.panelSettings.add(this.labelFront, gridBagContraints);
		
		gridBagContraints.insets = new Insets(0,0,0,0);
		gridBagContraints.gridy = 6;
		gridBagContraints.ipadx = 1;

		this.panelSettings.add(this.textAreaFront, gridBagContraints);
		
		gridBagContraints.insets = new Insets(20,0,10,0);
		gridBagContraints.fill = 0;
		gridBagContraints.gridy = 7;
		gridBagContraints.ipadx = 1;
		this.panelSettings.add(this.labelBack, gridBagContraints);
		
		gridBagContraints.insets = new Insets(0,0,0,0);
		gridBagContraints.gridy = 8;
		gridBagContraints.ipadx = 1;

		this.panelSettings.add(this.textAreaBack, gridBagContraints);
		
		gridBagContraints.insets = new Insets(20,0,0,0);
		gridBagContraints.gridy = 12;
		gridBagContraints.ipadx = 1;
		this.panelSettings.add(panelNav, gridBagContraints);
		
		this.labelStartLearning = new JLabel("Lernen starten");
		this.labelStartLearning.setFont(MyFont.Ueberschrift1.getMyFont());
		this.panelStartLearning.add(labelStartLearning);
		this.panelStartLearning.setVisible(true);
		
		this.toolKit = Toolkit.getDefaultToolkit();
		Dimension d = toolKit.getScreenSize();
		x = (int) ((d.getWidth() - width) / 2);		// Gesamtbreite des Screens abz�glich Breite der Anwendung durch 2 ergibt die x-Anfangsposition.
		y = (int) ((d.getHeight() - heigth) / 2);	// Gesamth�he des Screens abz�glich H�he der Anwendung durch 2 ergibt y-Anfangsposition.
		setBounds(x, y, this.width, this.heigth);
		this.setMinimumSize(new Dimension(width, heigth));
		
		//Man kann auf einem Label ein Layout definieren...
		labelImageIconBackground.setLayout(new GridBagLayout());
		labelImageIconBackground.add(this.mainPanel);
		this.add(labelImageIconBackground);
	}

	public void paintSettingsPanel() 
	{
		//initializeSettings(this.register);
		this.repaintTheFrame(this.panelSettings);
	}
	
	public void initializeSettingsWithData(Register reg)
	{
		if(reg != null)
		{
			this.textfieldRegisterName.setText(reg.getTitle());
			try {
				this.textfieldCardNumber.setText(reg.getCards().get(0).getIdCard() + "");
				this.textAreaBack.setText(reg.getCards().get(0).getBack());
				this.textAreaFront.setText(reg.getCards().get(0).getFront());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
		else
		{
			
		}
	}

	public void paintPlayPanel()
	{
		repaintTheFrame(this.panelStartLearning);
	}
	
	private void repaintTheFrame(JPanel panelToShow) {				
		labelImageIconBackground.removeAll();
		this.mainPanel.removeAll();
		this.mainPanel.add(panelToShow);
		labelImageIconBackground.add(this.mainPanel);
		this.setVisible(true);
	}
	
	public void closeApplication()
	{
		// Anwendung beenden
		this.dispose();
		this.setVisible(false);
	}

	public void openFileDialog() 
	{
		switch(this.jfImportFile.showOpenDialog(this))
		{
		case JFileChooser.APPROVE_OPTION:
			JOptionPane.showMessageDialog(this, "todo Ruel: Import starten...");
			break;
		case JFileChooser.CANCEL_OPTION:
			//JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Import wurde abgebrochen.", Language.Deutsch, this.language));
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Fehler beim importieren der Daten", Language.Deutsch, this.language));
			break;
		}
	}
	
	public void saveFileDialog()
	{		
		switch(this.jfImportFile.showSaveDialog(this))
		{
		case JFileChooser.APPROVE_OPTION:
			
			//JOptionPane.showMessageDialog(this, "todo Ruel: Karten werden in ein File exportiert...");
			break;
		case JFileChooser.CANCEL_OPTION:
			//JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Export wurde abgebrochen.", Language.Deutsch, this.language));
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Fehler beim exportieren der Karten", Language.Deutsch, this.language));
			break;
		}
	}
	
	public void setLanguageMenuActionListener(MeinMenuActionListener listener) {
		
		if(listener.command.equals(Language.Deutsch.toString()))
		{
			this.menuItemDeutsch.addActionListener(listener);	
		}
		else if(listener.command.equals(Language.Englisch.toString()))
		{
			this.menuItemEnglisch.addActionListener(listener);
		}
		else if(listener.command.equals(Language.Französisch.toString()))
		{
			this.menuItemFranzoesisch.addActionListener(listener);
		}
		else if(listener.command.equals(Language.Italienisch.toString()))
		{
			this.menuItemItalienisch.addActionListener(listener);
		}
	}
	
	public void setStartMenuActionListener(MeinMenuActionListener listener) 
	{
		this.menuItemClose.addActionListener(listener);
	}

	public void setSettingsMenuActionListener(MeinMenuActionListener listener)
	{
		this.menuItemSettings.addActionListener(listener);
	}
	
	public void setStartLearningsMenuActionListener(MeinMenuActionListener listener)
	{
		this.menuItemStartLearning.addActionListener(listener);
	}
	
	public void setImportMenuActionListener(MeinMenuActionListener listener)
	{
		this.menuItemImport.addActionListener(listener);
	}
	
	public void setExportMenuActionListener(MeinMenuActionListener listener)
	{
		this.menuItemExport.addActionListener(listener);
	}
	
	public void setNavigationButtonListener(MeinButtonActionListener listener)
	{
		this.buttonNewCard.addActionListener(listener);
		this.buttonDeleteCard.addActionListener(listener);
		this.buttonSaveCard.addActionListener(listener);
		this.buttonNavBack.addActionListener(listener);
		this.buttonNavForward.addActionListener(listener);
	}

	public void switchLanguage(ActionEvent e) 
	{
		switch ((e.getActionCommand()))
		{
		case "Deutsch" : setControlsToBeTranslated(Language.Deutsch);
			break;
		case "Italienisch": setControlsToBeTranslated(Language.Italienisch);
			break;
		case "Französisch": setControlsToBeTranslated(Language.Französisch);
			break;
		case "Englisch": setControlsToBeTranslated(Language.Englisch);
			break;
		default: setControlsToBeTranslated(Language.Deutsch);
			break;
		}
	}
	
	public void translateTextOfAControl(Object o)
	{			
			for (String[] elem : this.translationArrayList)
			{
				if(o instanceof JMenuItem)
				{	
					if(elem[this.previousLanguage.value].equals(((JMenuItem)o).getText()))
					{
						((JMenuItem)o).setText(elem[this.language.value]);
						
						// Actioncommand  muss neu gesetzt werden, damit die 
						// deutschen Befehle NICHT mit der fremdsprachigen überschrieben werden!
						((JMenuItem)o).setActionCommand(elem[Language.Deutsch.value]);
						break;
					}
				}
				
				else if(o instanceof JMenu)
				{	
					if(elem[this.previousLanguage.value].equals(((JMenu)o).getText()))
					{
						((JMenu)o).setText(elem[this.language.value]);
						
						// Actioncommand  muss neu gesetzt werden, damit die 
						// deutschen Befehle NICHT mit der fremdsprachigen überschrieben werden!
						((JMenu)o).setActionCommand(elem[Language.Deutsch.value]);
						break;
					}
				}
				
				else if(o instanceof JLabel)
				{	
					if(elem[this.previousLanguage.value].equals(((JLabel)o).getText()))
					{
						((JLabel)o).setText(elem[this.language.value]);
						break;
					}
				}
				
				else if(o instanceof JFrame)
				{
					if(elem[this.previousLanguage.value].equals(((JFrame)o).getTitle()))
					{
						((JFrame)o).setTitle(elem[this.language.value]);
						break;
					}
				}
			}
		}
	
	
	public static ArrayList<Component> getAllComponents(final Container c) {
	    Component[] comps = c.getComponents();
	    ArrayList<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	            compList.addAll(getAllComponents((Container) comp));
	    }
	    return compList;
	}
	
	public void setControlsToBeTranslated(Language newLanguage)
	{
		this.previousLanguage = this.language;
		this.language = newLanguage;

		ArrayList<Component> a = getAllComponents(this.mainPanel);
		
		for (Component c : a)
		{
			translateTextOfAControl(c);
		}
	}

	public void initializeControls() {
		setButtonState(this.buttonNewCard, false);
		setButtonState(this.buttonDeleteCard, false);
		setButtonState(this.buttonSaveCard, false);
		setButtonState(this.buttonNavBack, false);
		setButtonState(this.buttonNavForward, false);
	}
	
	public void setButtonState(JButton button, boolean isEnabled)
	{
		button.setEnabled(isEnabled);
	}
	
	public String getFrontText()
	{
		return this.textAreaFront.getText();
	}
	
	public String getBackText()
	{
		return this.textAreaBack.getText();
	}
	
	public void displayErrorMessage(String error)
	{
		JOptionPane.showMessageDialog(this, error);
	}

	public void displayNewCard(Card card, int cardId) {
		
		this.textAreaFront.setText(card.getFront());
		this.textAreaBack.setText(card.getBack());
		this.textfieldCardNumber.setText(cardId + "");
		
		
		//todo!
		//this.textfieldRegisterName.setText(register.getRegisterName());
	}

	public Integer getCardid() {
		
		if(this.textfieldCardNumber.getText() != null)
		{
			return  Integer.parseInt(this.textfieldCardNumber.getText());
		}
		
		return 0;
	}

	public void setStateDeleteButton(boolean state) {
		this.buttonDeleteCard.setEnabled(state);
	}
	
	public String getFileExportfile()
	{
		return this.fileExportPath;
	}
}