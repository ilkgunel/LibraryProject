package com.ilkaygunel.entities;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Book.findAll",query = "select b from Book b")})
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private long id;

    @Column(nullable = false)
    private String bookName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return String.format("Book [id=%d, bookName='%s']", id, bookName);
    }
}
