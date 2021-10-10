package com.example.servlet.itemServlet;


import com.example.config.ApplicationContainer;
import com.example.repository.ItemRepository;
import com.example.servlet.HttpConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ItemsServlet", value = "/Item")
public class ItemsServlet extends HttpServlet {

    public static final String PARAM_TYPE = "type";
    private final ItemRepository itemRepository = ApplicationContainer.context.getBean(ItemRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(itemRepository.findAll()));
    }
}
