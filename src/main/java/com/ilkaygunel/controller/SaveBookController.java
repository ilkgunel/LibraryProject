package com.ilkaygunel.controller;

import com.ilkaygunel.dto.BookDTO;
import com.ilkaygunel.serviceimpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SaveBookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/saveBookPage")
    public String renderSaveBookPage(Model model){
        model.addAttribute("bookDTO", new BookDTO());
        return "saveBookPage";
    }

    @RequestMapping({"/saveBook"})
    public String saveBook(final BookDTO bookDTO) {
        bookService.save(bookDTO);
        return "bookPage";
    }
}
