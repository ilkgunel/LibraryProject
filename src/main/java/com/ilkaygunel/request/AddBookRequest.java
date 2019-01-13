package com.ilkaygunel.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AddBookRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Book Name Field Can't Be Empty")
    @Size(min = 2,message = "Book Name Can't Be Less Than 2 Characters")
    private String bookName;

    @NotNull(message = "Author Name Field Can't Be Empty")
    @Size(min = 2,message = "Author Name Can't Be Less Than 2 Characters")
    private String authorName;

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
}
