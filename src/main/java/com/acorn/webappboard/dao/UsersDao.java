package com.acorn.webappboard.dao;

import com.acorn.webappboard.dto.UsersDto;

import java.util.List;

public interface UsersDao {
    // dao - db에 접속하는 오브젝트
    // dto - 테이블의 데이터를 맵핑하여 전송하는 오브젝트

    // 매개변수 - 함수에서 필요한 것들
    // 통신을 하는 것들은 모두 예외처리를 해야한다 - throws Exception

    // 추상메서드 : 메서드의 선언부만 있고 구현부(X) => 자식클래스에서 구현
    // 로그인을 하려면 아이디,패스워드로 검색한 결과 user 를 반환해야한다. UsersDto 타입. UsersDto 를 반환
    List<UsersDto> findAll() throws Exception;
    UsersDto findByUIdAndPw(String uId, String pw) throws Exception; // login // 로그인기능
    // => 유저 아이디와 패스워드를 받아서 이용. 유저(db 정보)를 찾는다.
    UsersDto findByUId(String uId) throws Exception; // detail, idCheck // 개인 상세정보, 아이디 체크 기능 
    // => 유저 아이디를 받아서 이용. 유저(db 정보)를 찾는다.
    // => 결과 유저(db정보) => 유저정보 UsersDto 를 반환
    int updateByPk(UsersDto user) throws Exception; // modify 개인정보 수정
    // 업데이트 하기위해서는 유저정보가 필요하므로, 매개변수로 user 를 받는다.
    // => 결과 성공여부 성공한 개수를 반환 => 메서드 반환타입 int
    int save(UsersDto user) throws Exception; // register 등록
    int updatePermissionByUIdAndPw(UsersDto user) throws Exception;  // 권한 수정
//    int deleteByUIdAndPw(String uId) throws Exception;
    int deleteByUIdAndPw(String uId,String pw) throws Exception;
}
