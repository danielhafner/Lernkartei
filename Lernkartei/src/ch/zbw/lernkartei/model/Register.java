package ch.zbw.lernkartei.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Register {
	private ArrayList<Card> cards;
	private int maxId_Card;

	public Register() {
		// Dieser Konstruktor wird nur für Testzwecke aufgerufen - so
		// implementiert lassen
		this.cards = new ArrayList<Card>();
		this.cards = getDataFromInternalFile("src/data.csv");
	}

	public Register(ArrayList<Card> cards, String title) {
		this.cards = cards;
	}

	public void imports(String sourcePath)
	{
		this.cards = getDataFromInternalFile(sourcePath);	
	}

	private ArrayList<Card> getDataFromInternalFile(String sourcePath) {
		BufferedReader br = null;
		FileReader fl;
		String line = "";
		String delim = ";";		
		try
		{
			fl = new FileReader(sourcePath);
			br = new BufferedReader(fl);
			this.cards.removeAll(cards);
			
			while((line=br.readLine())!=null)
			{
				String[] newcard = line.split(delim);
				this.cards.add(new Card(Integer.parseInt(newcard[0]), newcard[1], newcard[2], Integer.parseInt(newcard[3]), Integer.parseInt(newcard[4]), Integer.parseInt(newcard[5])));
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

	private void saveDataIntoInternalFile(String string) {
		//Wenn interner Pfad angegeben wurde muss unterschieden werden
		//zwischen Windows und Unix-basierten Systemen, da / und \ unterschiedlich? dh
	}

	public void sortList() 
	{
		ArrayList<Integer> sortedNumbers = new ArrayList<Integer>();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = (Card) i.next();
			int prob = card.getcalcProbability();
			sortedNumbers.add(prob);
		}

		Collections.sort(sortedNumbers);

		i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = (Card) i.next();

			for (int n = 0; n < this.cards.size(); n++) {
				if (card.getcalcProbability() == (sortedNumbers.get(n))) {
					sortedCards.add(card);
					sortedNumbers.remove(n);
					break;
				}
			}
		}
		this.cards = sortedCards;
	}
	
	public ArrayList<Card> getSortedCardsByCardID() throws Exception
	{
		ArrayList<Integer> sortedNumbers = new ArrayList<Integer>();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator<Card> i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = (Card) i.next();
			int id = card.getIdCard();
			sortedNumbers.add(id);
		}

		Collections.sort(sortedNumbers);

		i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = (Card) i.next();

			for (int n = 0; n < this.cards.size(); n++) {
				if (card.getIdCard() == (sortedNumbers.get(n))) {
					sortedCards.add(card);
					sortedNumbers.remove(n);
					break;
				}
			}
		}
		if (sortedCards == null)
			throw new Exception("Keine Karten vorhanden.");
			
		return sortedCards;
		
	}

	public void export(String destinationPath) {
		try {
			FileWriter writer = new FileWriter(destinationPath);
			Iterator<Card> i = this.cards.iterator();
			while (i.hasNext()) {
				Card card = (Card) i.next();
				writer.append(card.getIdCard() + ";" + card.getFront() + ";"
						+ card.getBack() + ";" + card.getProbability() + ";"
						+ card.getcalcProbability() + ";" + card.getBox()
						+ "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveChanges(Card card) {
		this.cards.set(card.getIdCard(), card);
	}

	public boolean remove(Card card) {
		Iterator i = this.cards.iterator();
		while (i.hasNext()) {
			if (card.equals(i.next())) {
				this.cards.remove(card);
				return true;
			}
		}
		return false;
	}

	public boolean add(Card card) {
		Iterator i = this.cards.iterator();
		while (i.hasNext()) {
			if (card.equals(i.next())) {
				return false;
			}
		}
		this.cards.add(card);
		return true;
	}

	public ArrayList<Card> getCards() throws Exception {
		if (this.cards == null)
			throw new Exception("Keine Karten vorhanden.");

		return this.cards;
	}

	public ArrayList<Card> getCardsByBox(int box) throws Exception{
		sortList();
		Iterator<Card> i = this.cards.iterator();
		ArrayList<Card> cards = new ArrayList<Card>();
		while (i.hasNext()) {
			Card card = (Card) i.next();
			if (card.getBox() == box) 
			{
				cards.add(card);
			}
		}
		if (cards == null)
			throw new Exception("Keine Karten vorhanden.");
		
		return cards;
	}

	public ArrayList<Card> getTestDataSet() {
		ArrayList<Card> myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Gebäude", "Building", 1));
		myTestCards.add(new Card("rot", "red", 2));
		myTestCards.add(new Card("Fenster", "Window", 3));
		return myTestCards;
	}

	public int getNumberOfCards() {
		if(cards != null)
		{
			return cards.size();
		}
		return 0;	
	}

	public Card getCardByIndex(int index) {
		if (this.cards != null && this.cards.size() > 0
				&& index < this.cards.size() && index >= 0) {
			return this.cards.get(index);
		}
		return null;
	}
	
	public void isTrue(int id)
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card cardi = (Card) i.next();
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
				cardi.setBoxAndProbability(box, probability);
				break;
			}
		}
	}
	
	public void isFalse(int id)
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card cardi = (Card) i.next();
			if(cardi.getIdCard() == id)
			{
				int probability = cardi.getProbability();
				
				if(probability>2)
				{			
					probability -= 1;
				}
				cardi.setBoxAndProbability(1, probability);
				break;
			}
		}
	}
	
	public ArrayList<Integer> getBoxes()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(this.cards != null)
		{
		 Iterator<Card> it = this.cards.iterator();
		 while(it.hasNext())
		 {
			 int box = ((Card)it.next()).getBox();
			 if(!list.contains(box))
			 {
				 list.add(box);
			 }
		  }
		}
		return list;
	}
	
	public int getMaxId_Card()
	{
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext())
		{
			Card card = (Card)i.next();
			if( this.maxId_Card < card.getIdCard())
			{
				this.maxId_Card = card.getIdCard();
			}
		}
		this.maxId_Card += 1;
		return this.maxId_Card;
	}
}
