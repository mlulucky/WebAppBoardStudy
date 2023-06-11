package com.acorn.webappboard.controller;

import com.acorn.webappboard.dto.UsersDto;
import com.acorn.webappboard.service.UsersService;
import com.acorn.webappboard.service.UsersServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/users/login.do") // 로그인 동적 페이지 주소 // users 폴더 안에 login.do 파일 // login.jsp 파일의 form 태그에 연동시켜놓음
public class UsersLoginController extends HttpServlet {
    // 🍎컨트롤러와 라우터와 같다! 요청과 응답을 처리.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 인터프린트(js,python,ruby)(컴파일이 안되고 바로 문자열그대로 실행되는 언어 - 스크립트 언어로도 얘기(스크립트언어가 인터프린트의 속성를 가지고있다))(컴파일 x-> 배포, 빠르다. 오류가 발생) <-> 컴파일(java,c,c+...)(컴파일 후 배포. 느리지만 오류가 없다.) (컴파일되는 언어. 컴퓨터가 읽을수 있도록 한번 변형되는 언어)
        // JSP : 퍼그처럼 html 을 렌더링 하는 템플릿 엔진(문법은 자바인데 인터프린트언어로 취급)
        // html(유연성이있다) : xhtml(html4 버전 - xml 규칙을 따른다)
        // <닫는 태그가 없는 경우 - <button/> 이렇게 표시하는데, xhtml 은 이렇게 표시 안한다. 반드시 닫는태그씀
        // JSP 보안에 취약해서 개발에 사용하진 않지만 공부로 수업한다.
        req.getRequestDispatcher("/templates/users/login.jsp").forward(req,resp); // 위치 🔥 여기 다시 강의보기
        // 나의 요청과 응답을 login.jsp 파일에 위임(forward)하겠다. 너(forward)가 화면을 렌더링하고 끝내 ~

        // express 퍼그는 라우터가 view 를 렌더링(html을 반환)하고 끝낸다. (대세!!) // view 는 서버사이드(서버에서 렌더링)이다~!
        // 톰캣 jsp 는 view 를 렌더링하면서 요청과 응답을 JSP 페이지 에서 마무리한다. (옛날방식! 보안에 취약)
        // jsp 는 동적페이지만 인터프린트 언어로 정적리소스 페이지와 동일한 위치에 존재한다.(public 폴더에 존재)
        // 🍎이제 jsp 사용 안하는 이유들
        // =>문제점) 클라이언트는 정적리소스 페이지의 위치에 파일을 업로드하거나 다운로드 할수있다.(보안에 취약)
        // jsp는 요청과 응답을 처리할 수 있는 동적페이지로 서버에 대한 주요 정보가 존재할 수 있다.
        // 해커가 jsp 페이지를 서버에 업로드하고 호출하면서 실행 (db 에 접속해서 중요한 정보를 취득 가능 및 서버 다운 가능)
        // (=> 동적페이지는 db 에 접속할수있다 => jsp 페이지에서 db 에 접속할수있다 => 따라서 요새는 view 를 렌더링 하는 것만으로 끝내는 퍼그방식이 대세)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // ?🔥
        String uId=req.getParameter("u_id"); // 문자열 // 파라미터의
        String pw=req.getParameter("pw");
        UsersDto loginUser=null; // 유저가 없거나 실수할수 있으므로 null 로 기본값 처리
        try {
            UsersService usersService=new UsersServiceImp();
            loginUser=usersService.login(uId,pw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        resp.getWriter().println(loginUser);
        // 로그인 성공 == 디비에 uid, pw 가 같은 사람이 있냐
        // http Session : 인증같은 서비스를 위해 서버에 저장하는 객체(쿠키로 클라이언트 아이디(JSESSIONID - 웹페이지 검사-어플리케이션 쿠키에서 확인가능 - 세션이 만들어준 아이디)와 세션의 만료시간(기본이 30분)을 책정한다)
        // 요청이 들어온 클라이언트와 대응되는 세션(서버에 저장하는 객체)이 있다면 요청정보에 담아준다. (없으면 새로 만들어 담아준다)
        HttpSession session=req.getSession(); // session 의 타입은 Map 과 동일 // 요청할때마다 요청정보를 저장한다.
        session.setAttribute("loginUser",loginUser); //  세션객체에 저장. 서버에 저장  // Map 타입.setAttribute(키값, value) // setAttribute 로 해당 key 에 값을 저장
        resp.sendRedirect(req.getContextPath()); // ???🔥12시40분쯤..
        // resp.sendRedirect("/"); // index 페이지 // 톰캣은 웹앱 index.jps 를 root 경로로 지정해 놓았다.
        // 서버가 새로고침되면 세션이 삭제된다.
    }
}
