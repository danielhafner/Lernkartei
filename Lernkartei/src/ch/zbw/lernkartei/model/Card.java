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

	/**
	* @Author: Daniel
	* Alternative constructor. Used for Import.
	*/
	public Card(int id_Card, String front, String back, int probability, int calcProbability, int box)
	{
		this.id_Card = id_Card;
		this.front = front;
		this.back = back;
		this.probability = probability;
		this.calcProbability = calcProbability;
		this.box = box;
	}
	
	/**
	* @Author: Daniel
	* Checks whether the parameters front and back are neither null nor only consisting of a space.
	* If true: Writes parameters into the fields. If false: Prints error message.
	*/
	public void setCard(String front, String back)
	{
		if(front!=null && front!=" " && back!=null && back!=" ")
		{
			this.front=front;
			this.back=back;
		}
		else{System.out.println("Felder duerfen nicht leer sein");}
	}

	/**
	* @Author: Daniel
	* Writes parameters into fields and executes method setCalcProbability.
	*/
	public void setBoxAndProbability(int box, int probability)
	{
		this.box = box;
		this.probability = probability;
		setCalcProbability();
	}
	
	/**
	* @Author: Elias
	* Resets the card 
	*/
	public void resetBoxAndProbability()
	{
		box=1;
		probability=1;
	}
	
	/**
	* @Author: Elias
	* Return the number of the box
	*/
	public int getBox()
		{
			return box;
		}
	
	/**
	* @Author: Elias
	* Returns the id of the card
	*/
	public int getIdCard()
	{
		return this.id_Card;
	}

	/**
	* @Author: Elias
	* return the calculated probability
	*/
	public int getCalcProbability()
	{
		return this.calcProbability;
	}

	/**
	* @Author: Daniel
	* Calculates a random number which is between 1 and 10 and multiplies this number with the probability. 
	* This way the probability rises randomly and always stays in relation with the older probability.
	*/
	public void setCalcProbability()
	{
		int rnd;
		Random random = new Random();
		rnd = random.nextInt(9)+1;
		this.calcProbability = this.probability*rnd;
	}
	
	/**
	* @Author: Elias
	* Returns the probability	
	*/
	public int getProbability()
	{
		return probability;
	}
	
	/**
	* @Author: Elias
	* Returns the front of the card
	*/
	public String getFront()
	{
		return this.front;
	}
	
	/**
	* @Author: Elias
	* Returns the back of the card
	*/	
	public String getBack()
	{
		return this.back;
	}
}