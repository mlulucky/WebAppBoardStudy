package com.acorn.webappboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WebAppBoardConnTest {
    // Junit 자바 단위테스트 - 테스트 원하는 클래스에서 command+n 으로 테스트 선택하면 자동생성
    @Test
    void getConn() throws SQLException, ClassNotFoundException {
        // 🍎getConn() 메서드가 static 메서드라서 WebAppBoardConn 클래스 객체를 생성하지 않고 바로 사용가능
        Connection conn=WebAppBoardConn.getConn(); // 🍎테스트는 메서드에 예외 처리해도 된다. 실패시 종료, 오류가 안뜨면 통과
        // System.out.println(conn); // com.mysql.cj.jdbc.ConnectionImpl@1b266842
        Assertions.assertNotNull(conn); // 성공시 테스트통과했는가 ? 결과 - 테스트 통과 // 예외위임
        /* Assertions 이 제공하는 테스트 함수
         * assertEquals(expected, actual): 예상 값과 실제 값이 같은지 비교합니다.
         * assertNotEquals(expected, actual): 예상 값과 실제 값이 다른지 비교합니다.
         * assertNull(actual): 값이 null인지 검증합니다.
         * assertNotNull(actual): 값이 null이 아닌지 검증합니다.
         * assertTrue(condition): 조건이 참인지 검증합니다.
         * assertFalse(condition): 조건이 거짓인지 검증합니다.
         */


    }
}