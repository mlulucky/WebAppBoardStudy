package com.acorn.webappboard.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BoardsDto {
    // 구현
    private int bId; // pk
    private String uId; // fk users.u_id // users 테이블의 u_id 참조
    private java.util.Date postTime; // insert(등록할때) CURRENT_TIMESTAMP 함수(자동으로 시간등록)
    private java.util.Date updateTime; // insert(등록),update(수정) CURRENT_TIMESTAMP // date가 종류가 많아서 java.util 꺼 쓸 자신이 없으면 패키지이름 전체 적기
    private String status; // enum(열거형) ("PUBLIC", "REPORT", "BLOCK", "PRIVATE")
    private String title;
    private String content;
    private int viewCount;
}
