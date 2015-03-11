package ch.zbw.lernkartei.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.LearningView;
import ch.zbw.lernkartei.view.MainView;
import ch.zbw.lernkartei.view.SettingsView;

public class Controller {

	private MainView mainView;
	private SettingsView settingsView;
	private LearningView learningView;
	private Register register;
	private Card settingCard;
	private int index;
	private ArrayList<Card> allCards;
	private ArrayList<Card> cardsOfABox;

	public Controller(MainView mainView, SettingsView settingsView, LearningView learningView, Register register) {
		this.mainView = mainView;
		this.settingsView = settingsView;
		this.learningView = learningView;
		this.settingCard = register.getCardByIndex(0);
		this.register = register;
		addListener();
	}

	public void startApplication() {
		try {
			this.mainView.paint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
				settingsView.setTextAreaFront(allCards.get(0).getFront());
				settingsView.setTextAreaBack(allCards.get(0).getBack());
				settingsView.setStateNavBackForwardButtons(register, 0);
				settingsView.setStateSaveButton(false);
				mainView.repaintTheFrame(settingsView);
				settingsView.setInitialFocus();
			} else if (e.getActionCommand().equals("Lernen starten")) {
				try {
					cardsOfABox = register.getCardsByBox(register.getBoxes().get(0));
				} catch (Exception e1) {
					settingsView.showMessageBox(e1.getMessage());
				}				
				learningView.initializeSettingsWithData(register.getBoxes(), register);
				mainView.repaintTheFrame(learningView);
			} else if (e.getActionCommand().equals("Import")) {
				mainView.openFileDialog();
				register.imports(mainView.getFileImportfile());
			} else if (e.getActionCommand().equals("Export")) {
				mainView.saveFileDialog();
				register.export(mainView.getFileExportfile());
			}
		}
	}

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
					if (cardWantsToBeDeleted()) {
						register.getCards().remove(settingCard);
						index = register.getNumberOfCards();
						// Eine nachfolgende Karte müsste nun am gleichen Index stehen...						
						settingCard = register.getCardByIndex(register.getNumberOfCards() - 1);
						while (settingCard == null && index >= 0)
						{
							settingCard = register.getCardByIndex(index);
							index--;
						}
						
						if(settingCard == null)
						{
							index = 0;
							settingCard = new Card("", "", register.getMaxId_Card());
							register.add(settingCard);
							settingsView.displayCard(settingCard);
							settingsView.setStateButtonNew(false);
							settingsView.setStateDeleteButton(false);
						}
						else
						{
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
				index = learningView.getCardId();
				if(e.getActionCommand().equals("Richtig"))
				{
					register.isTrue(learningView.getCardId());
				}
				else if(e.getActionCommand().equals("Falsch"))
				{
					register.isFalse(learningView.getCardId());
				}
				cardsOfABox.remove(settingCard);
				learningView.refreshData(register.getBoxes(), cardsOfABox);
			}
		}

		private boolean cardWantsToBeDeleted() {
			if (JOptionPane.showConfirmDialog(mainView,
					"Willst du die Karte definitiv löschen?") == JOptionPane.OK_OPTION)
				return true;
			return false;
		}

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
}