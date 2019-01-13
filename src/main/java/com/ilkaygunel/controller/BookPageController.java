package com.ilkaygunel.controller;

import com.ilkaygunel.dto.BookDTO;
import com.ilkaygunel.serviceimpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookPageController {

    @Autowired
    BookServiceImpl bookService;

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("books",bookService.findAll());
        return "bookPage";
    }

    @PostMapping({"/deleteBook/{bookId}"})
    public String deleteBook(@PathVariable String bookId){
        bookService.delete(bookId);
        return "redirect:/";
    }
}
