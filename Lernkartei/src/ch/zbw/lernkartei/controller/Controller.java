
package ch.zbw.lernkartei.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.zbw.lernkartei.model.Card;
import ch.zbw.lernkartei.model.Language;
import ch.zbw.lernkartei.model.Register;
import ch.zbw.lernkartei.view.GUI;

public class Controller {
	
	private  GUI gui;
	private  Register register;
	private Card card;
	
	public Controller()
	{
		this.gui = new GUI();
		addListener();
	}
	
	public void startApplication()
	{
		try {
			this.gui.paint();
			this.gui.initializeControls();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addListener()
	{
		MeinMenuActionListener mBeenden = new MeinMenuActionListener("Beenden");
		this.gui.setStartMenuActionListener(mBeenden);
		
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(Language.Deutsch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(Language.Englisch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(Language.Französisch.toString()));
		this.gui.setLanguageMenuActionListener(new MeinMenuActionListener(Language.Italienisch.toString()));
		
		MeinMenuActionListener mSettings = new MeinMenuActionListener("Einstellungen");
		this.gui.setSettingsMenuActionListener(mSettings);		
		
		MeinMenuActionListener mStartLearning = new MeinMenuActionListener("Lernen starten");
		this.gui.setStartLearningsMenuActionListener(mStartLearning);
		
		MeinMenuActionListener mImport = new MeinMenuActionListener("Import");
		this.gui.setImportMenuActionListener(mImport);
		
		MeinMenuActionListener mExport = new MeinMenuActionListener("Export");
		this.gui.setExportMenuActionListener(mExport);
		
		MeinButtonActionListener mNavigation = new MeinButtonActionListener();
		this.gui.setNavigationButtonListener(mNavigation);
		
		
	}
	
    public class MeinMenuActionListener implements ActionListener{

    	public String command;
    	
    	public MeinMenuActionListener(String command)
    	{
    		setCommand(command);
    	}
   
    	public void setCommand(String command)
    	{
    		this.command = command;
    	}
 
    	@Override
		public void actionPerformed(ActionEvent e) {
	            if(e.getActionCommand().equals("Beenden"))
	            {
	             gui.closeApplication();
	            }
	            else if(e.getActionCommand().equals("Deutsch"))
	            {
	            	gui.switchLanguage(e);
	            }
	            else if(e.getActionCommand().equals("Franzoesisch"))
	            {
	            	gui.switchLanguage(e);
	            }
	            else if(e.getActionCommand().equals("Englisch"))
	            {
	            	gui.switchLanguage(e);
	            }
	            else if(e.getActionCommand().equals("Italienisch"))
	            {
	            	gui.switchLanguage(e);
	            }
	            else if(e.getActionCommand().equals("Einstellungen"))
	            {
	            	gui.paintSettingsPanel();
	            }
	            else if(e.getActionCommand().equals("Lernen starten"))
	            {
	            	gui.paintPlayPanel();
	            }
	            else if(e.getActionCommand().equals("Import"))
	            {
	            	gui.openFileDialog();
	            }
	            else if(e.getActionCommand().equals("Export"))
	            {
	            	gui.saveFileDialog();
	            }
	            else if (e.getActionCommand().equals("Speichern"))
	            {
	            }
	        }
	    }    


    public class MeinButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			  gui.buttonClicked(e);
            
		}
    	
    }
}