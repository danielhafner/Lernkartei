package ch.zbw.lernkartei.view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Visibility;
import java.util.ArrayList;
import java.util.FormatterClosedException;
import java.util.Iterator;

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
import javax.swing.JWindow;

import ch.zbw.lernkartei.controller.controller;
import ch.zbw.lernkartei.controller.controller.MeinMenuActionListener;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.TranslationDataSet;

public class GUI extends JFrame{
	
	private TranslationDataSet translationArrayList;
	private Language language;
	private Language previousLanguage;
	
	//Menü
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
	
	private JPanel panelMainPanel;
	private JPanel panelSettings;
	private JLabel labelSettings;
	private JPanel panelStartLearning;
	private JLabel labelStartLearning;
	
	private JPanel panelFront;
	private JPanel panelBack;
	private JPanel panelNav;
	
	private JLabel labelCardNumber;
	private JLabel labelFront;
	private JTextArea textfieldFront;
	private JLabel labelBack;
	private JTextArea textfieldBack;
	
	private JButton buttonNewCard;
	private JButton buttonDeleteCard;
	private JButton buttonSaveCard;
	private JButton buttonNavBack;
	private JButton buttonNavForward;
	
	
	private JFileChooser jfImportFile;
	
	public GUI()
	{
		this.language = Language.Deutsch;
		this.setName("Karteikarte Version 1.0");
		this.menuBar = new JMenuBar();
		
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
		//this.language = Sprache.Deutsch;
		
		this.panelMainPanel = new JPanel();
		
		this.panelStartLearning = new JPanel(new BorderLayout());
		
		this.labelSettings = new JLabel("Einstellungen");
		this.labelCardNumber = new JLabel("Karten-Nr.:");
		this.panelSettings = new JPanel(new BorderLayout());
		
		this.panelFront = new JPanel(new BorderLayout());		
		this.labelFront = new JLabel("Vorderseite");
		this.textfieldFront = new JTextArea(4, 4);
		this.panelBack = new JPanel(new BorderLayout());
		this.labelBack = new JLabel("Rückseite");
		this.textfieldBack = new JTextArea(4, 4);
		this.textfieldBack.setSize(300, 100);
		
		this.panelNav = new JPanel(new FlowLayout());
		this.buttonNewCard = new JButton("Neu");
		this.buttonDeleteCard = new JButton("Löschen");
		this.buttonSaveCard = new JButton("Speichern");
		this.buttonNavBack = new JButton("<<<");
		this.buttonNavForward = new JButton(">>>");
		
		this.jfImportFile = new JFileChooser();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void paint()
	{
		this.setSize(1024, 768);
		
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(menuBar, BorderLayout.NORTH);
		this.add(panelMainPanel, BorderLayout.CENTER);		
		menuBar.add(menuDatei);
		menuBar.add(menuSprache);
		menuBar.setSize(60, 20);
		menuDatei.add(menuItemStartLearning);
		menuDatei.add(menuItemSettings);
		menuDatei.add(menuItemImport);
		menuDatei.add(menuItemExport);
		menuDatei.add(menuItemClose);
		menuSprache.add(menuItemDeutsch);
		menuSprache.add(menuItemFranzoesisch);
		menuSprache.add(menuItemItalienisch);
		menuSprache.add(menuItemEnglisch);
		
		this.panelSettings.setSize(1024, 768);	
		
		this.panelFront.add(this.labelFront, BorderLayout.NORTH);
		this.panelFront.add(this.textfieldFront, BorderLayout.CENTER);
		this.panelFront.setSize(1024, 768);
		
		this.panelBack.setSize(1024, 768);	
		this.panelBack.add(this.labelBack, BorderLayout.NORTH);
		this.panelBack.add(this.textfieldBack, BorderLayout.CENTER);
		
		this.panelNav.add(this.buttonNewCard);
		this.panelNav.add(this.buttonDeleteCard);
		this.panelNav.add(this.buttonSaveCard);
		this.panelNav.add(this.buttonNavBack);
		this.panelNav.add(this.buttonNavForward);
		
		this.panelSettings.add(labelSettings, BorderLayout.NORTH);
		this.panelSettings.add(labelCardNumber, BorderLayout.NORTH);
		this.panelSettings.add(panelFront, BorderLayout.WEST);
		this.panelSettings.add(panelBack, BorderLayout.EAST);
		this.panelSettings.add(panelNav, BorderLayout.SOUTH);
		
		this.panelStartLearning.setSize(1024, 768);
		this.labelStartLearning = new JLabel("Lernen starten");
		this.panelStartLearning.add(labelStartLearning);
		this.panelStartLearning.setVisible(true);
	}
	
	public void closeApplication()
	{
		// Anwendung beenden
		this.dispose();
		this.setVisible(false);
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
	
	public void setControlsToBeTranslated(Language s)
	{
		this.previousLanguage = this.language;
		this.language = s;
		
		this.translateTextOfAControl(this.menuDatei);
		this.translateTextOfAControl(menuItemStartLearning);
		this.translateTextOfAControl(this.menuItemSettings);
		this.translateTextOfAControl(this.menuItemImport);
		this.translateTextOfAControl(this.menuItemExport);
		this.translateTextOfAControl(this.menuItemClose);
		this.translateTextOfAControl(this.menuSprache);
		this.translateTextOfAControl(this.menuItemDeutsch);
		this.translateTextOfAControl(this.menuItemFranzoesisch);
		this.translateTextOfAControl(this.menuItemItalienisch);
		this.translateTextOfAControl(this.menuItemEnglisch);
		
		this.translateTextOfAControl(this.labelStartLearning);
		this.translateTextOfAControl(this.labelSettings);
	}

	public void paintSettingsPanel() 
	{
		this.panelMainPanel.removeAll();
		this.panelMainPanel.add(this.panelSettings);
		this.setVisible(true);
		this.repaint();
	}
	
	public void paintPlayPanel()
	{
		this.panelMainPanel.removeAll();
		this.panelMainPanel.add(this.panelStartLearning);;
		this.setVisible(true);
		this.repaint();
	}

	public void openFileDialog() 
	{
		switch(this.jfImportFile.showOpenDialog(this))
		{
		case JFileChooser.APPROVE_OPTION:
			JOptionPane.showMessageDialog(this, "todo Ruel: Import starten...");
			break;
		case JFileChooser.CANCEL_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Import wurde abgebrochen.", Language.Deutsch, this.language));
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
			JOptionPane.showMessageDialog(this, "todo Ruel: Karten werden in ein File exportiert...");
			break;
		case JFileChooser.CANCEL_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Export wurde abgebrochen.", Language.Deutsch, this.language));
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList.getTranslatedText("Fehler beim exportieren der Karten", Language.Deutsch, this.language));
			break;
		}
	}
}