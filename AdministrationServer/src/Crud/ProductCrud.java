package Crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.CompanyParser;
import objects.ProductParser;
import utility.Names;

public class ProductCrud implements CrudRepository<ArrayList<ProductParser>, Integer>{

	@Override
	public void close() throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) 
	{

	}

	@Override
	public Integer create(ArrayList<ProductParser> data) 
	{
		ProductParser product = data.get(0);
		try
		(
			// Getting Connection to driver
			Connection conn = DriverManager.getConnection
					(Names.DBUrl + Names.DB , Names.usrName, Names.usrPswd);
			//create a query
			Statement stmt = conn.createStatement();
		)
		{
			//insert product to the product table
			String insertjson= "INSERT INTO " + Names.productTable + 
								"(type, model, company_id, active)" +
								"VALUES( '" + product.type + "', "+
								"'" + product.model + "', " +
								+ product.company_id + ", TRUE)";
			System.out.println(product.type +" " + product.model +" " + product.company_id);	
			//execute a query
			stmt.executeUpdate(insertjson);
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	//doGet
	@Override
	public ArrayList<ProductParser> read(Integer company_id)
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
			String insertjson = "SELECT * FROM " + Names.productTable +
										" WHERE company_id = '" + company_id +"'";	
			//execute a query and return the relevant product object
			ResultSet rSet = stmt.executeQuery(insertjson);
			return ProductParser.parse(rSet);
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(Integer id, ArrayList<ProductParser> data) {
		// TODO Auto-generated method stub
		
	}

}
