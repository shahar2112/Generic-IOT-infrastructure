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
import objects.LoginObject;
import objects.ProductParser;
import utility.Names;

/**
 * Servlet implementation class Product
 */
@WebServlet("/products")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<ProductParser> productList;
	private ProductCrud productsCrud;
    private static final int ERROR = 401;
    private KeyHandler kHandler;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Product() 
    {
        super();
        try 
        {
			Class.forName(Names.driverLoad);
		} catch (ClassNotFoundException e) {e.printStackTrace();}
        
        productList = new ArrayList<ProductParser>();
		productsCrud = new ProductCrud();
		kHandler = new KeyHandler();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		String token = request.getHeader("token");
		
		if(kHandler.isTokenValid(token))
		{
			int company_id = kHandler.getCompany_Id(token);
			ArrayList<ProductParser> productsObj = productsCrud.read(company_id);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			//prints the companies details and products
			String jsonString = ProductParser.listToString(productsObj);
			out.print(jsonString);
			
			//Committing the response with flush
			out.flush();
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

		doGet(request, response);
		
		BufferedReader reader = request.getReader();
		//parse the request and get a product object
		ProductParser productObject = ProductParser.parse(reader);
			
		String token = request.getHeader("token");
		
		if(kHandler.isTokenValid(token))
		{			
			productObject.company_id = kHandler.getCompany_Id(token);
			productList.add(productObject);
			productsCrud.create(productList);
		}
		else
		{
			response.sendError(ERROR);
		}

	}

}
