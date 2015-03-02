package ch.zbw.lernkartei.model;

import java.util.ArrayList;

public class TranslationDataSet extends ArrayList<String[]>{
	
	public TranslationDataSet()
	{
		this.fillTranslationWithTestData();
	}
	
	public void addToTranslation(String[] a)
	{
		this.add(a);
	}
	
	public void addNewToTranslation(String deutsch, String englisch, String franzoesisch, String italienisch){	
		String[] myTranslateArray = {deutsch, englisch, franzoesisch, italienisch};		
		add(myTranslateArray);
	}
	
	public void removeFromTranslation(String[] a)
	{
		this.remove(a);
	}
	
	public void fillTranslationWithTestData()
	{
		//Übersetzungen müssen in dieser Reihenfolge eingefügt werden: 1. Deutsch, 2. Englisch, 3. Französisch, 4. Italienisch		
		this.addNewToTranslation("Datei", "File", "Fichier", "File");
		this.addNewToTranslation("Beenden", "Close", "Rompre", "Rottura");
		this.addNewToTranslation("Sprache", "Language", "Langue", "Lingua");
		this.addNewToTranslation("Import", "Import", "Importation", "Importazione");
		this.addNewToTranslation("Export", "Export", "Exportation", "Exportazione");
		this.addNewToTranslation("Einstellungen", "Settings", "Paramètres", "Impostazioni");
		this.addNewToTranslation("Deutsch", "German", "Allemand", "Tedesco");
		this.addNewToTranslation("Franz�sisch", "French", "Francais", "Francese");
		this.addNewToTranslation("Italienisch", "Italian", "Italien", "Italiano");
		this.addNewToTranslation("Englisch", "English", "Anglais", "Inglese");
		this.addNewToTranslation("Lernen starten", "Start learning", "Démarrer apprentissage", "Imparare inizio");
		this.addNewToTranslation("Export wurde abgebrochen.", "Export was canceled.", "Export a été annulée.", "Export è stato annullato.");
		this.addNewToTranslation("Import wurde abgebrochen.", "Import was canceled.", "Import a été annul�e.", "Import è stato annullato.");
		this.addNewToTranslation("Karten-Nr.", "Card-No.", "Numéro de Carte", "Numero della Carta");
		this.addNewToTranslation("Karteiname", "Name of the Database", "Nom de la base de données", "Nome del database");
		this.addNewToTranslation("Vorderseite", "Front", "Avant", "Anteriore");
		this.addNewToTranslation("Rückseite", "Back", "Arrière", "Posteriore");
	}
	
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