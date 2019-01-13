package com.ilkaygunel.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ilkaygunel.application.Application;
import com.ilkaygunel.entities.Book;
import com.ilkaygunel.exception.ApiError;
import com.ilkaygunel.request.AddBookRequest;
import com.ilkaygunel.request.UpdateBookRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.expression.Mvc;
import org.thymeleaf.util.StringUtils;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class TestOperations {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAllRecordsAndCheck() throws Exception {
        mockMvc.perform(get("/library/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void testAddingNewRecord() throws Exception {
        //Find max id in data base
        Gson gson = new GsonBuilder().create();
        long maxId=0l;
        MvcResult mvcResult = mockMvc.perform(get("/library/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(content).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Book book = gson.fromJson(jsonArray.get(i), Book.class);
            if(book.getId()>maxId){
                maxId = book.getId();
            }
        }

        //Add new record and test it
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Gurur Ve Önyargı");

        int intOfMaxId = Math.toIntExact(maxId);


        mockMvc.perform(post("/library/add").
                contentType(MediaType.APPLICATION_JSON).
                content(gson.toJson(addBookRequest)).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(intOfMaxId+1)))
                .andExpect(jsonPath("bookName", is("Gurur Ve Önyargı")));
    }

    @Test
    public void testSingleGet() throws Exception {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Hobbit");

        Gson gson = new GsonBuilder().create();

        MvcResult mvcResult = mockMvc.perform(post("/library/add").
                contentType(MediaType.APPLICATION_JSON).
                content(gson.toJson(addBookRequest)).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("bookName", is("Hobbit")))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Book book = gson.fromJson(content,Book.class);

        int intOfBookId = Math.toIntExact(book.getId());

        mockMvc.perform(get("/library/get/by/id/"+String.valueOf(book.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id",is(intOfBookId)))
                .andExpect(jsonPath("bookName", is("Hobbit")));
    }

    @Test
    public void testUpdate() throws Exception{
        //Find max id in data base
        Gson gson = new GsonBuilder().create();
        long maxId=0l;
        MvcResult getAllBooksMvcResult = mockMvc.perform(get("/library/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();

        String content = getAllBooksMvcResult.getResponse().getContentAsString();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(content).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Book book = gson.fromJson(jsonArray.get(i), Book.class);
            if(book.getId()>maxId){
                maxId = book.getId();
            }
        }

        //Add new record and test it
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Yüzüklerin Efendisi");

        int intOfMaxId = Math.toIntExact(maxId);


        MvcResult addNewBookMvcResult = mockMvc.perform(post("/library/add").
                contentType(MediaType.APPLICATION_JSON).
                content(gson.toJson(addBookRequest)).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(intOfMaxId+1)))
                .andExpect(jsonPath("bookName", is("Yüzüklerin Efendisi"))).andReturn();

        //Update it
        String addNewBookResultContent = addNewBookMvcResult.getResponse().getContentAsString();
        Book book = gson.fromJson(addNewBookResultContent,Book.class);
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setId(book.getId());
        updateBookRequest.setBookName(book.getBookName()+" Changed");
        int intOfAddedBook = Math.toIntExact(book.getId());

        mockMvc.perform(put("/library/update").
                contentType(MediaType.APPLICATION_JSON).
                content(gson.toJson(updateBookRequest)).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(intOfAddedBook)))
                .andExpect(jsonPath("bookName", is(updateBookRequest.getBookName())));

    }

    @Test
    public void testDelete() throws Exception{
        //Find max id in data base
        Gson gson = new GsonBuilder().create();
        long maxId=0l;
        MvcResult getAllBooksMvcResult = mockMvc.perform(get("/library/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();

        String content = getAllBooksMvcResult.getResponse().getContentAsString();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(content).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Book book = gson.fromJson(jsonArray.get(i), Book.class);
            if(book.getId()>maxId){
                maxId = book.getId();
            }
        }

        //Add new record and test it
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Beşinci Kurban");

        int intOfMaxId = Math.toIntExact(maxId);


        MvcResult addNewBookMvcResult = mockMvc.perform(post("/library/add").
                contentType(MediaType.APPLICATION_JSON).
                content(gson.toJson(addBookRequest)).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(intOfMaxId+1)))
                .andExpect(jsonPath("bookName", is("Beşinci Kurban"))).andReturn();

        //Delete it
        String addNewBookResultContent = addNewBookMvcResult.getResponse().getContentAsString();
        Book book = gson.fromJson(addNewBookResultContent,Book.class);
        int intOfAddedBook = Math.toIntExact(book.getId());

        mockMvc.perform(delete("/library/delete/"+intOfAddedBook).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        //Check is deleted
        String error =  mockMvc.perform(get("/library/get/by/id/"+intOfAddedBook))
                .andExpect(status().isNotFound())
               .andReturn().getResolvedException().getMessage();
        System.out.println(error);
        assertTrue(StringUtils.equals(error, "Any record couldn't find with this id:"+intOfAddedBook));

    }
}
