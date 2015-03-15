package ch.zbw.lernkartei.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.TranslationDataSet;

public class MyFileFilter extends FileFilter {
	
	private TranslationDataSet t = new TranslationDataSet();
	private MainView v;
	
	public MyFileFilter(MainView v)
	{
		this.v = v;
	}

	@Override
	public String getDescription() {
		return t.getTranslatedText("Files with comma-separated values (.csv)",Language.English, v.getCurrentLanguage());
	}
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory())
			return false;
		
		String extension = null;		
		int i = f.getName().lastIndexOf(".");
		if(i > 0)
			{
			//Ab Position des Punktes die Dateiendung ermitteln..
			extension = f.getName().substring(i+1);
			}
				
		if(extension != null)
		{
			if(extension.equals("csv"))
				return true;
		}
		return false;
	}
}
