package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import utility.Names;

public class sqlCompaniesTable implements TableCreatable
{
	@Override
	public void createTable() 
	{
		try
		(
			// Getting Connection to driver
			Connection conn = DriverManager.getConnection(Names.DBUrl , Names.usrName, Names.usrPswd);
			//create a query
			Statement stmt = conn.createStatement();
		)
		{
			
		String useDB = "USE " + Names.DB;
		
		String createCompaniesTable = "CREATE TABLE IF NOT EXISTS " + Names.companiesTable +
				"(company_id INT AUTO_INCREMENT PRIMARY KEY, " +
				"name VARCHAR(50) NOT NULL, " +
				"email VARCHAR(50) NOT NULL UNIQUE, " +
				"password VARCHAR(20) NOT NULL, " +
				"active BOOLEAN NOT NULL);" ;
		//execute a query
		//create a query
		stmt.executeUpdate(useDB);
		stmt.executeUpdate(createCompaniesTable);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

}
