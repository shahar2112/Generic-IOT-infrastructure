package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import utility.Names;

public class SqlDB implements DbCreatable
{	
	@Override
	public void createDb() 
	{		
		try
		(
				// Getting Connection to driver
				Connection conn = DriverManager.getConnection(Names.DBUrl , Names.usrName, Names.usrPswd);
				//create a query
				Statement stmt = conn.createStatement();
		)
		{
			String createDB = "CREATE DATABASE IF NOT EXISTS " + Names.DB ;
			stmt.executeUpdate(createDB);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
