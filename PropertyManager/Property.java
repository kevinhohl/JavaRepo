/*
	File Name:	Property.java

*/

// Property Class
public class Property implements Comparable <Property>
{

	//private data
	private int propertyID;
	private String status;
	private String agentName;
	private String listedDate;
	private String address;

	//public methods

	//default constructor
	public Property()
	{
		this (0, "", "", "", "");
	}

	//parameterized constructor
	public Property(int aPropertyID, String aStatus, String aAgentName, String aListedDate, String aAddress)
	{
		propertyID 	= aPropertyID;
		status 		= aStatus;
		agentName 	= aAgentName;
		listedDate	= aListedDate;
		address		= aAddress;
	}

	//accessor methods
	public int getPropertyID()
	{
		return propertyID;
	}

	public String getStatus()
	{
		return status;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public String getListedDate()
	{
		return listedDate;
	}

	public String getAddress()
	{
		return address;
	}

	//mutator methods
	public void setPropertyID(int aPropertyID)
	{
		propertyID = aPropertyID;
	}

	public void setStatus(String aStatus)
	{
		status = aStatus;
	}

	public void setAgentName(String aAgentName)
	{
		agentName = aAgentName;
	}

	public void setListedDate(String aListedDate)
	{
		listedDate = aListedDate;
	}

	public void setAddress(String aAddress)
	{
		address = aAddress;
	}

	//toString method
	public String toString()
	{
		return 	" " +    getPropertyID()
				+ "\t" + getAddress()
				+ "\t" + getListedDate()
				+ "\t" + getAgentName()
				+ "\t" + getStatus()
				+ "\n";
	}

	// compareTo()
	// assume agentName takes precedence to the status
	// for the natural ordering
	public int compareTo (Property p)
	{

		if (agentName.compareTo(p.agentName) > 0)
		{
			return 1;
		}
		else if (agentName.compareTo (p.agentName) < 0)
		{
			return -1;
		}
		else
		{
			// same agentName
			if (status.compareTo (p.status) > 0)
			{
				return 1;
			}
			else if (status.compareTo (p.status) < 0)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}

	}

}