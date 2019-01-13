package com.ilkaygunel.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateBookRequest {

    @NotNull(message = "Id Field Can't Be Empty To Update Book")
    private long id;

    @NotNull(message = "Book Name Field Can't Be Empty")
    @Size(min = 2,message = "Book Name Can't Be Less Than 2 Characters")
    private String bookName;

    @NotNull(message = "Author Name Field Can't Be Empty")
    @Size(min = 4,message = "Author Name Can't Be Less Than 4 Characters")
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
}
