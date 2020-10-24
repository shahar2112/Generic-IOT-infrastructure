package Crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.LoginObject;
import utility.Names;
import java.sql.Statement;

public class LoginCrud implements CrudRepository<Integer, LoginObject>
{
	@Override
	public Integer read(LoginObject loginDetails) 
	{
		try
		(
			// Getting Connection to driver
			Connection conn = DriverManager.getConnection
					(Names.DBUrl + Names.DB , Names.usrName, Names.usrPswd);
			//create a query
			Statement stmt = conn.createStatement();
		)
		{
			//Receive the company id from query
			String getCompany = "SELECT * FROM " + Names.companiesTable +
					" WHERE email = '" + loginDetails.email + "' and password = '" +
					loginDetails.password + "'";
			
			//execute a query
			ResultSet rSet = stmt.executeQuery(getCompany);
			//return the relevant company id
			return LoginObject.parse(rSet);
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public void close() throws Exception {}

	@Override
	public LoginObject create(Integer data) {return null;}

	@Override
	public void update(LoginObject id, Integer data) {}

	@Override
	public void delete(LoginObject id) {}

}
