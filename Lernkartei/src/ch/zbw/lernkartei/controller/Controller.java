package ch.zbw.lernkartei.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodListener;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.GUI;

public class Controller {

	private GUI gui;
	private Register register;
	private Card card;
	private int cardId;

	public Controller(GUI gui, Register register) {
		this.gui = gui;
		this.card = register.getCardByIndex(0);
		this.register = register;

		addListener();
	}

	public void startApplication() {
		try {
			this.gui.paint();
			// this.gui.initializeControls();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addListener() {
		MeinMenuActionListener mBeenden = new MeinMenuActionListener("Beenden");
		this.gui.setStartMenuActionListener(mBeenden);

		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Deutsch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Englisch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Französisch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(
				Language.Italienisch.toString()));

		MeinMenuActionListener mSettings = new MeinMenuActionListener(
				"Einstellungen");
		this.gui.setSettingsMenuActionListener(mSettings);

		MeinMenuActionListener mStartLearning = new MeinMenuActionListener(
				"Lernen starten");
		this.gui.setStartLearningsMenuActionListener(mStartLearning);

		MeinMenuActionListener mImport = new MeinMenuActionListener("Import");
		this.gui.setImportMenuActionListener(mImport);

		MeinMenuActionListener mExport = new MeinMenuActionListener("Export");
		this.gui.setExportMenuActionListener(mExport);

		MeinButtonActionListener mNavigation = new MeinButtonActionListener();
		this.gui.setNavigationButtonListener(mNavigation);

		DocumentListener documentListener = new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				if (gui.getFrontText().equals("")
						&& gui.getBackText().equals("")) {
					gui.setStateSaveButton(false);
					gui.setStateDeleteButton(true);
				} else if (!gui.getFrontText().equals("")
						&& !gui.getBackText().equals(""))
					gui.setStateSaveButton(true);
			}

			public void insertUpdate(DocumentEvent e) {
				if (!gui.getFrontText().equals(card.getFront())
						&& !gui.getBackText().equals(card.getBack())) {

					gui.setStateSaveButton(true);
					gui.setStateDeleteButton(true);
				} else
					removeUpdate(e);
			}

			public void changedUpdate(DocumentEvent e) {
			}
		};

		this.gui.setDocumentListener(documentListener);
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
				gui.closeApplication();
			} else if (e.getActionCommand().equals("Deutsch")) {
				gui.switchLanguage(e);
			} else if (e.getActionCommand().equals("Franzoesisch")) {
				gui.switchLanguage(e);
			} else if (e.getActionCommand().equals("Englisch")) {
				gui.switchLanguage(e);
			} else if (e.getActionCommand().equals("Italienisch")) {
				gui.switchLanguage(e);
			} else if (e.getActionCommand().equals("Einstellungen")) {
				gui.initializeSettingsWithData(register);
				gui.setStateNavBackForwardButtons(register, 0);
				gui.setStateSaveButton(false);
				gui.paintSettingsPanel();
			} else if (e.getActionCommand().equals("Lernen starten")) {
				gui.paintPlayPanel();
			} else if (e.getActionCommand().equals("Import")) {
				gui.openFileDialog();
			} else if (e.getActionCommand().equals("Export")) {
				gui.saveFileDialog();
			}
		}
	}

	public class MeinButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Speichern")) {
				saveCard();
			} else if (e.getActionCommand().equals(">>>")) {
				try {
					cardId++;
					gui.setStateNavBackForwardButtons(register, cardId);
					if (register.getNumberOfCards() == cardId) {
						gui.setStateNavBackForwardButtons(register, cardId);
						cardId--;
						
					}

					card = register.getCardByIndex(cardId);
					gui.displayCard(card, cardId);
					gui.setStateSaveButton(false);

				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else if (e.getActionCommand().equals("<<<")) {
				try {
					if (!continueWithoutSaving())
						saveCard();

					if (cardId != 0) {
						cardId = gui.getCardid() - 1;
					}
					card = register.getCardByIndex(cardId);
					gui.displayCard(card, cardId);
					gui.setStateNavBackForwardButtons(register, cardId);
					gui.setStateSaveButton(false);

					if (register.getCardByIndex(cardId).getFront().equals("")
							&& register.getCardByIndex(cardId).getBack()
									.equals("")) {
						gui.setStateDeleteButton(false);
					} else {
						gui.setStateDeleteButton(true);
					}

				} catch (Exception e1) {

					gui.displayErrorMessage(e1.getMessage());
				}
			} else if (e.getActionCommand().equals("Neu")) {
				try {
					cardId = register.getNumberOfCards();
					Card newCard = new Card("", "");
					register.add(newCard);
					gui.displayCard(newCard, cardId);

					gui.setStateButtonNew(false);
					gui.setStateSaveButton(false);
					gui.setStateDeleteButton(false);
					gui.setStateButtonBack(false);
					gui.setStateButtonForward(false);

				} catch (Exception e1) {

					gui.displayErrorMessage(e1.getMessage());
				}
			} else if (e.getActionCommand().equals("Löschen")) {
				try {
					if (cardWantsToBeDeleted()) {
						register.getCards().remove(
								register.getCardByIndex(cardId));

						if (register.getNumberOfCards() > 0) {
							card = register.getCardByIndex(cardId);
							gui.displayCard(card, cardId);
							gui.setStateNavBackForwardButtons(register, cardId);
						} else {
							cardId = 0;
							card = new Card("", "");
							register.add(card);
							gui.displayCard(card, cardId);
							gui.setStateNavBackForwardButtons(register, cardId);
						}
					}
				} catch (Exception e1) {

					gui.displayErrorMessage(e1.getMessage());
				}
			}

		}

		private boolean cardWantsToBeDeleted() {
			if (JOptionPane.showConfirmDialog(gui,
					"Willst du die Karte definitiv löschen?") == JOptionPane.OK_OPTION)
				return true;
			return false;
		}

		private void saveCard() {
			String front, back;
			try {
				front = gui.getFrontText();
				back = gui.getBackText();
				if (gui.getCardid() != null) {
					cardId = gui.getCardid();
					card = register.getCardByIndex(cardId);
					card.setCard(front, back);
					gui.displayCard(card, cardId);
					gui.setStateNavBackForwardButtons(register, cardId);
				} else {
					cardId = gui.getCardid() + 1;
					register.add(new Card(front, back));
					gui.displayCard(register.getCardByIndex(register
							.getNumberOfCards() - 1), cardId);
					gui.setStateNavBackForwardButtons(register, cardId);
				}
				gui.setStateDeleteButton(true);
				gui.setStateSaveButton(false);
				gui.setStateNavBackForwardButtons(register, cardId);
			}

			catch (Exception ex) {
				gui.displayErrorMessage("Error: " + ex.getMessage());
			}
		}

		private boolean continueWithoutSaving() {
			try {
				if (!gui.getFrontText().equals(card.getFront())
						|| !gui.getBackText().equals(card.getBack())) {
					if (gui.showMessageBox("Daten wurden verändert. Willst du fortfahren ohne zu speichern?") == JOptionPane.OK_OPTION) {
						return true;
					} else {
						return false;
					}

				}
			} catch (Exception e) {

				gui.displayErrorMessage(e.getMessage());
			}
			return true;
		}
	}
}