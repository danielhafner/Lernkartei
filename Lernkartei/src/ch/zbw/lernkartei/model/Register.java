package ch.zbw.lernkartei.model;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Register 
{
	private ArrayList<Card> cards;
	private String title;
	private boolean reverseMode;
	private ArrayList<Card> myTestCards;
	
	public Register()
	{
		myTestCards = new ArrayList<Card>();
	}
	
	public Register(ArrayList<Card> cards,String title)
	{
		this.cards = cards;
		this.title = title;
	}
	
	public void imports(String sourcePath)
	{
		this.cards = getTestDataSet();
	}
	
	public void sortList()
	{
		ArrayList<Integer> sortedNumbers = new ArrayList();
		ArrayList<Card> sortedCards = new ArrayList<Card>();
		Iterator i = this.cards.iterator();
		while(i.hasNext())
		{
			Card card = (Card)i.next();
			int prob = card.getcalcProbability();
			sortedNumbers.add(prob);
		}
		
		Collections.sort(sortedNumbers);
		
		while(i.hasNext())
		{
			Card card = (Card)i.next();
			
			for(int n = 0; n < this.cards.size(); n++)
			{
				if(card.equals(sortedNumbers.get(n)))
				{
					sortedCards.add(card);
					sortedNumbers.remove(n);
					break;
				}
			}
		}
		this.cards = sortedCards;
	}
	
	public void export(String destinationPath)
	{
		
	}
	
	public void saveChanges(Card card)
	{
		this.cards.set(card.getIndex(), card);
	}
	
	public void setReverseMode(boolean reverseMode)
	{
		
	}
	
	public boolean remove(Card card)
	{
		Iterator i = this.cards.iterator();
		while(i.hasNext())
		{
			if(card.equals(i.next()))
			{
				this.cards.remove(card);
				return true;
			}
		}
		return false;
	}

	public boolean add(Card card)
	{
		Iterator i = this.cards.iterator();
		while(i.hasNext())
		{
			if(card.equals(i.next()))
			{
				return false;
			}
		}
		this.cards.add(card);
		return true;
	}
	
	public ArrayList<Card> getCards()
	{
		return this.cards;
	}
	
	public ArrayList<Card> getCardsByBox(int box)
	{
		Iterator i = this.cards.iterator();
		ArrayList<Card> cards = new ArrayList<Card>();
		while(i.hasNext())
		{
			Card card = (Card) i.next();
			if(card.getBox() == box)
			{
				cards.add(card);
			}
		}
		
		return cards;
	}
	
	public Card getNextRandomCard()
	{
		Card a;
		a = null;
		return a;		
	}
	
	public ArrayList<Card> getTestDataSet()
	{
		myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Gebäude", "Building"));
		myTestCards.add(new Card("rot", "red"));
		myTestCards.add(new Card("Fenster", "Window"));
		return myTestCards;
	}
	
	public int getNumberOfCards()
	{
			return myTestCards.size();
	}
}
