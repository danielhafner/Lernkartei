package ch.zbw.lernkartei.model;


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
		if(front!=null && front!=" " && back!=null && back!=" ")
		{
			this.front=front;
			this.back=back;
		}
		else{System.out.println("Felder d�rfen nicht leer sein");}
	}

	public void setBoxAndProbability(int box, int probability)
	{
	
	}
	
	public int getIndex()
	{
		return this.id_Card;
	}

	public void setCalcProbability(int calcProbability)
	{
		this.calcProbability = calcProbability;
		//test
	}
}