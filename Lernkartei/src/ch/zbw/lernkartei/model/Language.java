package ch.zbw.lernkartei.model;

public enum Language {
	
	//Werte müssen in Deutsch verfasst sein und dürfen (wegen den ActionCommands) nicht verändert werden!
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
