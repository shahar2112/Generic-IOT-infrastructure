package objects;

import java.io.BufferedReader;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

public class CompanyParser implements Serializable
{
	private static final long serialVersionUID = 1L;
	public String name;
	public String email;
	public String password;
	public int company_id;
	
	public static CompanyParser parse(BufferedReader json)
	{
		Gson gson = new Gson();
		
		return gson.fromJson(json, CompanyParser.class);
	}
	
	/* this method returns a new company object with arguments from
	 * the resultSet she receives from the sql query
	 */
	public static CompanyParser parse(ResultSet rSet)
	{
		CompanyParser objToReturn = null;
		
		try 
		{
			while(rSet.next())
			{	
				objToReturn = new CompanyParser();
				objToReturn.name = rSet.getString("name");
				objToReturn.email = rSet.getString("email");
				objToReturn.company_id = rSet.getInt("company_id");
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return objToReturn;
	}
	
}
