package com.ilkaygunel.service;

import com.ilkaygunel.facade.BookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    protected BookFacade bookFacade;
}
