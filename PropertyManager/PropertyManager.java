/*
 * Author: Kevin John Hohl
 * Student Number: S0187382
 * Lecturer: Hans Telford
 * Coordinator: Michael Li
 * Date: Friday, 19 August 2011
 * ----------------------------------
 * COIT12137: Objects And Data Structures
 * PropertyManager.java
 * Description: PropertyManager.java is a Java Windowed Application that
 * provides an interactive Graphical User Interface that allows users to
 * display all property data in the collection, to add, remove and update
 * the property data into/from the collection and also allow users to
 * read/save data from/to a text file.
 */

//imports//
import ds.util.*;
import java.io.*;
import java.text.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;

public class PropertyManager extends JFrame
{

	//CONSTANTS//
	private final String FILE_NAME = "Property.txt";

	//GLOBAL VARIABLES//
	private STree<Property> propertyList = new STree<Property>();

	//TEXT AREAS//
	private JTextArea propertyInfoTextArea;
	private	JTextArea propertySearchTextArea;

	//BUTTONS//
	private JButton goBtn = new JButton("Go");
	private JButton addPropertyBtn = new JButton("Add Property");
	private JButton deletePropertyBtn = new JButton("Delete Property");
	private JButton updatePropertyBtn = new JButton("Update Property");
	private JButton saveFileBtn = new JButton("Save File");

	//TEXTFIELDS//
	private JTextField searchPropertyTextField = new JTextField("", 10);

	//Constructor
	public PropertyManager()
	{
		createGUI();

		//set application details (Title, size, visible, exit functionality)
		setTitle("General Property Data Manager v1.3.37 (Assignment 1) By Kevin John Hohl");
		setSize(760,520);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//Set the frame exit attributes

		//Center program on screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation( ((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2) );

		//read and display the property list file on startup
		readPropertyFile(propertyList);
		displayPropertyList(propertyList);

	}//end constructor

	//function used to create the graphical user interface
	public void createGUI()
	{
		//PANELS//
		JPanel topPanel = new JPanel();
		JPanel searchPropertyPanel = new JPanel();
		JPanel propertyInfoPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		//LABELS//
		JLabel searchPropertyLbl = new JLabel("Search Property: ");

		//TEXTAREAS//
		propertyInfoTextArea = new JTextArea("", 17, 66);
		propertySearchTextArea = new JTextArea("",7, 66);
	    JScrollPane propertyInfoScrollPane = new JScrollPane(propertyInfoTextArea);
	    JScrollPane propertySearchScrollPane = new JScrollPane(propertySearchTextArea);

	    //MENU BAR//
		JMenuBar propertyManagerMenu = new JMenuBar();
		setJMenuBar(propertyManagerMenu);

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");

		//MENU ITEMS//
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem clearMenuItem = new JMenuItem("Clear");
		JMenuItem aboutMenuItem = new JMenuItem("About");

		//TEXTAREA TAB SIZE//
		propertyInfoTextArea.setTabSize(9);
		propertySearchTextArea.setTabSize(9);

		//LAYOUTS//
		setLayout( new BorderLayout() );
		searchPropertyPanel.setLayout(new GridLayout(1,5));
		topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		propertyInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setLayout(new GridLayout(1,4,1,1));

		//ADD TO PANELS//
		topPanel.add(searchPropertyPanel);
		searchPropertyPanel.add(new JLabel());
		searchPropertyPanel.add(new JLabel());
		searchPropertyPanel.add(new JLabel());
		searchPropertyPanel.add(searchPropertyLbl);
		searchPropertyPanel.add(searchPropertyTextField);
		searchPropertyPanel.add(goBtn);

		propertyInfoPanel.add(propertyInfoScrollPane);
		propertyInfoPanel.add(propertySearchScrollPane);

		buttonPanel.add(addPropertyBtn);
		buttonPanel.add(deletePropertyBtn);
		buttonPanel.add(updatePropertyBtn);
		buttonPanel.add(saveFileBtn);

		//ADD TO FRAME//
		add(searchPropertyPanel, BorderLayout.NORTH);
		add(propertyInfoPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		//ActionListeners - Buttons//
		goBtn.addActionListener( new PropertyManagerCommandListeners() );
		addPropertyBtn.addActionListener( new PropertyManagerCommandListeners() );
		deletePropertyBtn.addActionListener( new PropertyManagerCommandListeners() );
		updatePropertyBtn.addActionListener( new PropertyManagerCommandListeners() );
		saveFileBtn.addActionListener( new PropertyManagerCommandListeners() );

		//ActionListeners - Menu Items//
		exitMenuItem.addActionListener( new PropertyManagerCommandListeners() );
		clearMenuItem.addActionListener( new PropertyManagerCommandListeners() );
		aboutMenuItem.addActionListener( new PropertyManagerCommandListeners() );

		//ADD MENU (BAR, ITEM, MENU) //
		propertyManagerMenu.add(fileMenu);
		propertyManagerMenu.add(editMenu);
		propertyManagerMenu.add(helpMenu);
		fileMenu.add(exitMenuItem);
		editMenu.add(clearMenuItem);
		helpMenu.add(aboutMenuItem);

	}//end createGUI

	class PropertyManagerCommandListeners implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();

			if ( command.equals(goBtn.getActionCommand() ) )
			{
				String searchString = searchPropertyTextField.getText().trim(); //trimmed so blank spaces count as an empty search
				if (searchString.length() != 0)
				{
					searchPropertyList(propertyList, searchString); //search for the specified string in the property list
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Search field is empty!\n\nPlease enter a valid search\nrequest and press 'Go'.","Search Help",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if ( command.equals(addPropertyBtn.getActionCommand() ) )
			{
				addProperty(propertyList);
				displayPropertyList(propertyList);
			}
			else if ( command.equals(deletePropertyBtn.getActionCommand() ) )
			{
				int propertyIDtoDelete = 0;
				String propertyIDStr = JOptionPane.showInputDialog(null,"Input Property ID to delete:");
				try
				{
					propertyIDtoDelete = Integer.parseInt(propertyIDStr);
					deleteProperty(propertyList, propertyIDtoDelete);
					displayPropertyList(propertyList);
				}
				catch(NumberFormatException nfe)
				{
                	JOptionPane.showMessageDialog(null,"Please enter a valid integer","Invalid Integer",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if ( command.equals(updatePropertyBtn.getActionCommand() ) )
			{
				int propertyIDtoUpdate = 0;
				String propertyIDStr = JOptionPane.showInputDialog(null,"Input Property ID to update:");
				try
				{
					propertyIDtoUpdate = Integer.parseInt(propertyIDStr);
					updateProperty(propertyList, propertyIDtoUpdate);
					displayPropertyList(propertyList);
				}
				catch(NumberFormatException nfe)
				{
                	JOptionPane.showMessageDialog(null,"Please enter a valid integer","Invalid Integer",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if ( command.equals(saveFileBtn.getActionCommand() ) )
			{
				savePropertyListToFile(propertyList);
			}
			else if ( command.equals("Exit") )
			{
				System.exit(0);
			}
			else if ( command.equals("Clear") )
			{
				propertySearchTextArea.setText("");
			}
			else if ( command.equals("About") )
			{
               	JOptionPane.showMessageDialog(null,"Property Data Manager v 1.3.37 (Assignment 1)\n\nBy Kevin Hohl\n\nCopyright (C) 2011.","About Property Data Manager v1.3.37",JOptionPane.INFORMATION_MESSAGE);
			}

		}//end actionPerformed
	}//end PropertyManagerCommandListeners

	public void readPropertyFile(STree<Property> aPropertyList)
	{
		try
		{
			Scanner propertyReader = new Scanner( new FileReader(FILE_NAME));
			int 	propertyID 	= 0;;
			String 	line = "";
			String 	status 		= "";
			String 	agentName	= "";
			String 	listedDate	= "";
			String 	address		= "";

			while (propertyReader.hasNextLine())
			{
				line = propertyReader.nextLine();
				StringTokenizer propertyStringToken = new StringTokenizer (line,",");

				while (propertyStringToken.hasMoreTokens())
				{
					propertyID 	= 	Integer.parseInt(propertyStringToken.nextToken());
					status 		= 	propertyStringToken.nextToken();
					agentName 	= 	propertyStringToken.nextToken();
					listedDate 	= 	propertyStringToken.nextToken();
					address 	= 	propertyStringToken.nextToken();

				}// end while (sToken.hasMoreTokens())

				aPropertyList.add ( new Property (propertyID, status, agentName, listedDate, address) );
			}// end while (in.hasNextLIne())
			propertyReader.close();
		}// end try
		catch(FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null,"ERROR! File '" + FILE_NAME + "' not found","ERROR: File Not Found",JOptionPane.ERROR_MESSAGE);
			return;
		}// end  fnfe catch
		catch(IOException io)
		{
			JOptionPane.showMessageDialog(null,"ERROR! I/O Exception ","ERROR: I/O Exception",JOptionPane.ERROR_MESSAGE);
		}// end I/O catch

	}//end readPropertyFile

	public void displayPropertyList(STree<Property> aPropertyList)
	{
		String propertyInfoString = "";

		propertyInfoString += "PropertyID\tAddress\t\t\tListed date\tAgent Name\tStatus\n"
							+ "-------------------------------------------------------------------"
							+ "-------------------------------------------------------------------"
							+ "--------------------------------------\n\n";

		if(propertyList.size()>0)
		{
			Iterator<Property> propertyIterator = aPropertyList.iterator();
			while ( propertyIterator.hasNext() )
			{
				Property p = (Property)propertyIterator.next();
				propertyInfoString += p.toString();
			}

			propertyInfoString += "\nTotal " + aPropertyList.size() + " entries!";

			propertyInfoTextArea.setText(propertyInfoString);
		}// end if
		else
		{
			JOptionPane.showMessageDialog(null,"ERROR! There are no properties to display","ERROR: Property List Empty",JOptionPane.ERROR_MESSAGE);
		}

	}// end of displayPropertyList

	public void searchPropertyList(STree<Property> aPropertyList, String searchThis)
   	{
		//Convert everything to lower case so search will ignore case
		String stringToSearch = searchThis.toLowerCase();
		String searchResultString = "";
		int searchResultsFound = 0;

		Iterator<Property> propertyIterator = aPropertyList.iterator();
		while ( propertyIterator.hasNext() )
		{
			Property p = (Property)propertyIterator.next();
			if( Integer.toString(p.getPropertyID() ).contains( stringToSearch ) )
			{
				searchResultString += p.toString();
				searchResultsFound++;
			}
			else if( p.getStatus().toLowerCase().contains( stringToSearch ) )
			{
				searchResultString += p.toString();
				searchResultsFound++;
			}
			else if( p.getAgentName().toLowerCase().contains( stringToSearch ) )
			{
				searchResultString += p.toString();
				searchResultsFound++;
			}
			else if( p.getListedDate().toLowerCase().contains( stringToSearch ) )
			{
				searchResultString += p.toString();
				searchResultsFound++;
			}
			else if( p.getAddress().toLowerCase().contains( stringToSearch ) )
			{
				searchResultString += p.toString();
				searchResultsFound++;
			}
		}//end while loop (searching)

		if (searchResultsFound > 0) {
			propertySearchTextArea.setText(searchResultsFound  + " results found for '" + stringToSearch + "'\n\n" + searchResultString);
		} else {
			propertySearchTextArea.setText("no results found");
		}

	}//end searchPropertyList

	public void addProperty(STree<Property> aPropertyList)
	{
		String 	inputCheck 		= "";
		int 	newPropertyID 	= 0;
		String 	newStatus 		= "";
		String 	newAgentName	= "";
		String 	newListedDate	= "";
		String 	newAddress		= "";
		boolean validPropertyID = false;
		boolean okToContinue = true;

		do
		{
			try
			{
				inputCheck = JOptionPane.showInputDialog(null,"Please input Property ID (an integer between 0 and 5000):" );
				if (inputCheck == null)
				{
					okToContinue = false; //not ok to continue.
					return; //exit the loop if user presses cancel or 'X'
				}

				newPropertyID = Integer.parseInt( inputCheck );

				if ( searchPropertyExists(aPropertyList, newPropertyID) >= 0 ) //propertyID already exists
				{
					validPropertyID = false;
					JOptionPane.showMessageDialog(null,"That Property ID already exists.\n\nPlease enter a valid property ID.","Property ID Exists!",JOptionPane.ERROR_MESSAGE);
				}
				else if (newPropertyID < 0 || newPropertyID > 5000) //propertyID out of bounds
				{
					validPropertyID = false;
                	JOptionPane.showMessageDialog(null,"Please enter a valid integer between 0 and 5000","Invalid Integer",JOptionPane.ERROR_MESSAGE);
				}
				else //else its a valid property ID
				{
					validPropertyID = true;
				}

			}
			catch (NumberFormatException nfe)
			{
				validPropertyID = false;
                JOptionPane.showMessageDialog(null,"Please enter a valid integer between 0 and 5000","Invalid Integer",JOptionPane.ERROR_MESSAGE);
			}

		} while (validPropertyID == false);

		//NEW ADDRESS//
		if (okToContinue = true) //checks if it is ok to continue (user did not press cancel)
		{
			inputCheck = JOptionPane.showInputDialog(null,"Please input Property Address: " );
			if (inputCheck == null)
			{
				okToContinue = false; //not ok to continue.
				return; //exit the function if user presses cancel or 'X'
			}
			else
			{
				if(inputCheck.trim().length() < 35)
				{
					inputCheck += "\t"; //purely for proper column formatting purposes only
				}
				newAddress = inputCheck;
			}
		}

		//NEW DATE// (now with free optional date validation!)
		if (okToContinue = true) //checks if it is ok to continue (user did not press cancel)
		{
			boolean validDate = false;

			do
			{
				inputCheck = JOptionPane.showInputDialog(null,"Please input the Listed Date of the Property (dd/mm/yy):  " );
				if (inputCheck == null)
				{
					okToContinue = false; //not ok to continue.
					return; //exit the loop if user presses cancel or 'X'
				}

				if ( checkValidDate(inputCheck) )
				{
					validDate = true;
					newListedDate = inputCheck;
				}
				else
				{
                	JOptionPane.showMessageDialog(null,"Please enter a valid date in the format 'dd/MM/yy' ","Invalid Date",JOptionPane.ERROR_MESSAGE);
				}
			} while (validDate == false);
		}

		//NEW AGENT NAME//
		if (okToContinue = true) //checks if it is ok to continue (user did not press cancel)
		{
			inputCheck = JOptionPane.showInputDialog(null,"Please input Agent Name: " );
			if (inputCheck == null)
			{
				okToContinue = false; //not ok to continue.
				return; //exit the function if user presses cancel or 'X'
			}
			else
			{
				newAgentName = inputCheck.trim();
			}
		}

		//NEW STATUS//
		if (okToContinue = true) //checks if it is ok to continue (user did not press cancel)
		{
			inputCheck = JOptionPane.showInputDialog(null,"Please input Property Status: " );
			if (inputCheck == null)
			{
				okToContinue = false; //not ok to continue.
				return; //exit the function if user presses cancel or 'X'
			}
			else
			{
				newStatus = inputCheck.trim();
			}
		}

		if (okToContinue = true) //checks if it is ok to continue (user did not press cancel)
		{
			aPropertyList.add ( new Property (newPropertyID, newStatus, newAgentName, newListedDate, newAddress) );
		}


	}//end addProperty

	public void deleteProperty(STree<Property> aPropertyList, int propIDToDelete)
	{

		if(searchPropertyExists(propertyList,propIDToDelete) >= 0)//checks if property ID exists. returns the property ID if it does -1 if not
		{
			Iterator<Property> deletePropertyIterator = aPropertyList.iterator();
			while ( deletePropertyIterator.hasNext() )
			{
				Property p = (Property)deletePropertyIterator.next();
				if (p.getPropertyID() == propIDToDelete)
				{
					aPropertyList.remove(p);
					break; //breaks out of the iterator so it does not go any further
				}
			}
		}
		else
		{
			//property ID not found
            JOptionPane.showMessageDialog(null,"Property ID does not exist.\n\nNo record is deleted", "ERROR: Invalid Property ID",JOptionPane.ERROR_MESSAGE);
		}
	}//end deleteProperty

	public void updateProperty(STree<Property> aPropertyList, int propIDToUpdate)
	{

		if(searchPropertyExists(propertyList,propIDToUpdate) >= 0)//checks if property ID exists. returns the property ID if it does -1 if not
		{
			String statusUpdate = JOptionPane.showInputDialog(null,"Please input updated Property Status (PropertyID " + propIDToUpdate + ")." );
			if (statusUpdate != null)
			{
				Iterator<Property> propertyIterator = aPropertyList.iterator();
				while ( propertyIterator.hasNext() )
				{
					Property p = (Property)propertyIterator.next();
					if (p.getPropertyID() == propIDToUpdate)
					{
						p.setStatus(statusUpdate);
						break; //breaks out of the iterator so it does not go any further
					}
				}
			}
		}
		else
		{
			//property ID not found
			JOptionPane.showMessageDialog(null,"Property ID does not exist.\n\nNo record will be updated", "ERROR: Invalid Property ID",JOptionPane.ERROR_MESSAGE);
		}

	}//end updateProperty

	private void savePropertyListToFile(STree<Property> aPropertyList)
	{
		try
		{
			PrintWriter propertyWriter = new PrintWriter(new FileWriter(FILE_NAME));

			int 	propertyID 	= 0;;
			String 	status 		= "";
			String 	agentName	= "";
			String 	listedDate	= "";
			String 	address		= "";

			Iterator<Property> propertyIterator = aPropertyList.iterator();
			while ( propertyIterator.hasNext() )
			{
				Property currentProperty = propertyIterator.next();

				propertyID = currentProperty.getPropertyID();
				status = currentProperty.getStatus();
				agentName = currentProperty.getAgentName();
				listedDate = currentProperty.getListedDate();
				address = currentProperty.getAddress();

				propertyWriter.println(propertyID+ "," + status + "," + agentName + "," + listedDate +","+ address); //writes into the file with correct formatting
			}

			propertyWriter.close();
			JOptionPane.showMessageDialog(null,"Save Complete!","Save File", JOptionPane.INFORMATION_MESSAGE);

		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,"Save Failed!","Save File", JOptionPane.ERROR_MESSAGE);
		}
	}//end savePropertyListToFile

	//check if property ID exists. returns the property ID if it does -1 if not
	public int searchPropertyExists(STree<Property> aPropertyList, int propIDToSearch)
	{
		Iterator<Property> propertyIterator = aPropertyList.iterator();
		while ( propertyIterator.hasNext() )
		{
			Property p = (Property)propertyIterator.next();
			if (p.getPropertyID() == propIDToSearch) //compare propertyID
			{
				return propIDToSearch;
			}
		}
		return -1;
	}//end searchPropertyExists

	public boolean checkValidDate(String dateToCheck) {

		final String DATE_FORMAT = "dd/MM/yy";

		dateToCheck = dateToCheck.trim();

	    //set the date format to dd/MM/yy
	    SimpleDateFormat ddMMyyFormat = new SimpleDateFormat(DATE_FORMAT);

	    ddMMyyFormat.setLenient(false); //parsing cannot be lenient. must follow dd/MM/yy format

		if (dateToCheck.length() != DATE_FORMAT.length())
		{
      		return false;//returns false if the date string is too long/ too short
		}

	    try
	    {
	      //tries to parse the dateToCheck string to the date format
	      ddMMyyFormat.parse(dateToCheck.trim());
	    }
	    catch (ParseException pe) {
	      return false; //returns false if unable to parse
	    }
	    return true;//otherwise returns true
  	}

	public static void main(String[] args) {

		PropertyManager propertyManager = new PropertyManager(); //creates an instance of PropertyManager class

	}//end main

}//end class
