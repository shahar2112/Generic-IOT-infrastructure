package Crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objects.ProductParser;
import utility.Names;

public class SpecificProductCrud implements CrudRepository<ProductParser, Integer>
{

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer create(ProductParser data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductParser read(Integer product_id) 
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
			String getProductjson = "SELECT * FROM " + Names.productTable +
										" WHERE product_id = '" + product_id +"'";	
			//execute a query and return the relevant product object
			ResultSet rSet = stmt.executeQuery(getProductjson);
			return ProductParser.rsetToObj(rSet);
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(Integer product_id, ProductParser data) 
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
			String updatejson = buildStatement(product_id, data);
			
			stmt.executeUpdate(updatejson);
		} 
		catch (SQLException e) {e.printStackTrace();}
		
	}

	@Override
	public void delete(Integer product_id) 
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
			String updatejson = "UPDATE " + Names.productTable + " SET"
					+ " active = false WHERE product_id = '" + product_id + "'";
			//execute query
			stmt.executeUpdate(updatejson);

		} catch (SQLException e) {e.printStackTrace();}
	}
	
	private String buildStatement(Integer product_id, ProductParser data)
	{
		StringBuilder statement = new StringBuilder("UPDATE " + Names.productTable + " SET ");
		
		if(data.type != null)
		{
			statement.append("type = '" + data.type + "'");
			if(null != data.model)
			{
				statement.append(", ");
			}
		}
		if(data.model != null)
		{
			statement.append("model = '" + data.model + "'");
		}
		
		statement.append(" WHERE Product_ID = "+ product_id.toString() + ";");
		return statement.toString();
	}

}
