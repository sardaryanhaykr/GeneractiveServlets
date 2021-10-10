package com.example.servlet.groupServlet;

import com.example.servlet.HttpConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.config.ApplicationContainer;
import com.example.repository.GroupRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GroupsServlet", value = "/Group")
public class GroupsServlet extends HttpServlet {
    private final GroupRepository groupRepository = ApplicationContainer.context.getBean(GroupRepository.class);

    public GroupsServlet(){}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(groupRepository.findAll()));
    }
}
