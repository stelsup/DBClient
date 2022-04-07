package com.maximus.dbclient;

public class PageArea {
    private int userPageNum; // "номер страницы"
    private int offset; // с
    private int limit; // до

    public PageArea (int pageNum, int offset, int limit) {
        this.userPageNum = pageNum; // "номер страницы"
        this.offset = offset; // с
        this.limit = limit; // до
    }

    public int getUserPageNum() {
        return userPageNum;
    }

    public void setUserPageNum(int userPageNum) {
        this.userPageNum = userPageNum;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
