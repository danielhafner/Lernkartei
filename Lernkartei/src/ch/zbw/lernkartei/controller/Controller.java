
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
	private int cardId;
	
	public Controller(GUI gui, Register register)
	{
		this.gui = gui;
		this.card = card;
		this.register = register;
		
		addListener();
	}
	
	public void startApplication()
	{
		try {
			this.gui.paint();
			//this.gui.initializeControls();
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
	            	gui.initializeSettingsWithData(register);
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
	        }
	    }

    public class MeinButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			  if(e.getActionCommand().equals("Speichern"))	
			  {
				String front, back;
		
				try
				{
					front = gui.getFrontText();
					back = gui.getBackText();
					if(gui.getCardid() != null)
					{
						cardId = gui.getCardid();			 
						Card modifiedCard = register.getCards().get(cardId);
						modifiedCard.setCard(front, back);
						gui.displayNewCard(modifiedCard, cardId);
					}
					else
					{
						cardId = gui.getCardid() + 1;
						register.add(new Card(front, back));
						gui.displayNewCard(register.getCards().get(register.getCards().size()- 1), cardId);
					}
					gui.setStateDeleteButton(true);
				}
				
				catch(Exception ex)
				{
					gui.displayErrorMessage("Error: " + ex.getMessage());
				}
			  }
			  else if(e.getActionCommand().equals(">>>"))
			  {
				  try {
					  if(cardId < register.getCards().size() - 1)
					  {
						  cardId++;
					  }
					  
					gui.displayNewCard(register.getCards().get(cardId), cardId);
					
					// todo rho: in Methode auslagern, wird beim back button auch verwendet
					  if(register.getCards().get(cardId).getFront().equals("") &&
					  register.getCards().get(cardId).getBack().equals(""))
					  {
						  gui.setStateDeleteButton(false);
					  }
					  else
					  {
						  gui.setStateDeleteButton(true);
					  }
					
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			  }
			  else if(e.getActionCommand().equals("<<<"))
			  {
				  try {
					  if(cardId != 0)
					  {
						  cardId = gui.getCardid() -1;
					  }
					  gui.displayNewCard(register.getCards().get(cardId), cardId);
					  
					  if(register.getCards().get(cardId).getFront().equals("") &&
					  register.getCards().get(cardId).getBack().equals(""))
					  {
						  gui.setStateDeleteButton(false);
					  }
					  else
					  {
						  gui.setStateDeleteButton(true);
					  }
					  
				
				} catch (Exception e1) {
				
					gui.displayErrorMessage(e1.getMessage());
				}
			  }
			  else if(e.getActionCommand().equals("Neu"))
			  {
				  try {
					  cardId = register.getCards().size();
					  Card newCard = new Card("", "");
					  register.add(newCard);
					  gui.displayNewCard(newCard, cardId);
					  gui.setStateDeleteButton(false);
				
				} catch (Exception e1) {
				
					gui.displayErrorMessage(e1.getMessage());
				}
			  }
			  else if(e.getActionCommand().equals("Löschen"))
			  {
				  try {
					  register.getCards().remove(cardId);
					  if(cardId != 0)
					  {
					  cardId--;
					  card = register.getCards().get(cardId);
					  register.add(card);
					  gui.displayNewCard(card, cardId);
					  }
					  else
					  {
						  gui.displayNewCard(new Card("", ""), 0);
						  gui.setStateDeleteButton(false);
					  }
				
				} catch (Exception e1) {
				
					gui.displayErrorMessage(e1.getMessage());
				}
			  }
			  
		}
    }
}