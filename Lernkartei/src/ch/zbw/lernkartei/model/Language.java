package ch.zbw.lernkartei.model;

public enum Language {
	
	//Werte m�ssen in Deutsch verfasst sein und d�rfen (wegen den ActionCommands) nicht ver�ndert werden!
	Deutsch(0), 
	Englisch(1),
	Französisch(2), 
	Italienisch(3);
	
	public int value;	
		
	Language(int value)
	{
		this.value = value;
	}
}
