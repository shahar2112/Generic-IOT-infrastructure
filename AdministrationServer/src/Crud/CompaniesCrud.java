package Crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.CompanyParser;
import utility.Names;

public class CompaniesCrud implements CrudRepository<CompanyParser, Integer>
{

	@Override
	public void close() throws Exception{}

	// The method gets the new entry (new change) and writes it into the DB
	//dopost
	@Override
	public Integer create(CompanyParser data) 
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
			String insertjson= "INSERT INTO " + Names.companiesTable + 
								"(name, email, password, active)" +
								"VALUES( '" + data.name + "', "+
								"'" + data.email + "', " +
								"'" + data.password +"', TRUE)";
			System.out.println(data.name +" " + data.email +" " + data.password);	
			//execute a query
			stmt.executeUpdate(insertjson);
		}  
		catch (SQLException e) {e.printStackTrace();}
		
		return 0;
	}


	@Override
	public void delete(Integer id)
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
			//query to change from active to non-active
			String updatejson = "UPDATE " + Names.companiesTable + " SET"
					+ " active = false WHERE company_id = '" + id + "'";
			//execute query
			stmt.executeUpdate(updatejson);

		} catch (SQLException e) {e.printStackTrace();}
	}
	
	//doget
	@Override
	public CompanyParser read(Integer company_id)
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
			String insertjson = "SELECT * FROM " + Names.companiesTable +
										" WHERE company_id = '" + company_id +"'";	
			//execute a query and return the relevant company object
			ResultSet rSet = stmt.executeQuery(insertjson);
			return CompanyParser.parse(rSet);
		}  
		catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}

	@Override
	public void update(Integer company_id, CompanyParser data)
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
			String updatejson = buildStatement(company_id, data);
			
			stmt.executeUpdate(updatejson);
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private String buildStatement(Integer company_id, CompanyParser data)
	{
		StringBuilder statement = new StringBuilder("UPDATE " + Names.companiesTable + " SET ");
		
		if(data.email != null)
		{
			statement.append("email = '" + data.email + "'");
			if(null != data.name || null != data.password)
			{
				statement.append(", ");
			}
		}
		if(data.name != null)
		{
			statement.append("name = '" + data.name + "'");
			if(null != data.email || null != data.password)
			{
				statement.append(", ");
			}
		}
		if(data.password != null)
		{
			statement.append("password = '" + data.email + "'");
		}
		statement.append(" WHERE Company_ID = "+ company_id.toString() + ";");
		return statement.toString();
	}
}
