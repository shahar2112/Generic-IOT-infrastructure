package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Crud.CompaniesCrud;
import key.KeyHandler;
import objects.CompanyParser;


/**
 * Servlet implementation class SpecificCompany
 */
@WebServlet("/SpecificCompany")
public class SpecificCompany extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private CompaniesCrud companiesCrud; 
	private static final int ERROR = 401;
	private KeyHandler kHandler;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SpecificCompany() 
    {
    	super();
    	companiesCrud = new CompaniesCrud();
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
	protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException 
	{
		
		String token = request.getHeader("token");

		if(validTokenAndURI(request, token))
		{
			companiesCrud.delete(kHandler.getCompany_Id(token));
		}
		else
		{	
			resp.sendError(ERROR);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException 
	{	
		BufferedReader reader = request.getReader();
		String token = request.getHeader("token");
		
		CompanyParser companyObj = CompanyParser.parse(reader);
		
		if(validTokenAndURI(request, token))
		{
			companiesCrud.update(kHandler.getCompany_Id(token), companyObj);
		}
		else
		{
			resp.sendError(ERROR);
		}
	}
	
	/*
	 * This method checks the validity of the token and if 
	 * the token returns the same company_id from URI
	 */
	private boolean validTokenAndURI(HttpServletRequest request, String token)
	{
		//returns the end of URI ("/id") and removes the "/"
		int companyIDfromURI = Integer.parseInt(request.getPathInfo().replaceAll("/", ""));
		
		if (kHandler.isTokenValid(token))
		{
			if (companyIDfromURI == kHandler.getCompany_Id(token))
			{
				return true;
			}
		}
		return false;
	}

}
