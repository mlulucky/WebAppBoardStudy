package com.acorn.webappboard.dto;

import lombok.*;

// 🍎컴파일(java->class) 할때 자동으로 무엇인가 하는것? 어노테이션!
// lombok 어노테이션 // 필드의 이름이 바껴도 겟터 셋터를 컴파일할때 자동으로 생성해준다.
// 보기메뉴 -> 도구창 -> 구조 메뉴에서 getter/setter 확인가능하다

@Data // getter/setter , 생성자, equals, toString 메서드 **자동으로 생성
//@Getter // getter 메서드
//@Setter // setter 메서드
//@NoArgsConstructor // 생성자
//@EqualsAndHashCode // equals 메서드 // 내 자신인지 확인하는 거
//@ToString // toString

// 어노테이션 : 컴파일 할때 자동으로 생성하는 것
public class UsersDto {
    // ** db 에서 _ 규칙을 사용하는 이유는 대소문자(낙타표기법)를 구분하지 않기 때문에 (설정으로 대소문자를 구분하게 할 수도 있다.)
    // 예) WebAppBoard == webappboard

    // DTO(데이터(테이블에서 가져온 결과)) DataTransferObject :
    // 🍎db 에서 가져온 결과(데이터)를 맵핑(테이블과 동일하게 맵핑)해서 controller 와 view 에 전달하는 역할
    // 🍎변수명은 자바의 낙타표기법을 지켜줘야한다. (java 는 변수를 낙타표기법으로 한다.)
    // 🍎database 의 타입과 유사한(맵핑 가능한) 자바의 데이터 타입으로 명시한다.
    // JPA(ORM) 환Entity 를 낙타표기법으로 작성하면 필드로 생각함 // ORM == 쿼리를 자동으로 생성
    // u_id 로 필드명 기입하는 경우, JPA(==ORM) 에서 sql 쿼리자동생성하는 함수(findByuId(테이블 필드명)) 작성 시
    // "_" 을 필드접근자라고 생각한다 => "_" 사용안하고 낙타표기법 사용
    // 예) u_id => findByU_id(paramUId)
    // => SELECT * FROM users WHERE u_id(=:paramUId) (기대 - u_id 필드 = 매개변수필드(sql문의 필드명)) // : 매개변수 필드 표기
    // => SELECT * FROM users WHERE u.id(=:paramUId) (실제로 생성되는 결과 - 객체 u 의 필드 id)

    private String uId;
    private String pw;
    private String name;
    private String phone;
    private String imgPath;
    private String email;
    private java.util.Date postTime; // 데이트 타입 (util 의 데이트 패키지사용 // sql 과 타입이달라서 패키지로 명시)
    private String birth; // 유닉스시간(1997.01.01) 를 기준으로 하면 1970 이전에 태어난 사람을 저장할 수 없어서 ... 문자열타입으로 변경
    // private java.util.Date birth; // 데이트객체 : 1997년1월1일부터~ 밀리초~ (이전에 태어난 사람은 저장 못함)
    // 이전 시간은 로컬데이트 타입으로 또는 문자열로 받기 // 문자열로 하는 경우에는 나이를 구하기 어려워, 다시 데이트 객체로 변환해야한다.
    private String gender;
    private String address;
    private String detailAddress;
    private String permission;

}
