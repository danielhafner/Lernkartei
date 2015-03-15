package ch.zbw.lernkartei.model;

import java.util.ArrayList;

/**
 * @author Ruel
 *
 */
public class TranslationDataSet extends ArrayList<String[]>{
	
	/**
	 * Konstructor
	 */
	public TranslationDataSet()
	{
		this.fillWithData();
	}
	
	/** Adds a StringArray to the TranslationsDataSet
	 * @param a
	 */
	public void addToTranslation(String[] a)
	{
		this.add(a);
	}
	
	/** Adds a new Group of words in different Languages
	 * @param deutsch
	 * @param englisch
	 * @param franzoesisch
	 * @param italienisch
	 */
	public void addNewToTranslation(String deutsch, String englisch, String franzoesisch, String italienisch){	
		String[] myTranslateArray = {deutsch, englisch, franzoesisch, italienisch};		
		add(myTranslateArray);
	}
	
	/** Removes a word from the TranslationsDataSet
	 * @param a
	 */
	public void removeFromTranslation(String[] a)
	{
		this.remove(a);
	}
	
	public void fillWithData()
	{
		//Übersetzungen müssen in dieser Reihenfolge eingefügt werden: 1. Deutsch, 2. Englisch, 3. Französisch, 4. Italienisch		
		this.addNewToTranslation("Datei", "File", "Fichier", "File");
		this.addNewToTranslation("Beenden", "Close", "Rompre", "Rottura");
		this.addNewToTranslation("Sprache", "Language", "Langue", "Lingua");
		this.addNewToTranslation("Import", "Import", "Importation", "Importazione");
		this.addNewToTranslation("Export", "Export", "Exportation", "Exportazione");
		this.addNewToTranslation("Karten verwalten", "Edit cards", "Gestion des cartes", "Gestione carte");
		this.addNewToTranslation("Deutsch", "German", "Allemand", "Tedesco");
		this.addNewToTranslation("Französisch", "French", "Francais", "Francese");
		this.addNewToTranslation("Italienisch", "Italian", "Italien", "Italiano");
		this.addNewToTranslation("Englisch", "English", "Anglais", "Inglese");
		this.addNewToTranslation("Lernen starten", "Start learning", "Démarrer apprentissage", "Imparare inizio");
		this.addNewToTranslation("Lernstand zurücksetzen", "Reset the learning level", "Réinitialiser le niveau d'apprentissage", "Ripristinare il livello di apprendimento");
		this.addNewToTranslation("Karten-Nr.", "Card-No.", "Numéro de Carte", "Numero della Carta");
		this.addNewToTranslation("Karteiname", "Name of the Database", "Nom de la base de données", "Nome del database");
		this.addNewToTranslation("Vorderseite", "Front", "Avant", "Anteriore");
		this.addNewToTranslation("Rückseite", "Back", "Arrière", "Posteriore");
		this.addNewToTranslation("Fach", "Box", "Compartiment", "Scomparto");
		this.addNewToTranslation("Willst du deinen Lernstand speichern?", "Want to save your skill level?", "Vous voulez économiser votre niveau de compétence?", "Vuoi risparmiare il vostro livello di abilità?");
		this.addNewToTranslation("Hinweis", "Note", "Remarque", "Nota");
		this.addNewToTranslation("Dein persönliches Ziel", "Your personal goal", "Votre objectif personnel", "Il tuo obiettivo personale");
		this.addNewToTranslation("Bis zu welchem Fach möchtest du lernen?", "How far will you go today?", "Jusqu'où irez-vous aujourd'hui?", "Fino a che punto andrai oggi?");
		this.addNewToTranslation("Anzahl Karten   ", "Number of cards   ", "Numéro des cartes   ", "Numero di carte   ");
		this.addNewToTranslation("Total Karten   ", "Total cards   ", "Nombre de cartes   ", "Totale carte   ");
		this.addNewToTranslation("Anteil korrekt   ", "Proportion correct   ", "Proportion correcte   ", "percentuale di corretto   ");
		this.addNewToTranslation("Anteil falsch   ", "Proportion of false   ", "Proportion correcte   ", "percentuale di falso   ");
		this.addNewToTranslation("Dateien mit kommagetrennten Werten (.csv)", "Files with comma-separated values (.csv)", "Fichiers avec des valeurs séparées par des virgules (.csv)", "I file con valori separati da virgole (.csv)");
		this.addNewToTranslation("Du hast dein Lernziel erreicht!", "You have reached your learning goal!", "Vous avez atteint votre objectif d'apprentissage!", "Hai raggiunto il tuo obiettivo di apprendimento!");
		this.addNewToTranslation("Es sind keine Karten vorhanden. Bitte Karten erfassen.", "There are no Cards available. Please enter some Cards.", "Il n'y a pas de cartes disponibles. Se il vous plaît entrer certaines cartes.", "Non ci sono carte disponibili. Si prega di inserire alcuni Cards.");
	}
	
	/** 
	 * @param originalText
	 * @param languageOriginalText
	 * @param languageTranslatedText
	 * @return Gets the translated Text of a Word (Originaltext)
	 */
	public String getTranslatedText(String originalText, Language languageOriginalText, Language languageTranslatedText)
	{
		for (String[] elem : this)
		{
			if(elem[languageOriginalText.value].equals(originalText))
			{
				return elem[languageTranslatedText.value];
			}
		}
		return originalText;
	}
}