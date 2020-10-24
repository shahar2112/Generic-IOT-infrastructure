package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import utility.Names;

public class SqlProductTable implements TableCreatable
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
		
		String createProductTable = "CREATE TABLE IF NOT EXISTS " + Names.productTable +
				"(product_id INT AUTO_INCREMENT PRIMARY KEY, " +
				"type VARCHAR(50) NOT NULL, " +
				"model VARCHAR(50), " +
				"company_id INT, " +
				"FOREIGN KEY(company_id) REFERENCES "+ 
				Names.companiesTable + "(company_id)," +
				" active BOOLEAN NOT NULL)" ;
		System.out.println("Database and table created successfully...");
		//execute a query
		//create a query
		stmt.executeUpdate(useDB);
		stmt.executeUpdate(createProductTable);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

}
