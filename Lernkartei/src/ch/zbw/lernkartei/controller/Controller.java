package ch.zbw.lernkartei.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.LearningView;
import ch.zbw.lernkartei.view.MainView;
import ch.zbw.lernkartei.view.SettingsView;


/**
 * @author Ruel
 *
 */
public class Controller {

	private MainView mainView;
	private SettingsView settingsView;
	private LearningView learningView;
	private Register register;
	private Card settingCard;
	private Card learningCard;
	private int index;
	private ArrayList<Card> allCards;
	private ArrayList<Card> cardsOfABox;

	/** Konstructor
	 * @param mainView
	 * @param settingsView
	 * @param learningView
	 * @param register
	 */
	public Controller(MainView mainView, SettingsView settingsView, LearningView learningView, Register register) {
		this.mainView = mainView;
		this.settingsView = settingsView;
		this.learningView = learningView;
		this.settingCard = register.getCardByIndex(0);
		this.register = register;
		addListener();
	}


	/**
	 * Paints the mainView
	 */
	public void paintMainView() {
		try {
			this.mainView.paint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Registrates all needed Listeners
	 */
	private void addListener() {
		MeinMenuActionListener mBeenden = new MeinMenuActionListener("Beenden");
		this.mainView.setStartMenuActionListener(mBeenden);
		this.mainView.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Deutsch.toString()));
		this.mainView.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Englisch.toString()));
		this.mainView.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Französisch.toString()));
		this.mainView.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Italienisch.toString()));

		MeinMenuActionListener mSettings = new MeinMenuActionListener(
				"Einstellungen");
		this.mainView.setSettingsMenuActionListener(mSettings);

		MeinMenuActionListener mStartLearning = new MeinMenuActionListener(
				"Lernen starten");
		this.mainView.setStartLearningsMenuActionListener(mStartLearning);

		MeinMenuActionListener mImport = new MeinMenuActionListener("Import");
		this.mainView.setImportMenuActionListener(mImport);

		MeinMenuActionListener mExport = new MeinMenuActionListener("Export");
		this.mainView.setExportMenuActionListener(mExport);

		MeinButtonActionListener mNavigation = new MeinButtonActionListener();
		this.settingsView.setNavigationButtonListener(mNavigation);
		
		MeinButtonActionListener mLearning = new MeinButtonActionListener();
		this.learningView.setButtonFrontBackListener(mLearning);
		
		MyComboboxItemListener itemHandler = new MyComboboxItemListener();
		this.learningView.setComboboxItemListener(itemHandler);

		DocumentListener documentListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (settingsView.getFrontText().equals("")
						&& settingsView.getBackText().equals("")) {
					settingsView.setStateSaveButton(false);
					settingsView.setStateDeleteButton(true);
				} else if (!settingsView.getFrontText().equals("")
						&& !settingsView.getBackText().equals(""))
					settingsView.setStateSaveButton(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!settingsView.getFrontText().equals(settingCard.getFront())
						&& !settingsView.getBackText().equals(settingCard.getBack())) {

					settingsView.setStateSaveButton(true);
					settingsView.setStateDeleteButton(true);
				} else
					removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		};

		this.settingsView.setDocumentListener(documentListener);
	}

	/**
	 * @author Ruel
	 * MenuActionListener (Inner Class) for all Menu(Items)
	 */
	public class MeinMenuActionListener implements ActionListener {

		public String command;

		public MeinMenuActionListener(String command) {
			setCommand(command);
		}

		public void setCommand(String command) {
			this.command = command;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Beenden")) {
				if(mainView.quitAndSave())
				{
					register.saveDataIntoInternalFile();
				}
				mainView.closeApplication();
			} else if (e.getActionCommand().equals("Deutsch")) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals("Französisch")) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals("Englisch")) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals("Italienisch")) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals("Einstellungen")) {
				
				try {
					allCards = register.getSortedCardsByCardID();
				} catch (Exception e1) {
					settingsView.showMessageBox(e1.getMessage());
				}
				settingsView.setTextTextFieldCardNumber(allCards.get(0).getIdCard() + "");
				settingsView.setBoxNumber(allCards.get(0).getBox() + "");
				settingsView.setTextAreaFront(allCards.get(0).getFront());
				settingsView.setTextAreaBack(allCards.get(0).getBack());
				settingsView.setStateNavBackForwardButtons(register, 0);
				settingsView.setStateSaveButton(false);
				mainView.repaintTheFrame(settingsView);
				settingsView.setInitialFocus();
			} else if (e.getActionCommand().equals("Lernen starten")) {
				try {
					cardsOfABox = register.getCardsByBox(register.getBoxes().get(0));
					learningCard = cardsOfABox.get(0);
				} catch (Exception e1) {
					settingsView.showMessageBox(e1.getMessage());
				}				
				learningView.initializeWithData(register.getBoxes(), register);
				mainView.repaintTheFrame(learningView);
			} else if (e.getActionCommand().equals("Import")) {
				mainView.openFileDialog();
				register.imports((Paths.get(mainView.getFileImportPath())));
			} else if (e.getActionCommand().equals("Export")) {
				mainView.saveFileDialog();
				register.export(Paths.get(mainView.getFileExportPath()));
			}
		}
	}

	
	/**
	 * @author Ruel
	 * ButtonActionListener (Inner Class) for all Buttons
	 */
	public class MeinButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Speichern")) {
				saveCard();
				
			} else if (e.getActionCommand().equals(">>>")) {
				try {											
					settingCard = register.getCardByIndex(index + 1);
					if(settingCard != null)
					{
						index++;
						settingsView.displayCard(settingCard);
					}
					else
					{
						index--;
					}
					settingsView.setStateSaveButton(false);	
					settingsView.setStateNavBackForwardButtons(register, index);

				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else if (e.getActionCommand().equals("<<<")) {
				try {
					if (!continueWithoutSaving())
						saveCard();
					if (index != 0) {
						index--;
					}
					settingCard = register.getCardByIndex(index);
					settingsView.displayCard(settingCard);
					settingsView.setStateNavBackForwardButtons(register, index);
					settingsView.setStateSaveButton(false);

					if (register.getCardByIndex(index).getFront().equals("")
							&& register.getCardByIndex(index).getBack()
									.equals("")) {
						settingsView.setStateDeleteButton(false);
					} else {
						settingsView.setStateDeleteButton(true);
					}

				} catch (Exception e1) {

					mainView.displayErrorMessage(e1.getMessage());
				}
			} else if (e.getActionCommand().equals("Neu")) {
				try {
					index = register.getNumberOfCards();
					settingCard = new Card("", "", register.getMaxId_Card());
					register.add(settingCard);
					
					//Erfasste Karte neu laden (am Ende der Liste)
					settingsView.displayCard(register.getCardByIndex(index));
					
					//Buttons aktualisieren
					settingsView.setStateNavBackForwardButtons(register, index);
					settingsView.setStateButtonNew(false);
					settingsView.setStateSaveButton(false);
					settingsView.setStateDeleteButton(false);
					settingsView.setStateButtonBack(false);
					settingsView.setStateButtonForward(false);
				} catch (Exception e1) {

					mainView.displayErrorMessage(e1.getMessage());
				}
			} else if (e.getActionCommand().equals("Löschen")) {
				try {
					// Benutzer fragen, ob er die Karte auch wirklich löschen will...
					if (cardWantsToBeDeleted()) {
						register.getCards().remove(settingCard);
						index = register.getNumberOfCards();
						// Eine nachfolgende Karte müsste nun am gleichen Index stehen...						
						settingCard = register.getCardByIndex(register.getNumberOfCards() - 1);
						while (settingCard == null && index >= 0)
						{
							//vorgehende Karte ermitteln
							index--;
							settingCard = register.getCardByIndex(index);
						}
						
						if(settingCard == null)
						{
							//Keine Karten mehr vorhanden
							index = 0;
							settingCard = new Card("", "", register.getMaxId_Card());
							register.add(settingCard);
							settingsView.displayCard(settingCard);
							settingsView.setStateButtonNew(false);
							settingsView.setStateDeleteButton(false);
						}
						else
						{
							// Vor
							settingsView.displayCard(settingCard);
							index = register.getNumberOfCards() - 1;
							settingsView.setStateButtonNew(true);
						}
						settingsView.setStateSaveButton(false);
						settingsView.setStateNavBackForwardButtons(register, index);
					}
				} catch (Exception e1) {

					mainView.displayErrorMessage(e1.getMessage());
				}
			}
			else if(e.getActionCommand().equals("VORDERSEITE")){
				//
				// Lernen: d.h. eine Vorder- oder Rückseite wurde angeklickt
				// --> entweder Vorderseite ausblenden und Rückseite einblenden
				// oder Vorderseite einblenden und Rückseite ausblenden
				learningView.showAnswer();
			}
			else if(e.getActionCommand().equals("Richtig") || e.getActionCommand().equals("Falsch"))
			{
				if(e.getActionCommand().equals("Richtig"))
				{
					register.isTrue(learningView.getCardId());
				}
				else if(e.getActionCommand().equals("Falsch"))
				{
					register.isFalse(learningView.getCardId());
				}
				cardsOfABox.remove(learningCard);
				if(cardsOfABox.size() == 0)
				{
					JOptionPane.showMessageDialog(learningView, "Fach leer, bitte ein neues Fach wählen.");
					learningView.refreshComboboxFachWithData(register.getBoxes());
				}
				refreshLearningData(cardsOfABox);
			}
		}


		/** Shows a Dialog and ask the User, if he really want to delete the card
		 * @return: yes = true, no = false
		 */
		private boolean cardWantsToBeDeleted() {
			if (JOptionPane.showConfirmDialog(mainView,
					"Willst du die Karte definitiv löschen?") == JOptionPane.OK_OPTION)
				return true;
			return false;
		}


		/**
		 * Saves a card
		 */
		private void saveCard() {
			try {
				settingCard.setCard(settingsView.getFrontText(), settingsView.getBackText());				
				settingsView.setStateDeleteButton(true);
				settingsView.setStateButtonNew(true);
				settingsView.setStateSaveButton(false);
				settingsView.setStateNavBackForwardButtons(register, index);				
			}

			catch (Exception ex) {
				mainView.displayErrorMessage("Error: " + ex.getMessage());
			}
		}


		/**Ask if User wants to continue without saving the changes...
		 * @return
		 */
		private boolean continueWithoutSaving() {
			try {
				if (!settingsView.getFrontText().equals(settingCard.getFront())
						|| !settingsView.getBackText().equals(settingCard.getBack())) {
					if (settingsView.showMessageBox("Daten wurden verändert. Willst du fortfahren ohne zu speichern?") == JOptionPane.OK_OPTION) {
						return true;
					} else {
						return false;
					}
				}
			} catch (Exception e) {

				mainView.displayErrorMessage(e.getMessage());
			}
			return true;
		}
	}
	
	/**
	 * @author Ruel
	 * Inner Class for all ItemListeners
	 */
	public class MyComboboxItemListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent arg0) {
			
			if(arg0.getStateChange() == ItemEvent.SELECTED)
			{
				try {
					cardsOfABox = register.getCardsByBox(Integer.parseInt(arg0.getItem().toString()));
					refreshLearningData(cardsOfABox);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					learningView.displayErrorMessage(e.getMessage());
				}
			}
		}
	}
	
	/** Refreshs the Learning-Data
	 * @param cardsOfABox
	 */
	public void refreshLearningData(ArrayList<Card> cardsOfABox)
	{
		this.learningCard = cardsOfABox.get(0);
		learningView.ShowQuestion(this.learningCard.getFront(), this.learningCard.getBack(), this.learningCard.getIdCard());
	}
	
}