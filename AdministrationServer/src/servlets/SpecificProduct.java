package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Crud.SpecificProductCrud;
import key.KeyHandler;
import objects.CompanyParser;
import objects.ProductParser;
import utility.Names;

/**
 * Servlet implementation class SpecificProduct
 */
@WebServlet("/SpecificProduct")
public class SpecificProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductParser productObj;
	private SpecificProductCrud specificProductCrud; 
	private KeyHandler kHandler;
    private static final int ERROR = 401;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SpecificProduct() 
    {
    	try
        {
			Class.forName(Names.driverLoad);
		} catch (ClassNotFoundException e) {e.printStackTrace();}
    	productObj = new ProductParser();
        specificProductCrud = new SpecificProductCrud();
        kHandler = new KeyHandler();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		String token = request.getHeader("token");
		
		//returns the end of URI ("/id") and removes the "/"
		int productIDfromURI = Integer.parseInt(request.getPathInfo().replaceAll("/", ""));
		
		productObj = specificProductCrud.read(productIDfromURI);
		
		int companyIDfromQuery = productObj.company_id;
				
		if(validTokenAndURI(token, companyIDfromQuery))
		{
			specificProductCrud.delete(kHandler.getCompany_Id(token));
		}
		else
		{	
			resp.sendError(ERROR);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		String token = request.getHeader("token");
		
		//returns the end of URI ("/id") and removes the "/"
		int productIDfromURI = Integer.parseInt(request.getPathInfo().replaceAll("/", ""));
		
		//a product object that contains the specific product information
		productObj = specificProductCrud.read(productIDfromURI);
		
		int companyIDfromQuery = productObj.company_id;
		
		if(validTokenAndURI(token, companyIDfromQuery))
		{
			BufferedReader reader = request.getReader();
			
			//a product object that contains only the updated information
			ProductParser productObjfromRequest = ProductParser.parse(reader);
			
			specificProductCrud.update(kHandler.getCompany_Id(token), productObjfromRequest);
		}
		else
		{
			resp.sendError(ERROR);
		}
	}
	
	/* checks if the token is correct and compares the id
	 *  for the specific product from query to the company_id from token
	 */
	private boolean validTokenAndURI(String token, int company_id)
	{	
		if (kHandler.isTokenValid(token))
		{
			if (company_id == kHandler.getCompany_Id(token))
			{
				return true;
			}
		}
		return false;
	}
	

}
