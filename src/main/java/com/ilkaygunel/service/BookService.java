package com.ilkaygunel.service;

import com.ilkaygunel.entities.Book;
import com.ilkaygunel.exception.ResourceNotFoundException;
import com.ilkaygunel.request.AddBookRequest;
import com.ilkaygunel.request.UpdateBookRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends BaseService {

    public Book addBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setBookName(addBookRequest.getBookName());
        book.setAuthorName(addBookRequest.getAuthorName());
        bookFacade.create(book);
        return book;
    }

    public Book findBookById(String bookId){
        long longId = Long.parseLong(bookId);
        Book book = bookFacade.find(longId);
        if(book == null)
            throw new ResourceNotFoundException("Any record couldn't find with this id:"+bookId);
        return bookFacade.find(longId);
    }

    public List<Book> findAllBooks() {
        List<Book> bookList = null;
        try {
            bookList = bookFacade.findListByNamedQuery("Book.findAll");
        } catch (Exception e) {
            //TODO implement here later
        }
        return bookList;
    }

    public Book updateBook(UpdateBookRequest updateBookRequest){
        Book book = new Book();
        book.setId(updateBookRequest.getId());
        book.setBookName(updateBookRequest.getBookName());
        book.setAuthorName(updateBookRequest.getAuthorName());
        return bookFacade.update(book);
    }

    public void deleteBook(String bookId){
        long longBookId = Long.parseLong(bookId);
        Book book = bookFacade.find(longBookId);
        //TODO kitap bulunamazsa hata fırlatılacak
        bookFacade.delete(book);
    }
}
