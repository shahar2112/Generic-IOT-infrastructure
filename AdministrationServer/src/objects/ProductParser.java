package objects;

import java.io.BufferedReader;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ProductParser implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String type;
	public String model;
	public int company_id;
	public int product_id;
	
	public static ProductParser parse(BufferedReader json)
	{
		Gson gson = new Gson();
		
		return gson.fromJson(json, ProductParser.class);
	}
	
	public static ArrayList<ProductParser> parse(ResultSet rSet)
	{
		ArrayList<ProductParser> productsList = new ArrayList<ProductParser>();
		
		try 
		{	
			while(rSet.next())
			{	
				ProductParser productObject = new ProductParser();
				productObject.company_id = rSet.getInt("company_id");
				productObject.product_id = rSet.getInt("product_id");
				productObject.type = rSet.getString("type");
				productObject.model = rSet.getString("model");
				productsList.add(productObject);
			}
			return productsList;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static ProductParser rsetToObj(ResultSet rSet)
	{
		try 
		{	
			ProductParser productObject = new ProductParser();
			while(rSet.next())
			{	
				productObject.company_id = rSet.getInt("company_id");
				productObject.product_id = rSet.getInt("product_id");
				productObject.type = rSet.getString("type");
				productObject.model = rSet.getString("model");
			}
			return productObject;
		} catch (SQLException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static String listToString(ArrayList<ProductParser> productObj)
	{
		Gson gson = new Gson();
		String products = gson.toJson(productObj);
		return products;
	}

}
