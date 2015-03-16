package ch.zbw.lernkartei.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Register {
	private ArrayList<Card> cards;
	private int maxId_Card;
	private static final Path internalPath =  Paths.get("src/data.csv");
	
	//Statistics
	private int answeredQuestions = 0;
	private int answeredCorrect = 0;
	private int answeredWrong = 0;
	
	private boolean hasChanged = false;

	public Register() {
		// Dieser Konstruktor wird nur für Testzwecke aufgerufen - so
		// implementiert lassen
		this.cards = new ArrayList<Card>();
		this.cards = getDataFromInternalFile(internalPath);
	}

	/**Resets the register
	 * @param cards
	 * @param title
	 */
	public Register(ArrayList<Card> cards, String title) {
		this.cards = cards;
	}

	/*Inserts pre-saved cards into cards
	 * @param sourcePath
	 */
	public void imports(Path sourcePath)
	{
		this.cards = getDataFromInternalFile(sourcePath);
		this.hasChanged = true;
	}

	/**
	* Opens an existing register saved in a csv-file. Takes one line after another and splits lines after each delim (semicolon in our case).
	* The parts of the line are temporarily written into an array and after delivered as parameters into the cards.add method.
	* @param: sourcePath
	*/
	private ArrayList<Card> getDataFromInternalFile(Path sourcePath) {
		BufferedReader br = null;
		FileReader fl;
		String line = "";
		String delim = ";";		
		try
		{
			if (sourcePath != null) {
				fl = new FileReader(sourcePath.toString());
				br = new BufferedReader(fl);
				this.cards.removeAll(cards);
				while ((line = br.readLine()) != null) {
					String[] newcard = line.split(delim);
					this.cards.add(new Card(Integer.parseInt(newcard[0]),
							newcard[1], newcard[2], Integer
									.parseInt(newcard[3]), Integer
									.parseInt(newcard[4]), Integer
									.parseInt(newcard[5])));
				}
				br.close();
				fl.close();
			}
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex2)
		{
			ex2.printStackTrace();
		}
		
		finally
		{
			//
		}
		return cards;
	}

	public void saveDataIntoInternalFile() {
		//Wenn interner Pfad angegeben wurde muss unterschieden werden
		//zwischen Windows und Unix-basierten Systemen, da / und \ unterschiedlich? dh
		export(internalPath);
	}

	/**
	* Sorts all the cards by the calculated probability-Poroperty.
	*/
	public void sortList() 
	{
		ArrayList<Integer> sortedNumbers = new ArrayList<Integer>();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = i.next();
			int prob = card.getCalcProbability();
			sortedNumbers.add(prob);
		}

		Collections.sort(sortedNumbers);

		while (!sortedNumbers.isEmpty())
		{
			int n = 0;
			i = this.cards.iterator();
			while (i.hasNext()) 
			{
				Card card = i.next();
				if (card.getCalcProbability() == (sortedNumbers.get(n))) {
					sortedCards.add(card);
					sortedNumbers.remove(n);
					i.remove();
					break;
				}
			}
		    n++;
		}
		this.cards = sortedCards;
	}
	
	/**
	 * @return Sorts all the cards by CardID-Poroperty and returns it.
	 * @throws Exception
	 */
	public ArrayList<Card> getSortedCardsByCardID() throws Exception
	{
		ArrayList<Integer> sortedNumbers = new ArrayList<Integer>();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = i.next();
			int id = card.getIdCard();
			sortedNumbers.add(id);
		}

		Collections.sort(sortedNumbers);

		while (!sortedNumbers.isEmpty())
		{
			int n = 0;
			i = this.cards.iterator();
			while (i.hasNext()) 
			{
				Card card = i.next();
				if (card.getIdCard() == (sortedNumbers.get(n))) {
					sortedCards.add(card);
					sortedNumbers.remove(n);
					i.remove();
					break;
				}
			}
				n++;
		}
		this.cards = sortedCards;
		return this.cards;
	}
	/**
	* If an export file is given to another user, he might want to reset the cards - so all of them are in box No 1 again and also the probability is set back to 1.
	*/
	public void resetRegister()
	{
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = i.next();
			card.resetBoxAndProbability();
		}
	}
	
	/**Goes through all cards and writes the information of each field into a String. 
	*  Each field is separated with a semicolon and cards are separated with a line break.
	*  In the end the writer-method must be flushed (emptied) and closed.
	 * @param destinationPath
	 */
	public void export(Path destinationPath) {
		try {
			FileWriter writer = new FileWriter(destinationPath.toString());
			Iterator<Card> i = this.cards.iterator();
			while (i.hasNext()) {
				Card card = i.next();
				writer.append(card.getIdCard() + ";" + card.getFront() + ";"
						+ card.getBack() + ";" + card.getProbability() + ";"
						+ card.getCalcProbability() + ";" + card.getBox()
						+ "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**Removes the card in the list and returns a boolean.
	 * @param card
	 * @return Returns true if it was possible to remove a card, false if it was not possible
	 */
	public boolean remove(Card card) {
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			if (card.equals(i.next())) {
				this.cards.remove(card);
				this.hasChanged = true;
				return true;
			}
		}
		return false;
	}

	/** Sorts all the cards by the calculated probability-Poroperty.
	 * @param card
	 * @return true if it was possible to add a Card
	 */
	public void add(Card card) {
		if(card != null)
		this.cards.add(card);
	}
	
	/**Returns all cards
	 * @return Returns all cards
	 * @throws Exception
	 */
	public ArrayList<Card> getCards() throws Exception {
		if (this.cards == null)
			throw new Exception("Keine Karten vorhanden.");

		return this.cards;
	}

	/** 
	 * @param box
	 * @return Returns a list of cards by box.
	 * @throws Exception
	 */
	public ArrayList<Card> getCardsByBox(int box){
		sortList();
		Iterator<Card> i = this.cards.iterator();
		ArrayList<Card> cards = new ArrayList<Card>();
		while (i.hasNext()) {
			Card card = i.next();
			if (card.getBox() == box) 
			{
				cards.add(card);
			}
		}
		return cards;
	}

	/**
	 * @return Returns an ArrayList with Cards (Testing-Data)
	 */
	public ArrayList<Card> getTestDataSet() {
		ArrayList<Card> myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Gebäude", "Building", 1));
		myTestCards.add(new Card("rot", "red", 2));
		myTestCards.add(new Card("Fenster", "Window", 3));
		return myTestCards;
	}

	/**
	 * @return Gets the number of all cards
	 */
	public int getNumberOfCards() {
		if(cards != null)
		{
			return cards.size();
		}
		return 0;	
	}

	/**
	 * @param index
	 * @return Returns a card at a certain index
	 */
	public Card getCardByIndex(int index) {
		if (this.cards != null && this.cards.size() > 0
				&& index < this.cards.size() && index >= 0) {
			return this.cards.get(index);
		}
		return null;
	}
	
	/**Sets the probability und the box of the card.
	 * @param id
	 */
	public void isTrue(int id)
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card cardi = i.next();
			if(cardi.getIdCard() == id)
			{
				int box = cardi.getBox();
				int probability = cardi.getProbability();
				
				if(box < 7)
				{
					box += 1;
				}
				if(probability > this.cards.size())
				{
					probability += 1;			
				}
				this.hasChanged = true;
				cardi.setBoxAndProbability(box, probability);
				break;
			}
		}
		this.answeredQuestions++;
		this.answeredCorrect++;
	}
	
	/**
	* Sets the probability und the box of the card. 
	* @param id of the card
	*/
	public void isFalse(int id)
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card cardi = i.next();
			if(cardi.getIdCard() == id)
			{
				int probability = cardi.getProbability();
				
				if(probability>2)
				{			
					probability -= 1;
				}
				cardi.setBoxAndProbability(1, probability);
				this.hasChanged = true;
				break;
			}
		}
		this.answeredQuestions++;
		this.answeredWrong++;
	}
	
	/**
	 * @return Returns a List of int of all boxes
	 */
	public ArrayList<Integer> getBoxes()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(this.cards != null)
		{
		 Iterator<Card> it = this.cards.iterator();
		 while(it.hasNext())
		 {
			 int box = it.next().getBox();
			 if(!list.contains(box))
			 {
				 list.add(box);
			 }
		  }
		}
		Collections.sort(list);
		return list;
	}
	
	/**
	 * @return Returns the highest id of the cards and count it +1
	 */
	public int getMaxId_Card()
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card card = i.next();
			if( this.maxId_Card < card.getIdCard())
			{
				this.maxId_Card = card.getIdCard();
			}
		}
		this.maxId_Card += 1;
		return this.maxId_Card;
	}

	/**
	 * @return Gets the calculated correct quota
	 */
	public int getCalculateQuotaCorrect() {
		if(this.answeredQuestions != 0)
			return (this.answeredCorrect * 100 / this.answeredQuestions);
		return 0;
	}

	/** 
	 * @return Gets the calculated wrong quota
	 */
	public int getCalculateQuotaWrong() {
		if(this.answeredQuestions != 0)
			return (this.answeredWrong * 100 / this.answeredQuestions);
		return 0;
	}

	public boolean checkCard(String frontText, String backText) {
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			
			Card c = i.next();
			
			if (frontText.equals(c.getFront()) &&
				backText.equals(c.getBack())) {
				return false;
			}
		}
		return true;
	}
	
	/** Sets the value hasChanged
	 * @param b
	 */
	public void setHasChanged(boolean b)
	{
		this.hasChanged = b;
	}
	
	/**Gets the value hasChanged
	 * @return
	 */
	public boolean getHasChanged()
	{
		return this.hasChanged;
	}
	
}
