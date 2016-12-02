import java.sql.*;
import java.util.*;
import javax.servlet.*;
/*
 * DAO(Data Access Object)데이터 접근 제어로
 * DB를 사용하여 데이터를 조회하고 insert, update 등의
 * 조작하는 기능을 분리하여 클래스로 만든 것이다.</P>
 */
public class MemberDAO {
      private Connection conn;
      public MemberDAO() throws ServletException{
         try{
        	Class.forName("com.mysql.jdbc.Driver");
         }catch(ClassNotFoundException ex) {
            throw new ServletException("드라이버 오류!");
         }
         String url="jdbc:mysql://localhost:3306/my_data";
         String id="dev";
         String pw="dev";
         try{
            conn=DriverManager.getConnection(url, id, pw);
         }catch(SQLException ex){
               throw new ServletException("접속오류!");
         }
      }

      public boolean addMember(MemberDTO dto) throws ServletException{
         String query="insert into grade_table values(NULL,?,?,?,?,?,?)";

         try{
            PreparedStatement pstmt=conn.prepareStatement(query);
            pstmt.setString(1, dto.getName());
            pstmt.setInt(2, dto.getKor());
            pstmt.setInt(3, dto.getMath());
            pstmt.setInt(4, dto.getEng());
            pstmt.setInt(5, dto.getSum());
            pstmt.setDouble(6, dto.getAvg());
            pstmt.executeUpdate();
            pstmt.close();
         }catch(SQLException ex) {
            throw new ServletException("insert FAIL");
         }finally{
            this.close();
         }
         return true;
      }

      
      public ArrayList<MemberDTO> selectMember() throws ServletException{
	      ArrayList<MemberDTO> vec = new ArrayList<MemberDTO>();
	      String query="select * from grade_table";

	      try {
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery(query);
	         while(rs.next()){
	           MemberDTO mt = new MemberDTO();
               mt.setId(rs.getInt("id"));
               mt.setName(rs.getString("name"));
               mt.setKor(rs.getInt("kor"));
               mt.setMath(rs.getInt("math"));
               mt.setEng(rs.getInt("eng"));
               mt.setSum(rs.getInt("sum"));
               mt.setAvg(rs.getDouble("avg"));

               vec.add(mt);
               }

	         rs.close();
	         pstmt.close();

	      }catch(SQLException ex){
	         throw new ServletException("select FAIL");
	      } finally{
	         this.close();
	      }
//          System.out.println("=================");
//          System.out.println(vec.get(2).getKor());
//          System.out.println(vec.get(2).getSum());
//          System.out.println(vec.get(2).getAvg());
//          System.out.println("=================");
	      return vec;
	  }

      public boolean delMember(String id) throws ServletException{
         String query="DELETE from grade_table WHERE id=" + id;

         try{
            PreparedStatement pstmt=conn.prepareStatement(query);
            pstmt.executeUpdate();
            pstmt.close();
         }catch(SQLException ex) {
            throw new ServletException("DELETE FAIL");
         }finally{
        	resetAutoIncrement();
            this.close();
         }
         return true;
      }
      
      
      // DELETE 시 Reset AUTO_INCREMENT(ALTER TABLE grade_table AUTO_INCREMENT=1)
      // TODO:중간 ID가 사라질 경우 처리 
      public boolean resetAutoIncrement() throws ServletException{
    	  String query = "ALTER TABLE grade_table AUTO_INCREMENT=1";

          try{
             PreparedStatement pstmt=conn.prepareStatement(query);
             pstmt.executeUpdate();
             pstmt.close();
          }catch(SQLException ex) {
             throw new ServletException("resetAutoIncrement FAIL");
          }finally{
             this.close();
          }
          return true;
       }
      
      public boolean editMember(MemberDTO dto) throws ServletException{
          String query="UPDATE grade_table SET name=?, kor=?, math=?, eng=?, sum=?, avg=? WHERE id=?";

		   try{
	            PreparedStatement pstmt=conn.prepareStatement(query);
	            pstmt.setString(1, dto.getName());
	            pstmt.setInt(2, dto.getKor());
	            pstmt.setInt(3, dto.getMath());
	            pstmt.setInt(4, dto.getEng());
	            pstmt.setInt(5, dto.getSum());
	            pstmt.setDouble(6, dto.getAvg());
	            pstmt.setInt(7, dto.getId());
	            pstmt.executeUpdate();
	            pstmt.close();
	         }catch(SQLException ex) {
	            throw new ServletException("edit FAIL");
	         }finally{
	            this.close();
	         }
	         return true;
      }

      private void close(){
    	  try{
    		  if(conn != null && !conn.isClosed()) conn.close();
    	  }catch(SQLException ex){conn=null;}
      }
}
