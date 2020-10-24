package objects;

import java.io.BufferedReader;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

public class LoginObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String email;
	public String password;
	
	public static LoginObject parse(BufferedReader json)
	{
		Gson gson = new Gson();
		
		return gson.fromJson(json, LoginObject.class);
	}
	
	//returns the company id from the result set
	public static Integer parse(ResultSet rset)
	{
		Integer company_id = 0;
		
		try {
			if(rset.next())
			{
				company_id = rset.getInt("company_id");
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		return company_id;
	}
}
