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

	public Card(String front, String back)
	{
	this.id_Card = id_Card;
	this.front = front;
	this.back = back;
	this.probability = 2;
	this.calcProbability = 0;
	this.box = 1;
	}
	
	public Card(){
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
		setCalcProbability(this.probability);
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
		setCalcProbability(this.probability);
		return this.calcProbability;
	}

	public void setCalcProbability(int calcProbability)
	{
		int rnd;
		Random random = new Random();
		rnd = random.nextInt(9)+1;
		this.calcProbability = calcProbability*rnd;
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