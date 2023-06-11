package com.acorn.webappboard.vo;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class PageVo {
    private int page;
    private int totalRow;
    private int totalPage;
    private  int next;
    private  int prev;
    private Boolean isNext;
    private Boolean isPrev;
    private int rowLength=5;
    private int offset;
    private String query;
    private String searchField;
    private String searchValue;
    private String orderField;
    private String orderDirect;

    public PageVo(int page, int totalRow, String reqQuery, int rowLength) {
        this.page=page;
        this.totalRow=totalRow;
        this.rowLength=rowLength;
        this.offset=(page-1)*rowLength;
        this.totalPage=(int)Math.ceil((double) totalRow/rowLength);
        this.next=page+1;
        this.prev=page-1;
        this.isNext=(this.next<=this.totalPage);
        this.isPrev=(this.prev>=1);
        this.query="";

        String[] queries=reqQuery.split("&"); // ["key"="value","key"="value".. ]
        for (String q : queries) { // "key"="value"
            String[] queryParam=q.split("="); // ["key":"value"]
            String key=queryParam[0];
            if (!key.equals("page")) { // 문자열 비교 수정
                this.query+=key+"="+queryParam[1]+"&";

                if(key.equals("field")){
                    this.searchField=queryParam[1];
                }else if(key.equals("value")){
                    this.searchValue=queryParam[1];
                }else if(key.equals("orderField")){
                    this.orderField=queryParam[1];
                }else if(key.equals("orderDirect")){
                    this.orderDirect=queryParam[1];
                }
            }

        }
    }

}


