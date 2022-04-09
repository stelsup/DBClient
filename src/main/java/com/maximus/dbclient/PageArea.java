package com.maximus.dbclient;

public class PageArea {
    private int userPageNum; // "номер страницы"
    private int offset; // с
    private int limit; // до

    public PageArea () {
        this.userPageNum = 1; // "номер страницы"
        this.offset = 0; // с
        this.limit = 50; // до
    }

    public void setPage (int numPage) {
        int i = 50;
        this.userPageNum = numPage;
        this.limit = numPage * i;
        this.offset = limit - 50;
    }

    public int getUserPageNum() {
        return userPageNum;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

}
