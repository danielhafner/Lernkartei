package ch.zbw.lernkartei.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.LearningView;
import ch.zbw.lernkartei.view.MainView;
import ch.zbw.lernkartei.view.EditCardsView;


/**
 * @author Ruel
 *
 */
public class Controller {
	private MainView mainView;
	private EditCardsView editCardsView;
	private LearningView learningView;
	private Register register;
	private Card editCardsCard;
	private Card learningCard;
	private ArrayList<Card> editCardsAllCards;
	private ArrayList<Card> learningCardsOfABox;
	private int index;

	/** Konstructor
	 * @param mainView
	 * @param editCardsView
	 * @param learningView
	 * @param register
	 */
	public Controller(MainView mainView, EditCardsView editCardsView, LearningView learningView, Register register) {
		this.mainView = mainView;
		this.editCardsView = editCardsView;
		this.learningView = learningView;
		this.editCardsCard = register.getCardByIndex(0);
		this.register = register;
		addListener();
		this.mainView.paint();
	}

	/**
	 * Registrates all needed Listeners
	 */
	private void addListener() {
		MyWindowListener wBeenden = new MyWindowListener();
		this.mainView.addWindowListener(wBeenden);
		
		MyMenuActionListener mBeenden = new MyMenuActionListener("Beenden");
		this.mainView.setStartMenuActionListener(mBeenden);
		this.mainView.setLanguageMenuActionListener(new MyMenuActionListener(
				Language.Deutsch.toString()));
		this.mainView.setLanguageMenuActionListener(new MyMenuActionListener(
				Language.English.toString()));
		this.mainView.setLanguageMenuActionListener(new MyMenuActionListener(
				Language.Francais.toString()));
		this.mainView.setLanguageMenuActionListener(new MyMenuActionListener(
				Language.Italiano.toString()));

		MyMenuActionListener mEditCards = new MyMenuActionListener("Karten verwalten");
		this.mainView.setEditCardsMenuActionListener(mEditCards);

		MyMenuActionListener mStartLearning = new MyMenuActionListener(
				"Lernen starten");
		this.mainView.setStartLearningsMenuActionListener(mStartLearning);
		
		MyMenuActionListener mResetLearningStatus = new MyMenuActionListener("Lernstand zurücksetzen");
		this.mainView.setResetLearningStatusActionListener(mResetLearningStatus);

		MyMenuActionListener mImport = new MyMenuActionListener("Import");
		this.mainView.setImportMenuActionListener(mImport);

		MyMenuActionListener mExport = new MyMenuActionListener("Export");
		this.mainView.setExportMenuActionListener(mExport);

		MeinButtonActionListener mNavigation = new MeinButtonActionListener();
		this.editCardsView.setNavigationButtonListener(mNavigation);
		
		MeinButtonActionListener mLearning = new MeinButtonActionListener();
		this.learningView.setButtonFrontBackListener(mLearning);
		
		MyComboboxItemListener itemHandler = new MyComboboxItemListener();
		this.learningView.setComboboxItemListener(itemHandler);

		DocumentListener documentListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (editCardsView.getFrontText().equals("")
						&& editCardsView.getBackText().equals("")) {
					editCardsView.setStateSaveButton(false);
					editCardsView.setStateDeleteButton(true);
				} else if (!editCardsView.getFrontText().equals("")
						&& !editCardsView.getBackText().equals(""))
					editCardsView.setStateSaveButton(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!editCardsView.getFrontText().equals(editCardsCard.getFront())
						&& !editCardsView.getBackText().equals(editCardsCard.getBack())) {

					editCardsView.setStateSaveButton(true);
					editCardsView.setStateDeleteButton(true);
				} else
					removeUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		};
		this.editCardsView.setDocumentListener(documentListener);
	}

	/**
	 * @author Ruel
	 * MenuActionListener (Inner Class) for all Menu(Items)
	 */
	public class MyMenuActionListener implements ActionListener {
		public String command;
		public MyMenuActionListener(String command) {
			setCommand(command);
		}
		public void setCommand(String command) {
			this.command = command;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Beenden")) {
				actionPerformedBeenden();
				mainView.closeApplication();
			} else if (e.getActionCommand().equals(Language.Deutsch.toString())) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals(Language.Francais.toString())) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals(Language.English.toString())) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals(Language.Italiano.toString())) {
				mainView.switchLanguage(e);
			} else if (e.getActionCommand().equals("Karten verwalten")) {
				editCardsSelected();
			} else if (e.getActionCommand().equals("Lernen starten")) {
				startLearningSelected();
			}
			else if (e.getActionCommand().equals("Lernstand zurücksetzen")) {
				resetLearningStatusSelected();
			}
			else if (e.getActionCommand().equals("Import")) {
				importSelected();
			} 
			else if (e.getActionCommand().equals("Export")) {
				exportSelected();
			}
		}
		/**
		 * Menuitem Export selected
		 */
		private void exportSelected() {
			if(mainView.saveFileDialog())
			{
				register.export(Paths.get(mainView.getFileExportPath()));
			}
		}
		/**
		 * Menuitem Import selected
		 */
		private void importSelected() {
			if(mainView.openFileDialog())
			{
				register.imports((Paths.get(mainView.getFileImportPath())));
			}
		}
		/**
		 * Menuitem Reset Learning Status selected
		 */
		private void resetLearningStatusSelected() {
			register.resetRegister();
			editCardsView.displayCard(editCardsCard);
			//learningView.ShowQuestion(learningCard.getFront(), learningCard.getBack(), learningCard.getIdCard());
			try {
				learningCardsOfABox = register.getCardsByBox(1);
			} catch (Exception e1) {
				mainView.displayErrorMessage(e1.getMessage());
			}
			learningView.activateProgressBarThread();
			learningView.initializeWithData(register.getBoxes(), register);
		}
		/**
		 * Menu Item Start Learning selected
		 */
		private void startLearningSelected() {
			try {
				learningCardsOfABox = register.getCardsByBox(register.getBoxes().get(0));
				learningCard = learningCardsOfABox.get(0);
				learningView.initializeWithData(register.getBoxes(), register);
			} catch (Exception e1) {
				editCardsView.showMessageBox(e1.getMessage());
			}
			if(learningView.getTargetBox() == 0)
			{
				if (learningView.getTargetBox() == 0)
					learningView.setTargetBox(mainView.askForTargetBox(register.getBoxes()));
			}
			
			learningView.activateProgressBarThread();
			mainView.repaintTheFrame(learningView);
			

		}
		/**
		 * Menu Item Edit Cards selected
		 */
		private void editCardsSelected() {
			try {
				learningView.disableProgressBarThread();
				editCardsAllCards = register.getSortedCardsByCardID();
			} catch (Exception e1) {
				editCardsView.showMessageBox(e1.getMessage());
			}
			editCardsView.setTextTextFieldCardNumber(editCardsAllCards.get(0).getIdCard() + "");
			editCardsView.setBoxNumber(editCardsAllCards.get(0).getBox() + "");
			editCardsView.setTextAreaFront(editCardsAllCards.get(0).getFront());
			editCardsView.setTextAreaBack(editCardsAllCards.get(0).getBack());
			editCardsView.setStateNavBackForwardButtons(register, 0);
			editCardsView.setStateSaveButton(false);
			mainView.repaintTheFrame(editCardsView);
			editCardsView.setInitialFocus();
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
				saveSelected();				
			} else if (e.getActionCommand().equals(">>>")) {
				forwardSelected();
			} else if (e.getActionCommand().equals("<<<")) {
				navBack();
			} else if (e.getActionCommand().equals("Neu")) {
				newSelected();
			} else if (e.getActionCommand().equals("Löschen")) {
				deleteSelected();
			}
			else if(e.getActionCommand().equals("VORDERSEITE")){
				frontSelected();
			}
			else if(e.getActionCommand().equals("Richtig") || e.getActionCommand().equals("Falsch"))
			{
				correctOrWrongSelected(e);
			}
		}

		/**
		 *  >>> Button selected
		 */
		private void forwardSelected() {
			try {											
				editCardsCard = register.getCardByIndex(index + 1);
				if(editCardsCard != null)
				{
					index++;
					editCardsView.displayCard(editCardsCard);
				}
				else
				{
					index--;
				}
				editCardsView.setStateSaveButton(false);	
				editCardsView.setStateNavBackForwardButtons(register, index);

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}

		/** Correct or False Button selected
		 * @param e
		 */
		private void correctOrWrongSelected(ActionEvent e) {
			if(e.getActionCommand().equals("Richtig"))
			{
				register.isTrue(learningView.getCardId());
			}
			else if(e.getActionCommand().equals("Falsch"))
			{
				register.isFalse(learningView.getCardId());
			}
			learningCardsOfABox.remove(learningCard);
			if(learningCardsOfABox.size() == 0)
			{
				learningView.refreshComboboxFachWithData(register.getBoxes());;
			}
			refreshLearningData(learningCardsOfABox);
			learningView.setZahl(calculateNumberForProgressBar());
		}

		/**
		 * Front Button selected
		 */
		private void frontSelected() {
			learningView.showAnswer();
		}

		/**
		 * Löschen Button selected
		 */
		private void deleteSelected() {
			try {
				// Benutzer fragen, ob er die Karte auch wirklich löschen will...
				if (cardWantsToBeDeleted()) {
					register.getCards().remove(editCardsCard);
					index = register.getNumberOfCards();
					// Eine nachfolgende Karte müsste nun am gleichen Index stehen...						
					editCardsCard = register.getCardByIndex(register.getNumberOfCards() - 1);
					while (editCardsCard == null && index >= 0)
					{
						//vorgehende Karte ermitteln
						index--;
						editCardsCard = register.getCardByIndex(index);
					}
					
					//Falls keine Karten mehr vorhanden sind...
					if(editCardsCard == null)
					{
						index = 0;
						editCardsCard = new Card("", "", register.getMaxId_Card());
						register.add(editCardsCard);
						editCardsView.displayCard(editCardsCard);
						editCardsView.setStateButtonNew(false);
						editCardsView.setStateDeleteButton(false);
					}
					else
					{
						// Vor
						editCardsView.displayCard(editCardsCard);
						index = register.getNumberOfCards() - 1;
						editCardsView.setStateButtonNew(true);
					}
					editCardsView.setStateSaveButton(false);
					editCardsView.setStateNavBackForwardButtons(register, index);
				}
			} catch (Exception e1) {

				mainView.displayErrorMessage(e1.getMessage());
			}
		}

		/**
		 * Neu Button selected
		 */
		private void newSelected() {
			try {
				index = register.getNumberOfCards();
				editCardsCard = new Card("", "", register.getMaxId_Card());
				register.add(editCardsCard);
				
				//Erfasste Karte neu laden (am Ende der Liste)
				editCardsView.displayCard(register.getCardByIndex(index));
				
				//Buttons aktualisieren
				editCardsView.setStateNavBackForwardButtons(register, index);
				editCardsView.setStateButtonNew(false);
				editCardsView.setStateSaveButton(false);
				editCardsView.setStateDeleteButton(false);
				editCardsView.setStateButtonBack(false);
				editCardsView.setStateButtonForward(false);
			} catch (Exception e1) {

				mainView.displayErrorMessage(e1.getMessage());
			}
		}

		/**
		 * <<< Button selected
		 */
		private void navBack() {
			try {
				if (!continueWithoutSaving())
					saveSelected();
				if (index != 0) {
					index--;
				}
				editCardsCard = register.getCardByIndex(index);
				editCardsView.displayCard(editCardsCard);
				editCardsView.setStateNavBackForwardButtons(register, index);
				editCardsView.setStateSaveButton(false);

				if (register.getCardByIndex(index).getFront().equals("")
						&& register.getCardByIndex(index).getBack()
								.equals("")) {
					editCardsView.setStateDeleteButton(false);
				} else {
					editCardsView.setStateDeleteButton(true);
				}

			} catch (Exception e1) {

				mainView.displayErrorMessage(e1.getMessage());
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
		 * Save-Button selected
		 */
		private void saveSelected() {
			try {
				editCardsCard.setCard(editCardsView.getFrontText(), editCardsView.getBackText());				
				editCardsView.setStateDeleteButton(true);
				editCardsView.setStateButtonNew(true);
				editCardsView.setStateSaveButton(false);
				editCardsView.setStateNavBackForwardButtons(register, index);				
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
				if (!editCardsView.getFrontText().equals(editCardsCard.getFront())
						|| !editCardsView.getBackText().equals(editCardsCard.getBack())) {
					if (editCardsView.showMessageBox("Daten wurden verändert. Willst du fortfahren ohne zu speichern?") == JOptionPane.OK_OPTION) {
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
					learningCardsOfABox = register.getCardsByBox(Integer.parseInt(arg0.getItem().toString()));
					refreshLearningData(learningCardsOfABox);
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
		this.learningView.ShowQuestion(this.learningCard.getFront(), this.learningCard.getBack(), this.learningCard.getIdCard());
		try {
			this.learningView.setStatistics(
					register.getCardsByBox(learningView.getBox()).size()
					,register.getNumberOfCards()
					,register.calculateQuotaCorrect()
					,register.calculateQuotaWrong()
					);
		} catch (Exception e) {
			learningView.displayErrorMessage(e.getMessage());
		}
	}
	
	/**
	 * Asks the User if he want to save the Data into the internal csv-File
	 * and closes the Application
	 */
	private void actionPerformedBeenden() {
		if(mainView.quitAndSave())
		{
			register.saveDataIntoInternalFile();
		}
		mainView.closeApplication();
	}

	/**
	 * @author Ruel
	 * Own WindowListener: Actions on JFrame is closing..
	 */
	public class MyWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			actionPerformedBeenden();
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowOpened(WindowEvent e) {	
		}
	}
	
	public int calculateNumberForProgressBar()
	{
		// 100 entspricht einem vollen Progressbar
		// 0 = leer	
		int countEffektiv = 0;
		int countMax = 0;
		int i = 1;
		{
		try {
			countMax = (register.getCards().size() * 7);
			
			for (i = 1; i <= 7; i++)
			{
				if(register.getCardsByBox(i).size() > 0)
				{
					countEffektiv += (register.getCardsByBox(i).size() * (7-i));
				}
			}
			
			// Sind ALL Faecher bis zum persoenlichen Ziel leer --> Ziel erreicht!
			for (i = 1; i <= 7; i++)
			{
				if(register.getCardsByBox(i).size() > 1 && i == learningView.getTargetBox())
				{
					return 100;
				}
			}			
				
			} catch (Exception e) {
				learningView.displayErrorMessage(e.getMessage());
			}
		}
	    return ((countMax - countEffektiv) * 100 / countMax);
	}	
}