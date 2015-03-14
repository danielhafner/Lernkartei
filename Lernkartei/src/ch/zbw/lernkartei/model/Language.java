package ch.zbw.lernkartei.model;

public enum Language {
	
	//Werte müssen in Deutsch verfasst sein und dürfen (wegen den ActionCommands) nicht verändert werden!
	Deutsch(0), 
	English(1),
	Francais(2), 
	Italiano(3);
	
	public int value;	
		
	Language(int value)
	{
		this.value = value;
	}
}
