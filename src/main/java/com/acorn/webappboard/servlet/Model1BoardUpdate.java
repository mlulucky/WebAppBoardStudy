package com.acorn.webappboard.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/model1/boardUpdate.do")
public class Model1BoardUpdate extends HttpServlet {
    // doPost 에서도 사용하기 위해 전역으로 뺌
    String url="jdbc:mysql://localhost:3306/webAppBoard";
    String user="boardServerDev";
    String pw="mysql123";
    String driver="com.mysql.cj.jdbc.Driver"; // jdbc 가
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8"); 
        // 서버가 클라이언트에 응답할 컨텐츠의 타입 - html 문서, UTF-8 인코딩 => 브라우저(클라이언트)가 화면으로 출력
        String html="<h1>게시글 상세수정폼</h1>";

        Connection conn=null; // 접속
        PreparedStatement pstmt=null; // Statement : 쿼리실행, PreparedStatement : url 에 파라미터 주입
        ResultSet rs=null; // table 의 자료구조 // 테이블 실행결과 타입 ResultSet

        // ./boardDetail.do?b_id=rs.getInt("b_id") 동적페이지 url
        String bId_str=req.getParameter("b_id"); // "6" // 겟파라미터 // 파라미터는 문자열이다
        // req.getQueryString(); // 겟쿼리 // ?b_id=6&... 물음표 포함 전체 쿼리스트링 파라미터를 문자열로 가져온다.
        try{
            int bId=Integer.parseInt(bId_str); // 파라미터 정수로 형변환시 NumberFormation 예외처리 // 숫자로 형변환시 오류발생할수도(문자가 포함되어있는 경우 "10b" => 10b).. // 파라미터 문자열 => 정수로 형변환하기. 그래야 키,값으로 사용가능
            Class.forName(driver); // driver 를 가져와서 객체로 만든다.
            conn= DriverManager.getConnection(url,user,pw);

            String sql="SELECT * FROM boards WHERE b_id=?";
            pstmt=conn.prepareStatement(sql); // url 쿼리스트링으로 sql 을 대입 // db에 접속해서 sql 쿼리문 실행
            pstmt.setInt(1,bId); // 첫번째 물음표 에 b_id 에 넣겠다
            rs=pstmt.executeQuery();  // 쿼리문 실행결과

            // 수정양식 - 폼태그 - 조건은 b_id, 수정내용 status, title, content
            html+="<form method='POST' action='./boardUpdate.do'>"; // POST 로 처리해줘~
            if(rs.next()){
                html+="<p>b_id : "+rs.getInt("b_id")+"</p>"; // 테이블 db 의 b_id 컬럼의 값을 대입
                html+="<p>u_id : "+rs.getString("u_id")+"</p>";
                html+="<p>등록일 : "+rs.getString("post_time")+"</p>";
                html+="<p>수정일 : "+rs.getString("update_time")+"</p>";
                html+="<p>status : "+rs.getString("status")+"";
                html+="<select name='status'><option>PUBLIC</option><option>PRIVATE</option><option>REPORT</option><option>BLOCK</option></select></p>";
                // option 태그안에 입력시, 자동으로 value 가 된다
                html+="<p>title: <label> title : <input name='title' value='"+rs.getString("title")+"'> </label> </p>"; // title 은 기본값이 text. type 안써도 된다
                html+="<p>content : <textarea name='content'>"+rs.getString("content")+"</textarea></p>";
                html+="<p>view_count : "+rs.getInt("view_count")+"</p>";
                html+="<p><a href='./boardDelete.do?b_id="+rs.getInt("b_id")+"'>삭제</a></p>";// 수정폼과 디테일은 폼이 똑같다.
                html+="<p><button type='reset'>초기화</button> <button>수정</button></p>";
                html+="<input type='hidden' name='b_id' value='"+rs.getInt("b_id")+"'>"; // 숨겨놓은 // 수정업데이트시 전달예정
            }
            html+="</form>";
        }catch (NumberFormatException e){ // 파라미터를 정수로 변경할 수 없다는 것은 없거나 잘못된 값이 온 것
            e.printStackTrace();
            resp.sendError(400,"잘못된 요청입니다.");
        }catch (Exception e) { // 모든 오류 예외처리 // 모든 오류예외처리는 항상 마지막에 추가하력
            e.printStackTrace();
        }
        resp.getWriter().println(html);
    }

    // 405 에러 : 해당 동적페이지의 요청 메소드가 잘못됨 - GET,POST 방식
    // 수정 처리 - POST 방식
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // req(==request 요청정보) 클라이언트가 서버에 보내는 요청
        // 브라우저(클라이언트)가 서버에 url 을 요청할때 url 인코딩.
        // url 인코딩 -> UTF-8 인코딩으로 바꿔야 자바에서 처리할 수 있다.
        String status=req.getParameter("status");
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String bId_str=req.getParameter("b_id");  // 숨겨놓은 b_id
        String sql="UPDATE boards SET status=?,title=?,content=? WHERE b_id=?";
        Connection conn=null;
        PreparedStatement pstmt=null;
        int update=0; // dml
        try{
            int bId=Integer.parseInt(bId_str); // 파라미터 정수로 형변환
            Class.forName(driver); // 새로운 접속
            conn=DriverManager.getConnection(url,user,pw); // 새로운 쿼리실행
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,status);
            pstmt.setString(2,title); // title 이 크기가 있어서 너무 길면 오류가 뜰수있다.
            pstmt.setString(3,content);
            pstmt.setInt(4,bId);
            update=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        // 수정 성공시 detail 페이지로 실패시 updateForm
        if(update>0){
            resp.sendRedirect(req.getContextPath()+"/model1/boardDetail.do?b_id="+bId_str);
        }else{
            resp.sendRedirect(req.getContextPath()+"/model1/boardUpdate.do?b_id="+bId_str);
        }

    }
}
