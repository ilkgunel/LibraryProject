package com.ilkaygunel.webservice;

import com.ilkaygunel.request.AddBookRequest;
import com.ilkaygunel.request.UpdateBookRequest;
import com.ilkaygunel.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilkaygunel.entities.Book;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryWebService {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Book> addNewBookToLibrary(@Valid @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.addBook(addBookRequest);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getAllBooks() throws Exception{
        List<Book> allBooks = bookService.findAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(value = "/get/by/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id){
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Book> updateBook(@RequestBody UpdateBookRequest updateBookRequest) {
        Book book = bookService.updateBook(updateBookRequest);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/delete/{bookId}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable String bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().body("Successfully Deleted!");
    }
}
