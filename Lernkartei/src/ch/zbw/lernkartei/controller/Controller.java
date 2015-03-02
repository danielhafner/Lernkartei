package ch.zbw.lernkartei.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.GUI;
import ch.zbw.lernkartei.view.SettingsView;

public class Controller {

	private GUI gui;
	private SettingsView settingsView;
	private Register register;
	private Card card;
	private int cardId;

	public Controller(GUI gui, SettingsView settingsView, Register register) {
		this.gui = gui;
		this.settingsView = settingsView;
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
		this.settingsView.setNavigationButtonListener(mNavigation);

		DocumentListener documentListener = new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				if (settingsView.getFrontText().equals("")
						&& settingsView.getBackText().equals("")) {
					settingsView.setStateSaveButton(false);
					settingsView.setStateDeleteButton(true);
				} else if (!settingsView.getFrontText().equals("")
						&& !settingsView.getBackText().equals(""))
					settingsView.setStateSaveButton(true);
			}

			public void insertUpdate(DocumentEvent e) {
				if (!settingsView.getFrontText().equals(card.getFront())
						&& !settingsView.getBackText().equals(card.getBack())) {

					settingsView.setStateSaveButton(true);
					settingsView.setStateDeleteButton(true);
				} else
					removeUpdate(e);
			}

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
				settingsView.initializeSettingsWithData(register);
				settingsView.setStateNavBackForwardButtons(register, 0);
				settingsView.setStateSaveButton(false);
				gui.repaintTheFrame(settingsView);
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
					settingsView.setStateNavBackForwardButtons(register, cardId);
					if (register.getNumberOfCards() == cardId) {
						settingsView.setStateNavBackForwardButtons(register, cardId);
						cardId--;
						
					}
					card = register.getCardByIndex(cardId);
					settingsView.displayCard(card, cardId);
					settingsView.setStateSaveButton(false);

				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else if (e.getActionCommand().equals("<<<")) {
				try {
					if (!continueWithoutSaving())
						saveCard();

					if (cardId != 0) {
						cardId = settingsView.getCardid() - 1;
					}
					card = register.getCardByIndex(cardId);
					settingsView.displayCard(card, cardId);
					settingsView.setStateNavBackForwardButtons(register, cardId);
					settingsView.setStateSaveButton(false);

					if (register.getCardByIndex(cardId).getFront().equals("")
							&& register.getCardByIndex(cardId).getBack()
									.equals("")) {
						settingsView.setStateDeleteButton(false);
					} else {
						settingsView.setStateDeleteButton(true);
					}

				} catch (Exception e1) {

					gui.displayErrorMessage(e1.getMessage());
				}
			} else if (e.getActionCommand().equals("Neu")) {
				try {
					cardId = register.getNumberOfCards();
					Card newCard = new Card("", "");
					register.add(newCard);
					settingsView.displayCard(newCard, cardId);
					settingsView.setStateButtonNew(false);
					settingsView.setStateSaveButton(false);
					settingsView.setStateDeleteButton(false);
					settingsView.setStateButtonBack(false);
					settingsView.setStateButtonForward(false);

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
							settingsView.displayCard(card, cardId);
							settingsView.setStateNavBackForwardButtons(register, cardId);
						} else {
							cardId = 0;
							card = new Card("", "");
							register.add(card);
							settingsView.displayCard(card, cardId);
							settingsView.setStateNavBackForwardButtons(register, cardId);
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
				front = settingsView.getFrontText();
				back = settingsView.getBackText();
				if (settingsView.getCardid() != null) {
					cardId = settingsView.getCardid();
					card = register.getCardByIndex(cardId);
					card.setCard(front, back);
					settingsView.displayCard(card, cardId);
					settingsView.setStateNavBackForwardButtons(register, cardId);
				} else {
					cardId = settingsView.getCardid() + 1;
					register.add(new Card(front, back));
					settingsView.displayCard(register.getCardByIndex(register
							.getNumberOfCards() - 1), cardId);
					settingsView.setStateNavBackForwardButtons(register, cardId);
				}
				settingsView.setStateDeleteButton(true);
				settingsView.setStateSaveButton(false);
				settingsView.setStateNavBackForwardButtons(register, cardId);
			}

			catch (Exception ex) {
				gui.displayErrorMessage("Error: " + ex.getMessage());
			}
		}

		private boolean continueWithoutSaving() {
			try {
				if (!settingsView.getFrontText().equals(card.getFront())
						|| !settingsView.getBackText().equals(card.getBack())) {
					if (settingsView.showMessageBox("Daten wurden verändert. Willst du fortfahren ohne zu speichern?") == JOptionPane.OK_OPTION) {
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