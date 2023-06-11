package com.acorn.webappboard.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/model1/boardDetail.do")
public class Model1BoardDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url="jdbc:mysql://localhost:3306/webAppBoard";
        String user="boardServerDev";
        String pw="mysql123";
        String driver="com.mysql.cj.jdbc.Driver";
        resp.setContentType("text/html;charset=UTF-8");
        String html="<h1>게시글 상세리스트</h1>";

        Connection conn=null; // 접속
        PreparedStatement pstmt=null; // Statement : 쿼리실행, PreparedStatement : ? 에 파라미터 주입
        ResultSet rs=null; // table 의 자료구조 // 테이블 실행결과 타입 ResultSet

        // ./boardDetail.do?b_id=rs.getInt("b_id") 동적페이지 url
        // Modeql1BoardsList 자바 파일에서 db board 테이블의 b_id 를 파라미터로 전달했음
        String bId_str=req.getParameter("b_id"); // url 파라미터의 b_id 의 값을 가져온다 // "6" // 겟파라미터 // 파라미터는 문자열이다
        // req.getQueryString(); // 겟쿼리 // ?b_id=6&... 물음표 포함 전체 쿼리스트링 파라미터를 문자열로 가져온다.
        try{
            int bId=Integer.parseInt(bId_str); // NumberFormation 예외처리 // 숫자로 형변환시 오류발생할수도(문자가 포함되어있는 경우 "10b" => 10b).. // 파라미터 문자열 => 정수로 형변환하기. 그래야 키,값으로 사용가능
            Class.forName(driver); // driver 를 가져와서 객체로 만든다.
            conn= DriverManager.getConnection(url,user,pw);

            String sql="SELECT * FROM boards WHERE b_id=?";
            pstmt=conn.prepareStatement(sql); // db에 접속해서 sql 쿼리문 실행
            pstmt.setInt(1,bId); // 첫번째 물음표 에 b_id 에 넣겠다
            rs=pstmt.executeQuery();  // 쿼리문 실행결과
            if(rs.next()){
                html+="<p>b_id : "+rs.getInt("b_id")+"</p>";
                html+="<p>u_id : "+rs.getString("u_id")+"</p>";
                html+="<p>등록일 : "+rs.getString("post_time")+"</p>";
                html+="<p>수정일 : "+rs.getString("update_time")+"</p>";
                html+="<p>status : "+rs.getString("status")+"</p>";
                html+="<p>title: "+rs.getString("title")+"</p>";
                html+="<p>content : "+rs.getString("content")+"</p>";
                html+="<p>view_count : "+rs.getInt("view_count")+"</p>";
                html+="<p><a href='./boardUpdate.do?b_id="+rs.getInt("b_id")+"'>수정폼(==detail)</a></p>";// 수정폼과 디테일은 폼이 똑같다.
            }
        }catch (NumberFormatException e){ // 파라미터를 정수로 변경할 수 없다는 것은 없거나 잘못된 값이 온 것
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().println(html);
    }
}
