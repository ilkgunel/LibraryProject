package com.ilkaygunel.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({@NamedQuery(name = "Book.findAll",query = "select b from Book b")})
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", precision = 1)
    private long id;

    @Column(nullable = false)
    @NotNull(message = "Book Name Field Can't Be Empty")
    @Size(min = 2,message = "Book Name Can't Be Less Than 2 Characters")
    private String bookName;

    @Column
    @NotNull(message = "Author Name Field Can't Be Empty")
    @Size(min = 2,message = "Author Name Can't Be Less Than 2 Characters")
    private String authorName;

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return String.format("Book [id=%d, bookName='%s', writerName='%s']", id, bookName, authorName);
    }
}
