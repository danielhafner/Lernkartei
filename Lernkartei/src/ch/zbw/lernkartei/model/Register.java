package ch.zbw.lernkartei.model;
import java.util.ArrayList;
import java.util.Iterator;

public class Register 
{
	private ArrayList<Card> cards;
	private String title;
	private boolean reverseMode;
	
	public Register()
	{
		
	}
	
	public Register(ArrayList<Card> cards,String title)
	{
		this.cards = cards;
		this.title = title;
	}
	
	public void imports(String sourcePath)
	{
		
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
	
	public Card getCardById(int id_card)
	{
		Card a;
		a = null;
		return a;
	}
	
	public Card getNextRandomCard()
	{
		Card a;
		a = null;
		return a;		
	}
	
	public ArrayList<Card> getTestDataSet()
	{
		ArrayList<Card> myTestCards = new ArrayList<Card>();
		myTestCards = new ArrayList<Card>();
		myTestCards.add(new Card("Gebäude", "Building"));
		myTestCards.add(new Card("rot", "red"));
		myTestCards.add(new Card("Fenster", "Window"));
		return myTestCards;
	}
}
