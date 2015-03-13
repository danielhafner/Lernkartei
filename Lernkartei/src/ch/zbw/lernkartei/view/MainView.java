package ch.zbw.lernkartei.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.zbw.lernkartei.controller.Controller.MeinMenuActionListener;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.TranslationDataSet;

/**
 * @author Ruel
 *
 */
public class MainView extends JFrame {

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
	private SettingsView panelSettings;
	private LearningView panelLearming;
	private Toolkit toolKit;
	private JFileChooser jfImportFile;

	private int x = 0, y = 0, width = 800, heigth = 600;
	private String fileExportPath;
	private String fileImportPath;

	/**
	 * MainView-Constructor
	 */
	public MainView() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
		this.mainPanel = new JPanel(new GridLayout(1, 1));
		this.menuBar = new JMenuBar();

		// Menu
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
		this.panelLearming = new LearningView();
		this.panelSettings = new SettingsView();
		this.jfImportFile = new JFileChooser();

		ImageIcon imageIconBackground = new ImageIcon(this.getClass().getResource("/bg-sea.jpg"));
		imageIconBackground.setDescription("Isch das en Scheiss");
		labelImageIconBackground = new JLabel(imageIconBackground);
	}

	/**
	 *  Paints the mainView
	 */
	public void paint() {
		this.language = Language.Deutsch;
		setTitle("Vokabeltrainer R. Holderegger | E. Schwarz | D. Hafner");
		setIconImage(new ImageIcon(this.getClass().getResource("/zbw.jpg")).getImage());

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

		this.toolKit = Toolkit.getDefaultToolkit();
		Dimension d = toolKit.getScreenSize();
		x = (int) ((d.getWidth() - width) / 2); // Gesamtbreite des Screens
												// abz�glich Breite der
												// Anwendung durch 2 ergibt die
												// x-Anfangsposition.
		y = (int) ((d.getHeight() - heigth) / 2); // Gesamth�he des Screens
													// abz�glich H�he der
													// Anwendung durch 2 ergibt
													// y-Anfangsposition.
		setBounds(x, y, this.width, this.heigth);
		this.setMinimumSize(new Dimension(width, heigth));

		// Man kann auf einem Label ein Layout definieren...
		labelImageIconBackground.setLayout(new GridBagLayout());
		labelImageIconBackground.add(this.mainPanel);
		this.add(labelImageIconBackground);
	}

	/**
	 * Paints the Setting-Panel
	 */
	public void paintSettingsPanel() {
		this.repaintTheFrame(this.panelSettings);
	}

	/**
	 * Paints the Learning-Panel
	 */
	public void paintPanelLearning() {
		repaintTheFrame(this.panelLearming);
	}

	/** Repaints a certain Panel
	 * @param panelToShow
	 */
	public void repaintTheFrame(JPanel panelToShow) {
		labelImageIconBackground.removeAll();
		this.mainPanel.removeAll();
		this.mainPanel.add(panelToShow);
		labelImageIconBackground.add(this.mainPanel);
		this.setVisible(true);
	}

	/**
	 *  Closes the application
	 */
	public void closeApplication() {
		this.dispose();
		this.setVisible(false);
	}

	/**
	 * Sets the File Import Path
	 */
	public void openFileDialog() {
		switch (this.jfImportFile.showOpenDialog(this)) {
		case JFileChooser.APPROVE_OPTION:
			fileImportPath = jfImportFile.getSelectedFile().toString();
			break;
		case JFileChooser.CANCEL_OPTION:
			// JOptionPane.showMessageDialog(this,
			// translationArrayList.getTranslatedText("Import wurde abgebrochen.",
			// Language.Deutsch, this.language));
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList
					.getTranslatedText("Fehler beim importieren der Daten",
							Language.Deutsch, this.language));
			break;
		}
	}

	/**
	 * Saves as File unter the selected Path
	 */
	public void saveFileDialog() {
		switch (this.jfImportFile.showSaveDialog(this)) {
		case JFileChooser.APPROVE_OPTION:
			fileExportPath = jfImportFile.getSelectedFile().toString();
			// JOptionPane.showMessageDialog(this,
			// "todo Ruel: Karten werden in ein File exportiert...");
			break;
		case JFileChooser.CANCEL_OPTION:
			JOptionPane.showMessageDialog(this,
			translationArrayList.getTranslatedText("Export wurde abgebrochen.",
			Language.Deutsch, this.language));
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList
					.getTranslatedText("Fehler beim exportieren der Karten",
							Language.Deutsch, this.language));
			break;
		}
	}

	/** Sets MenuActionListener for all different Languages
	 * @param listener
	 */
	public void setLanguageMenuActionListener(MeinMenuActionListener listener) {
		if (listener.command.equals(Language.Deutsch.toString())) {
			this.menuItemDeutsch.addActionListener(listener);
		} else if (listener.command.equals(Language.Englisch.toString())) {
			this.menuItemEnglisch.addActionListener(listener);
		} else if (listener.command.equals(Language.Französisch.toString())) {
			this.menuItemFranzoesisch.addActionListener(listener);
		} else if (listener.command.equals(Language.Italienisch.toString())) {
			this.menuItemItalienisch.addActionListener(listener);
		}
	}

	/** Sets MenuActionListener for 'Close'
	 * @param listener
	 */
	public void setStartMenuActionListener(MeinMenuActionListener listener) {
		this.menuItemClose.addActionListener(listener);
	}

	
	/** Sets MenuActionListener for 'Settings'
	 * @param listener
	 */
	public void setSettingsMenuActionListener(MeinMenuActionListener listener) {
		this.menuItemSettings.addActionListener(listener);
	}

	/** Sets MenuActionListener for 'Start Learning'
	 * @param listener
	 */
	public void setStartLearningsMenuActionListener(
			MeinMenuActionListener listener) {
		this.menuItemStartLearning.addActionListener(listener);
	}

	/** Sets the MenuActionListener for 'Import'
	 * @param listener
	 */
	public void setImportMenuActionListener(MeinMenuActionListener listener) {
		this.menuItemImport.addActionListener(listener);
	}

	/** Sets the MenuActionListener for 'Export'
	 * @param listener
	 */
	public void setExportMenuActionListener(MeinMenuActionListener listener) {
		this.menuItemExport.addActionListener(listener);
	}

	
	/** Switchs into the selected Language
	 * @param e
	 */
	public void switchLanguage(ActionEvent e) {
		switch ((e.getActionCommand())) {
		case "Deutsch":
			setControlsToBeTranslated(Language.Deutsch);
			break;
		case "Italienisch":
			setControlsToBeTranslated(Language.Italienisch);
			break;
		case "Französisch":
			setControlsToBeTranslated(Language.Französisch);
			break;
		case "Englisch":
			setControlsToBeTranslated(Language.Englisch);
			break;
		default:
			setControlsToBeTranslated(Language.Deutsch);
			break;
		}
	}

	/** Translates a Control into the selectec Language
	 * @param o
	 */
	public void translateTextOfAControl(Object o) {
		for (String[] elem : this.translationArrayList) {
			if (o instanceof JMenuItem) {
				if (elem[this.previousLanguage.value].equals(((JMenuItem) o)
						.getText())) {
					((JMenuItem) o).setText(elem[this.language.value]);

					// Actioncommand muss neu gesetzt werden, damit die
					// deutschen Befehle NICHT mit der fremdsprachigen
					// überschrieben werden!
					((JMenuItem) o)
							.setActionCommand(elem[Language.Deutsch.value]);
					break;
				}
			}

			else if (o instanceof JMenu) {
				if (elem[this.previousLanguage.value].equals(((JMenu) o)
						.getText())) {
					((JMenu) o).setText(elem[this.language.value]);

					// Actioncommand muss neu gesetzt werden, damit die
					// deutschen Befehle NICHT mit der fremdsprachigen
					// überschrieben werden!
					((JMenu) o).setActionCommand(elem[Language.Deutsch.value]);
					break;
				}
			}

			else if (o instanceof JLabel) {
				if (elem[this.previousLanguage.value].equals(((JLabel) o)
						.getText())) {
					((JLabel) o).setText(elem[this.language.value]);
					break;
				}
			}

			else if (o instanceof JFrame) {
				if (elem[this.previousLanguage.value].equals(((JFrame) o)
						.getTitle())) {
					((JFrame) o).setTitle(elem[this.language.value]);
					break;
				}
			}
		}
	}

	
	/** 
	 * @param c: Container
	 * @return: An ArryList with Components
	 */
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

	
	/** Gets all Components of the mainPanel and call the Method
	 *  translateTExtOfAControl for each Control
	 * @param newLanguage
	 */
	public void setControlsToBeTranslated(Language newLanguage) {
		this.previousLanguage = this.language;
		this.language = newLanguage;

		ArrayList<Component> a = getAllComponents(this.mainPanel);
		for (Component c : a) {
			translateTextOfAControl(c);
		}
	}

	/** Sets the Button enabled or disabled
	 * @param button
	 * @param isEnabled
	 */
	public void setButtonState(JButton button, boolean isEnabled) {
		button.setEnabled(isEnabled);
	}

	/** Shows an Error into a Message box
	 * @param error
	 */
	public void displayErrorMessage(String error) {
		JOptionPane.showMessageDialog(this, error);
	}

	/**
	 * @return: The File Export Path
	 */
	public String getFileExportPath() {
		return this.fileExportPath;
	}
	
	/**
	 * @return: The File Import Path
	 */
	public String getFileImportPath() {
		return this.fileImportPath;
	}	
}