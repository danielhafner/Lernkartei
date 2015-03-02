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
	private String title;
	private boolean reverseMode;

	public Register() {
		// Dieser Konstruktor wird nur für Testzwecke aufgerufen - so
		// implementiert lassen
		setTitle("Aus Methode getTestDataSet()");
		this.cards = new ArrayList<Card>();
		this.cards = getDataFromInternalFile("src/data.csv");
	}

	public Register(ArrayList<Card> cards, String title) {
		this.cards = cards;
		this.title = title;
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
			
			while((line=br.readLine())!=null)
			{				
				this.cards.removeAll(cards);
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

	}

	public void sortList() {
		ArrayList<Integer> sortedNumbers = new ArrayList();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator i = this.cards.iterator();
		while (i.hasNext()) {
			Card card = (Card) i.next();
			int prob = card.getcalcProbability();
			sortedNumbers.add(prob);
		}

		Collections.sort(sortedNumbers);

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

	public void export(String destinationPath) {
		try {
			FileWriter writer = new FileWriter(destinationPath);
			Iterator i = this.cards.iterator();
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

	public void setReverseMode(boolean reverseMode) {

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

	public ArrayList<Card> getCardsByBox(int box) {
		Iterator i = this.cards.iterator();
		ArrayList<Card> cards = new ArrayList<Card>();
		while (i.hasNext()) {
			Card card = (Card) i.next();
			if (card.getBox() == box) {
				cards.add(card);
			}
		}
		return cards;
	}

	public Card getNextRandomCard() {
		Card a;
		a = null;
		return a;
	}

	public ArrayList<Card> getTestDataSet() {
		ArrayList<Card> myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Gebäude", "Building"));
		myTestCards.add(new Card("rot", "red"));
		myTestCards.add(new Card("Fenster", "Window"));
		return myTestCards;
	}

	public int getNumberOfCards() {
		return cards.size();
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public Card getCardByIndex(int index) {
		if (this.cards != null && this.cards.size() > 0
				&& index < this.cards.size() && index >= 0) {
			return this.cards.get(index);
		}
		return null;
	}
}
