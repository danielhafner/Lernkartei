
public class Card 
{
	private int id_Card;
	private String front;
	private String back;
	private int probability;
	private int calcProbability;
	private int box;

	public Card(String front, String back)
	{
	this.front = front;
	this.back = back;
	this.probability = 2;
	this.calcProbability = 0;
	this.box = 1;
	}

	public boolean equals(Card card)
	{
		return true;
	}
	public void setCard(String front, String back)
	{
	
	}

	public void setBoxAndProbability(int box, int probability)
	{
	
	}

	public void setCalcProbability()
	{
	
	}
}
