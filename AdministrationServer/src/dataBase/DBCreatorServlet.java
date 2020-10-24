package dataBase;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.Names;

/**
 * Servlet implementation class DbTest
 */
@WebServlet("/DBCreatorServlet")
public class DBCreatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public DBCreatorServlet() throws ClassNotFoundException {
        super();
        
        Class.forName(Names.driverLoad);
        
        SqlDB myDb = new SqlDB();
		myDb.createDb();
		sqlCompaniesTable companiesTable = new sqlCompaniesTable();
		companiesTable.createTable();
		SqlProductTable productTable = new SqlProductTable();
		productTable.createTable();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
