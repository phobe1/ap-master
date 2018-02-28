package com.you.ap.provider.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.book.IBookLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/book/location")
public class BookController {

    @Autowired
    private IBookLocationService bookLocationService;

    @RequestMapping(value = "/all.json", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllLocationList(){
        return ApiResponse.buildSuccess(bookLocationService.getLocationJson());
    }

    
}
