package com.ilkaygunel.serviceimpl;

import com.ilkaygunel.dto.BookDTO;
import com.ilkaygunel.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl {

    @Autowired
    RestTemplate restTemplate;

    @Value("${resource.book.add}")
    private String resourceBookAdd;

    @Value("${resource.book.getall}")
    private String resourceBookGetAll;

    @Value("${resource.book.getbyid}")
    private String resourceGetBookById;

    @Value("${resource.book.delete}")
    private String resourceBookDelete;

    @Value(("${resource.book.update}"))
    private String resourceBookUpdate;

    public BookDTO findById(String bookId){
        return restTemplate.getForObject(resourceGetBookById+bookId,BookDTO.class);
    }

    public List<BookDTO> findAll() {
        return Arrays.stream(restTemplate.getForObject(resourceBookGetAll, BookDTO[].class)).collect(Collectors.toList());
    }

    public void delete(String bookId) {
        restTemplate.delete(resourceBookDelete+bookId);
    }

    public BookDTO save(BookDTO bookDTO) {
        return restTemplate.postForObject(resourceBookAdd, bookDTO, BookDTO.class);
    }

    public void update(BookDTO bookDTO){
        restTemplate.put(resourceBookUpdate,bookDTO);
    }
}
