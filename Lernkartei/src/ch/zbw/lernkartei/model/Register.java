package ch.zbw.lernkartei.model;
import java.util.ArrayList;
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
	
	public Card getNextRandomCard()
	{
		Card a;
		a = null;
		return a;		
	}
	
	public ArrayList<Card> getTestDataSet()
	{
		myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Geb�ude", "Building"));
		myTestCards.add(new Card("rot", "red"));
		myTestCards.add(new Card("Fenster", "Window"));
		return myTestCards;
	}
	public int getNumberOfCards()
		{
			return cards.length;
		}
}
