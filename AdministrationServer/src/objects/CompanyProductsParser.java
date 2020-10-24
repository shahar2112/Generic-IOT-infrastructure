package objects;

import java.util.ArrayList;

import com.google.gson.Gson;

/*This class concatinates the companie details and her products */
public class CompanyProductsParser 
{
	CompanyParser companyObj;
	ArrayList<ProductParser> productsObj;
	
	public CompanyProductsParser(ArrayList<ProductParser> productsObj, CompanyParser companyObj)
	{
		this.productsObj = productsObj;
		this.companyObj = companyObj;
	}
	
	public static String parse(ArrayList<ProductParser> productsObj, CompanyParser companyObj)
	{
		CompanyProductsParser companyProduct = new CompanyProductsParser(productsObj, companyObj);
		Gson gson = new Gson();
		
		return gson.toJson(companyProduct);
	}
	

}
