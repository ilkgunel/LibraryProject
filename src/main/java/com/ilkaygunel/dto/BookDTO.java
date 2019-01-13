package com.ilkaygunel.dto;

public class BookDTO {

    private int id;
    private String bookName;
    private String authorName;

    public BookDTO() {

    }

    public BookDTO(int id, String bookName){
        this.id = id;
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
