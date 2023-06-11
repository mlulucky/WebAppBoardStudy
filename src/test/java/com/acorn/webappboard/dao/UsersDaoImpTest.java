package com.acorn.webappboard.dao;

import com.acorn.webappboard.WebAppBoardConn;
import com.acorn.webappboard.dto.UsersDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoImpTest {
    // TDD (test driven developement) : 테스트 주도개발
    // junit(자바) 단위 테스트로 TDD 를 했어요, Jest(js) 단위 테스트로 TDD 를 했어요

    private static UsersDao usersDao;
    @BeforeAll // test 시작하기 전에 먼저 실행! // 재사용하는 것을 초기셋팅 // db 접속 먼저 실행
    static void setConn() throws Exception {
        // BeforeAll 은 무조건 static(먼저실행) 선언
        Connection conn=WebAppBoardConn.getConn(); // WebAppBoardConn db 접속객체
        usersDao=new UsersDaoImp(conn); // db 접속 + 객체 생성 // UsersDaoImp 클래스 파일에서 클래스의 생성자로 db 객체를 매개변수로 받아 db 접속을 하게끔 만듬 // UsersDaoImp 클래스(타입)가 usersDao 인터페이스를 구현
        // userDao 필드명 앞에 UsersDaoImpTest 클래스명 안붙여도 자동으로 붙여진다
    }

    @Test
    void findAll() throws Exception {
        List<UsersDto> users=usersDao.findAll();
        System.out.println(users);
    }


    @Test
    void findByUIdAndPw() throws Exception {
        UsersDto user=usersDao.findByUIdAndPw("user02","mysql1234");
        System.out.println(user);
        // UsersDto(userId=user02, pw=1234, name=이영희, phone=01087654321, imgPath=null, email=user02@gmail.com, postTime=2023-03-13, birth=1992-03-02, gender=FEMALE, address=서울특별시, detailAddress=관악구, permission=USER)
        Assertions.assertNotNull(user); // null 인지 검증
    }

    @Test
    void findByUId() throws Exception {
        UsersDto user=usersDao.findByUId("user06");
        System.out.println(user);
        // UsersDto(userId=user06, pw=mysql1234, name=이미라, phone=01077778888, imgPath=null, email=user06@gmail.com, postTime=2023-03-13, birth=2002-09-06, gender=FEMALE, address=서울특별시, detailAddress=용산구, permission=USER)
        Assertions.assertNotNull(user);
    }

    @Test
    void updateByPk() throws Exception {
        UsersDto user=new UsersDto();
        user.setUId("user06");
        user.setName("수정_이미라");
        user.setPw("4321");
        user.setPhone("002155556677");
        user.setImgPath("./img/users/user06.jpeg");
        user.setEmail("수정_user06@test.com");
        user.setGender("NONE");
        user.setAddress("경기도");
        user.setDetailAddress("이천");
        int update=usersDao.updateByPk(user);
        System.out.println(update); // 성공시 1
        UsersDto user06=usersDao.findByUId("user06");
        // UsersDto(uId=user06, pw=4321, name=수정_이미라, phone=002155556677, imgPath=./img/users/user06.jpeg, email=수정_user06@test.com, postTime=2023-03-13, birth=2002-09-06, gender=NONE, address=경기도, detailAddress=이천, permission=USER)
        System.out.println(user06);
    }

    @Test
    void save() throws Exception {
        UsersDto user=new UsersDto();
        user.setUId("test33");
        user.setName("문은정");
        user.setPw("3333");
        user.setPhone("3333335559");
        user.setImgPath("./img/users/test33.jpeg");
        user.setEmail("test33@test.com");
        user.setGender("FEMALE");
        user.setAddress("서울시");
        user.setDetailAddress("마포구");
        user.setBirth("1993-12-25");
        int save=usersDao.save(user);
        Assertions.assertEquals(save,1); // assertEquals(expected, actual): 예상 값과 실제 값이 같은지 비교합니다.
        UsersDto test999=usersDao.findByUId("test33");
        System.out.println(test999);
    }

    @Test
    void updatePermissionByUIdAndPw() throws Exception {
        UsersDto user=new UsersDto();
        user.setUId("test999"); // 업데이트 하는 순서는 쿼리문과 같지않아도 상관없다
        user.setPw("44444");
        user.setPermission("PRIVATE");
        int update=usersDao.updatePermissionByUIdAndPw(user);
        Assertions.assertEquals(update,1);

    }

    @Test
    void deleteByUIdAndPw() throws Exception {
//        int delete= usersDao.deleteByUIdAndPw("test33");
        int delete= usersDao.deleteByUIdAndPw("test33","3333");
        Assertions.assertEquals(delete,1);
    }
}