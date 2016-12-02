import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Add")
public class Add extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Add() {
        super();
    }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      request.setCharacterEncoding("UTF-8");
      String name = request.getParameter("addname");
      int kor = Integer.parseInt(request.getParameter("addkor"));
      int math = Integer.parseInt(request.getParameter("addmath"));
      int eng = Integer.parseInt(request.getParameter("addeng"));

      MemberDTO dto = new MemberDTO();
      dto.setName(name);
      dto.setKor(kor);
      dto.setMath(math);
      dto.setEng(eng);

      MemberDAO dao = new MemberDAO();
      boolean bool = dao.addMember(dto);

      if(bool){ response.sendRedirect("View.html"); }
   }
}
