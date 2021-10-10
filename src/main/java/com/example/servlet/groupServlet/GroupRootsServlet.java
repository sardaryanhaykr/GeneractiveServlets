package com.example.servlet.groupServlet;

import com.example.config.ApplicationContainer;
import com.example.repository.GroupRepository;
import com.example.servlet.HttpConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "GroupRootServlet", value = "/Group/root")
public class GroupRootsServlet extends HttpServlet{

    private final GroupRepository groupRepository = ApplicationContainer.context.getBean(GroupRepository.class);
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(groupRepository.findAllRoot()));
        }
}
