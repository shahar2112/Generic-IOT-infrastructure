package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Crud.CompaniesCrud;
import Crud.ProductCrud;
import key.KeyHandler;
import objects.CompanyParser;
import objects.CompanyProductsParser;
import objects.ProductParser;
import utility.Names;

/**
 * Servlet implementation class Company
 */
@WebServlet("/companies")
public class Company extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private CompaniesCrud companiesCrud;
	private ProductCrud productCrud;
	private static final int ERROR = 401;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Company() 
    {
        super();
        try
        {
			Class.forName(Names.driverLoad);
		} catch (ClassNotFoundException e) {e.printStackTrace();}
        
        companiesCrud = new CompaniesCrud();
        productCrud = new ProductCrud();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		KeyHandler kHandler = new KeyHandler();
		String token = request.getHeader("token");
		
		if(kHandler.isTokenValid(token))
		{
			printDetails(kHandler, token, response);
		}
		else
		{
			response.sendError(ERROR);		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BufferedReader reader = request.getReader();
		
		CompanyParser companyParser = CompanyParser.parse(reader);
		
		companiesCrud.create(companyParser);
	}
	
	private void printDetails(KeyHandler kHandler, String token, HttpServletResponse response) throws IOException
	{
		int company_id = kHandler.getCompany_Id(token);
		CompanyParser companyObj = companiesCrud.read(company_id);
		
		ArrayList<ProductParser> productsObj = productCrud.read(company_id);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//prints the companies details and products
		String jsonString = CompanyProductsParser.parse(productsObj, companyObj);
		out.print(jsonString);
		
		//Committing the response with flush
		out.flush();
	}

}
