package com.acorn.webappboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// mysql db 접속객체 - 1번만 만들어서 사용 => db 정보를 매번 쓸 필요가 없다 매번
public class WebAppBoardConn {
    // 🍎객체생성 제한위해 private(캡슐화) 설정, 전역에서 쓸수있도록 static 필드 설정 => 검사 후 public 으로 객체 전달
    // => 싱글톤 패턴 (대체로 FINAL 로 하지 않는다 => 예) 커넥션 객체를 처음에 null 로 하고 검사후에 객체를 대입해 전달할거라서 값이 바뀔것이므로!(상수는 값을 못바꾼다))
    // 🍎static 키워드 - 클래스 객체 생성없이 변수 또는 메서드에 접근해 사용가능
    private static Connection conn=null; // 🍎db 커넥션(접속)객체 // 모든 db 전체 영역에서 사용 => static
    // => private 이유 : 처음엔 null 이라서 검사를 해서 줄것이다. db 접속을 달라고 요청하는 곳에서 따로 만들것
    // 🍎접속정보 - 변경되지 않는 정보 - final(==상수) // JVM 이 실행되는 도중에 값을 바꿀수 있는게 변수 / 없는게 상수
    private static final String USER="boardServerDev"; // mysql db 계정
    private static final String PW="mysql123"; // final 상수인 비밀번호를 바꾸려먼 JVM 실행을 끄고, 비밀번호를 바꾼 후 다시 실행하면 된다.
    private static final String URL="jdbc:mysql://localhost:3306/WebAppBoard"; // jdbc 통신 mysql 접속. 내컴퓨터 localhost . 모듈웹앱폴더
    private static final String DRIVER="com.mysql.cj.jdbc.Driver"; // mysql 구동 드라이버

    // public 으로 어디로든 전달가능 // 커넥션 객체
    public static Connection getConn() throws SQLException, ClassNotFoundException { // 메서드 예외처리
        // static 이어야지 객체생성없이 사용 가능
        // 커넥션 전달 시 db 접속 되있는지 검사! => public
        if(conn==null || conn.isClosed()){ // conn(db접속) 이 null 또는 closed(db 접속이 끊겼을때) db 에 접속 // 🍎isColsed 는 예외 위임을 해야한다
            Class.forName(DRIVER); // driver 가 없을 수 있어서 예외처리
            conn= DriverManager.getConnection(URL,USER,PW); // db 접속
        }
        return conn; // 무조건 db 접속해서 전달! // 맨 위에 null 로 선언하면 if문 조건문에 해당되서, db 접속객체 대입
    }//🍎디자인패턴 : 싱글톤 패턴 (객체를 한번만 만들어서 사용하기 위한 디자인 패턴)
    // => 요청이 들어올때마다 객체를 만드는게 아니라, 접속객체를 한번만 만들어서 계속 사용(재사용)

    // 객체 - new Object() - 객체(정보)를 사용
    // => 계속 만들 수 가 있다. 중구난방.. 이를 막기위해서 싱글톤 패턴
    // 모든 통신환경에서 객체지향문법(객체를 만들어서 객체를 전달)이 취약
    // new Object == Instance(객체)
    // 모든 통신은 요청이 들어오면 다 처리하도록 전달하기위해 만들어져있는데..
    // 동시접속시, 객체를 10만개, 100만개도 만드는데..

    // 싱글톤이 좋은이유
    // 예) db 접속 객체 하나 만들어 null 로 초기화 => conn=null
    // db 접속 요청이 들어오면 null 인지 검사를 통해서 전달
    // 요청이 와도 객체가 하나만 만들어지는 것
    
}
