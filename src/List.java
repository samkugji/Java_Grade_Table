import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 * Servlet implementation class selectProcessJson
 */
@WebServlet("/List")
public class List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public List() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		MemberDAO dao= new MemberDAO();
		ArrayList<MemberDTO> vec = new ArrayList<MemberDTO>();
		vec = dao.selectMember();
		
		String json =new Gson().toJson(vec)	;
		
		request.setAttribute("data", json);
		response.setContentType("application/json;charset=UTF-8");
		
		//TODO:아래 문법?
		response.getWriter().write(json);
	}
}