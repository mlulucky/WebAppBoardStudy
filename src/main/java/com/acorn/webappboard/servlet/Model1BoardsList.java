package com.acorn.webappboard.servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*; // 자바 mysql 패키지

// 클라이언트에서 요청이 오면 톰캣에서 web.xml (웹앱배포서술자)설정에 있는 동적파일(HttpServlet 타입 클래스만)을 실행해서 결과를 반환.응답
// jdk 자바개발도구 설치하기! (jre 가 포함되어있다)
@WebServlet("/model1/boardList.do") // url 이 이렇게 요청이 오면~! // 폴더가 있는것처럼 url 지정을 하는 것. 이와같이 url 을 입력하면 동적리소스 파일반환 // @WebServlet() : 컴파일시 web.xml 설정에 해당 동적리소스의 주소를 등록
public class Model1BoardsList extends HttpServlet { // 동적파일 Modelq1BoardsList HttpServlet 타입 클래스만 을 찾아서 결과를 반환하겠다~ 라는 뜻
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // url 로 오는 파라미터로 동적리소스 요청 - GET 방식 - doGet
        String url="jdbc:mysql://localhost:3306/webAppBoard"; // 접속 url (만든 웹앱모듈 webAppBoard)
        String user="boardServerDev"; // db 접속계정 아이디
        String pw="mysql123"; // db 접속 비밀번호
        String driver="com.mysql.cj.jdbc.Driver"; // jdbc 가 db 에 접속할 때 필요한 driver - 다운로드 받거나 maven(pom.xml)에서 의존성주입 => mysql Connector 추가
        resp.setContentType("text/html;charset=UTF-8"); // 한글깨짐 방지것
        String html = "<h1>model1+jdbc 게시글 리스트</h1>";
        html+="<p>model1 : Model,Controller,View 가 한페이지에 있는 것</p>"; // 2010 년 전까지
        html+="<p>JDBC : 자바 어플(톰캣의 웹앱)에서 db 를 접속하는 것(모듈과 패키지 java.sql.*)</p>";

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null; // 셀렉트문 결과

        try{
            // mysql 접속설정
            Class.forName(driver); // 동적로딩에 사용할 클래스를 불러오는 import 역할 // == import com.mysql.cj.jdbc.Driver; // mysql-connector-j (메이븐에 의존성 주입한 - mysql 접속 driver)
            conn=DriverManager.getConnection(url,user,pw); // 드라이버 동적로딩 // 동적로딩에 사용한 드라이버를 불러와야 한다 => Class.forName(driver)
            // System.out.println(conn); // 톰캣 실행시 서비스에서 출력이 나오면 접속이 되는 것! com.mysql.cj.jdbc.ConnectionImpl@6c821bfd // @ 접속 객체의 주소를 해쉬코드로 저장한것.

            // model 의 역할
            String sql="SELECT * FROM boards";
            pstmt=conn.prepareStatement(sql);  // sql 인식
            rs=pstmt.executeQuery(); // sql 쿼리문 실행. 결과

            // view 역할 - 이렇게 html 을 만들면 퍼블리셔가 도망간다
            html+="<table>";
            html+="<tr><th>no</th><th>title</th><th>u_id</th><th>등록일</th><th>상세</th></tr>";
            while(rs.next()){ // rs 는 테이블 row, 튜플, 1개의 객체
                html+="<tr>"; // 백틱은 자바스크립트 문법. 자바에서는 사용불가
                html+="<td>"+rs.getInt("b_id")+"</td>";
                html+="<td>"+rs.getString("title")+"</td>";
                html+="<td>"+rs.getString("u_id")+"</td>";
                html+="<td>"+rs.getString("post_time")+"</td>";
                html+="<td><a href='./boardDetail.do?b_id="+rs.getInt("b_id")+"'>상세</a></td>";
                // => a 태그의 링크로 url 에 ?b_id=value 파라미터 주입.(board 테이블의 b_id 필드(칼럼)의 값)
                html+="</tr>";
            }
            html+="</table>";

        }catch(Exception e){
            e.printStackTrace();
        }
        html+="<p><button type=button class=btn btn-primary><a href='./boardInsert.do'>등록</button></p>";
        // controller 응답 역할
        resp.getWriter().println(html); // **응답 화면 출력

    }
}
