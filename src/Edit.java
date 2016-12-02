

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Edit() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      request.setCharacterEncoding("UTF-8");
	      
	      String st_id = request.getParameter("id");
	      String name = request.getParameter("name");
	      String st_kor = request.getParameter("kor");
	      String st_math = request.getParameter("math");
	      String st_eng = request.getParameter("eng");
	      MemberDTO dto = new MemberDTO();

	      Integer id = Integer.parseInt(st_id);
	      Integer kor = Integer.parseInt(st_kor);
	      Integer math = Integer.parseInt(st_math);
	      Integer eng = Integer.parseInt(st_eng);

	      dto.setId(id);
	      dto.setName(name);
	      dto.setKor(kor);
	      dto.setMath(math);
	      dto.setEng(eng);
	      
	      MemberDAO dao = new MemberDAO();
	      boolean bool = dao.editMember(dto);
 
	      if(bool){ response.sendRedirect("View.html"); }
	}

}
