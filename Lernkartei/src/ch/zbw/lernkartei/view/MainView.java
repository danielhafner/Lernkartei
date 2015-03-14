package ch.zbw.lernkartei.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.Console;
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
import javax.swing.text.html.Option;

import ch.zbw.lernkartei.controller.Controller.MyMenuActionListener;
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
	private JLabel labelWelcome;

	private JMenu menuDatei;
	private JMenu menuSprache;
	private JMenuBar menuBar;

	private JMenuItem menuItemStartLearning;
	private JMenuItem menuItemEditCards;
	private JMenuItem menuItemResetLearningStatus;
	private JMenuItem menuItemImport;
	private JMenuItem menuItemExport;
	private JMenuItem menuItemClose;
	private JMenuItem menuItemDeutsch;
	private JMenuItem menuItemFranzoesisch;
	private JMenuItem menuItemItalienisch;
	private JMenuItem menuItemEnglisch;

	private JPanel panelMain;
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

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.panelMain = new JPanel(new GridLayout(1, 1));
		this.menuBar = new JMenuBar();

		// Menu
		this.menuDatei = new JMenu("Datei");
		this.menuItemStartLearning = new JMenuItem("Lernen starten");
		this.menuItemEditCards = new JMenuItem("Karten verwalten");
		this.menuItemResetLearningStatus = new JMenuItem("Lernstand zurücksetzen");
		this.menuItemImport = new JMenuItem("Import");
		this.menuItemExport = new JMenuItem("Export");
		this.menuItemClose = new JMenuItem("Beenden");
		this.menuSprache = new JMenu("Sprache");
		this.menuItemDeutsch = new JMenuItem("Deutsch");
		this.menuItemFranzoesisch = new JMenuItem("Francais");
		this.menuItemItalienisch = new JMenuItem("Italiano");
		this.menuItemEnglisch = new JMenuItem("English");

		this.translationArrayList = new TranslationDataSet();
		this.jfImportFile = new JFileChooser();

		ImageIcon imageIconBackground = new ImageIcon(this.getClass().getResource("/bg-sea.jpg"));
		imageIconBackground.setDescription("Isch das en Scheiss");
		labelImageIconBackground = new JLabel(imageIconBackground);
		labelWelcome = new JLabel(""
				+ "<html><body>"
				+ "<br><br><br><br><br><br><br><br><br><br><br>"
				+ "<h1>Vokabeltrainer v.1.0</h1>"
				+ "<br><br>"
				+ "Wenn A für Erfolg steht, lautet die Formel:"
				+ "<br> A = X + Y + Z. X steht für Arbeit, Y ist Muße und Z heißt Mund halten."
				+ "<br> Albert Einstein"
				+ "</body></html>");
		labelWelcome.setFont(MyFont.Ueberschrift4.getMyFont());
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
		menuDatei.add(menuItemEditCards);
		menuDatei.add(menuItemResetLearningStatus);
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
		labelImageIconBackground.add(this.panelMain);
		labelImageIconBackground.add(this.labelWelcome);
		this.add(labelImageIconBackground);
		this.setVisible(true);
	}

	/** Repaints a certain Panel
	 * @param panelToShow
	 */
	public void repaintTheFrame(JPanel panelToShow) {
		labelImageIconBackground.removeAll();
		this.panelMain.removeAll();
		this.panelMain.add(panelToShow);		
		labelImageIconBackground.add(this.panelMain);		
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
	public boolean openFileDialog() {
		switch (this.jfImportFile.showOpenDialog(this)) {
		case JFileChooser.APPROVE_OPTION:
			fileImportPath = jfImportFile.getSelectedFile().toString();
			return true;
		case JFileChooser.CANCEL_OPTION:
			fileImportPath = null;
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList
					.getTranslatedText("Fehler beim importieren der Daten",
							Language.Deutsch, this.language));
			break;
		}
	return false;
	}

	/**
	 * Saves as File unter the selected Path
	 */
	public boolean saveFileDialog() {
		switch (this.jfImportFile.showSaveDialog(this)) {
		case JFileChooser.APPROVE_OPTION:
			fileExportPath = jfImportFile.getSelectedFile().toString();
			break;
		case JFileChooser.CANCEL_OPTION:
			fileExportPath = null;
			break;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(this, translationArrayList
					.getTranslatedText("Fehler beim exportieren der Karten",
							Language.Deutsch, this.language));
			break;
		}
		
		if(fileExportPath != null)
			return true;
		else
			return false;
	}

	/** Sets MenuActionListener for all different Languages
	 * @param listener
	 */
	public void setLanguageMenuActionListener(MyMenuActionListener listener) {
		if (listener.command.equals(Language.Deutsch.toString())) {
			this.menuItemDeutsch.addActionListener(listener);
		} else if (listener.command.equals(Language.English.toString())) {
			this.menuItemEnglisch.addActionListener(listener);
		} else if (listener.command.equals(Language.Francais.toString())) {
			this.menuItemFranzoesisch.addActionListener(listener);
		} else if (listener.command.equals(Language.Italiano.toString())) {
			this.menuItemItalienisch.addActionListener(listener);
		}
	}

	/** Sets MenuActionListener for 'Close'
	 * @param listener
	 */
	public void setStartMenuActionListener(MyMenuActionListener listener) {
		this.menuItemClose.addActionListener(listener);
	}

	
	/** Sets MenuActionListener for 'Edit cards'
	 * @param listener
	 */
	public void setEditCardsMenuActionListener(MyMenuActionListener listener) {
		this.menuItemEditCards.addActionListener(listener);
	}

	/** Sets MenuActionListener for 'Start Learning'
	 * @param listener
	 */
	public void setStartLearningsMenuActionListener(
			MyMenuActionListener listener) {
		this.menuItemStartLearning.addActionListener(listener);
	}

	/** Sets the MenuActionListener for 'Import'
	 * @param listener
	 */
	public void setImportMenuActionListener(MyMenuActionListener listener) {
		this.menuItemImport.addActionListener(listener);
	}

	/** Sets the MenuActionListener for 'Export'
	 * @param listener
	 */
	public void setExportMenuActionListener(MyMenuActionListener listener) {
		this.menuItemExport.addActionListener(listener);
	}
	
	public void setResetLearningStatusActionListener(MyMenuActionListener listener)
	{
		this.menuItemResetLearningStatus.addActionListener(listener);
	}
	
	/** Switchs into the selected Language
	 * @param e
	 */
	public void switchLanguage(ActionEvent e) {
		switch ((e.getActionCommand())) {
		case "Deutsch":
			setControlsToBeTranslated(Language.Deutsch);
			break;
		case "Italiano":
			setControlsToBeTranslated(Language.Italiano);
			break;
		case "Francais":
			setControlsToBeTranslated(Language.Francais);
			break;
		case "English":
			setControlsToBeTranslated(Language.English);
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
			{
				if(getAllComponents((Container) comp).size() > 0)
				{
				  compList.addAll(getAllComponents((Container) comp));
				}
			}	
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
		
		ArrayList<Component> a = getAllComponents(this.panelMain); 
		a.addAll(getAllComponents(this.menuBar));
		a.addAll(getAllComponents(this.menuDatei));
			
		// Gewisse Komponenten einfach adden...
		a.add(this.menuItemStartLearning);
		a.add(this.menuItemEditCards);
		a.add(this.menuItemResetLearningStatus);
		a.add(this.menuItemImport);
		a.add(this.menuItemExport);
		a.add(this.menuItemClose);	
		for (Object c : a) {			
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

	/** Asks the User to Save Changes (Export to the internal csv-File)
	 * @return
	 */
	public boolean quitAndSave() {	
		if(JOptionPane.showConfirmDialog(
				this
				,translationArrayList.getTranslatedText("Willst du deinen Lernstand speichern?", Language.Deutsch, this.language)
				,translationArrayList.getTranslatedText("Hinweis", Language.Deutsch, this.language)
				,0 /* Ja oder NEIN */
				) == 0)
				{
					// 0 = Ja
					return true;
				}		
		return false;
	}
	
	public Language getCurrentLanguage()
	{
		return this.language;
	}
}