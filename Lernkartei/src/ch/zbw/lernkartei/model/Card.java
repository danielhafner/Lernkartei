package ch.zbw.lernkartei.model;
import java.util.Random;


public class Card 
{
	private int id_Card;
	private String front;
	private String back;
	private int probability;
	private int calcProbability;
	private int box;

	public Card(String front, String back, int id_Card)
	{
	this.id_Card = id_Card;
	this.front = front;
	this.back = back;
	this.probability = 2;
	this.box = 1;
	setCalcProbability();
	}
	
	public Card(){
	}

	public Card(int id_Card, String front, String back, int probability, int calcProbability, int box)
	{
		this.id_Card = id_Card;
		this.front = front;
		this.back = back;
		this.probability = probability;
		this.calcProbability = calcProbability;
		this.box = box;
	}
	
	public boolean equals(int calcProbability)
	{
		if(calcProbability == this.calcProbability)
		{
			return true;
		}
		return false;
	}
	
	public void setCard(String front, String back)
	{
		if(front!=null && front!=" " && back!=null && back!=" ")
		{
			this.front=front;
			this.back=back;
		}
		else{System.out.println("Felder duerfen nicht leer sein");}
	}

	public void setBoxAndProbability(int box, int probability)
	{
		this.box = box;
		this.probability = probability;
		setCalcProbability();
	}
	
	public int getBox()
		{
			return box;
		}
	
	public int getIdCard()
	{
		return this.id_Card;
	}

	public int getcalcProbability()
	{
		return this.calcProbability;
	}

	public void setCalcProbability()
	{
		int rnd;
		Random random = new Random();
		rnd = random.nextInt(9)+1;
		this.calcProbability = this.probability*rnd;
	}
	
	public int getProbability()
	{
		return probability;
	}
	
	public String getFront()
	{
		return this.front;
	}
	
	public String getBack()
	{
		return this.back;
	}
}