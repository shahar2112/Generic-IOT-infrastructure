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
import Crud.LoginCrud;
import Crud.ProductCrud;
import key.KeyHandler;
import objects.CompanyParser;
import objects.CompanyProductsParser;
import objects.LoginObject;
import objects.ProductParser;
import utility.Names;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private CompanyParser companyObj;
    private ArrayList<ProductParser> productsObj;
    private CompaniesCrud companyCrud;
    private ProductCrud productCrud;
    private LoginCrud loginCrud;
    private KeyHandler kHandler;
    private static final int ERROR = 401;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login()
    {
        super();
        try 
        {
			Class.forName(Names.driverLoad);
		} catch (ClassNotFoundException e) {e.printStackTrace();}
        
        companyCrud = new CompaniesCrud();
        productCrud = new ProductCrud();
        loginCrud = new LoginCrud();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//read the request and return a login object with request details
		BufferedReader reader = request.getReader();
		LoginObject loginObject = LoginObject.parse(reader);
		//get company id from the query
		int company_id = loginCrud.read(loginObject);
		
		if(checkCompanyId(company_id))
		{
			printDetails(company_id, response);
		}
		else
		{
			response.sendError(ERROR);
		}
	}
	
	/**********Utility methods************/
	
	private void printDetails(int company_id, HttpServletResponse response)
	{
		kHandler = new KeyHandler();
		kHandler.createToken(company_id);
		String token = kHandler.createToken(company_id);
		
		//get the company and product details
		companyObj = companyCrud.read(company_id);
		productsObj = productCrud.read(company_id);
		
		PrintWriter out = getResponse(response, token);
	
		//prints the companies details and products
		String jsonString = CompanyProductsParser.parse(productsObj, companyObj);
		out.print(jsonString);
	
		//Committing the response with flush
		out.flush();
	}
	
	private PrintWriter getResponse(HttpServletResponse response, String token)
	{
		try 
		{
			PrintWriter out = response.getWriter();
		
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("token", token);
			return out;
		} catch (IOException e){e.printStackTrace();}
		return null;
	}
	
	private boolean checkCompanyId(int id)
	{
		return id == 0 ? false : true;
	}

}
