package com.ilkaygunel.controller;

import com.ilkaygunel.dto.BookDTO;
import com.ilkaygunel.entities.Book;
import com.ilkaygunel.serviceimpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UpdateBookPageController {

    @Autowired
    BookServiceImpl bookService;

    @PostMapping(value = "/updateBookPage/{bookId}")
    public String renderUpdateBookPage(@PathVariable String bookId, Model model){
        BookDTO bookDTO = bookService.findById(bookId);
        model.addAttribute("bookForUpdate",bookDTO);
        return "updateBookPage";
    }

    @PostMapping(value = "/updateBook")
    public String updateBook(BookDTO bookDTO){
        bookService.update(bookDTO);
        return "redirect:/";
    }
}
