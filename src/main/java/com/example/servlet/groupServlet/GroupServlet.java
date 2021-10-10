package com.example.servlet.groupServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.config.ApplicationContainer;
import com.example.model.group.Group;
import com.example.model.group.RequestGroup;
import com.example.repository.GroupRepository;
import com.example.servlet.HttpConstants;
import com.example.util.URLUtils;
import com.example.validator.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "GroupServlet", value = "/Group/*")
public class GroupServlet extends HttpServlet {
    private final GroupRepository groupRepository = ApplicationContainer.context.getBean(GroupRepository.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();

        String payload = request.getReader().lines().collect(Collectors.joining());
        RequestGroup group = objectMapper.readValue(payload, RequestGroup.class);
        Group created = null;
        Optional<Group> parent = groupRepository.findById(group.getParentId());
        Optional<Group> groupNamed = groupRepository.findByName(group.getName());
        if (!groupNamed.isPresent()) {
            if (parent.isPresent()) {
                created = new Group(group.getName(), parent.get());
                groupRepository.create(created);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Resource not found: " + group.getParentId());
            }

            response.getWriter().write(objectMapper.writeValueAsString(created));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Group with name " + group.getName() + " alredy exist");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId;
        ObjectMapper objectMapper = new ObjectMapper();
        String lastPathSegment = URLUtils.getPathLastSegment(request.getRequestURI());
        if (lastPathSegment != null && Validator.isInt(lastPathSegment)) {
            groupId = URLUtils.getLastPathSegment(request, response);
            int id = groupId.intValue();
            Optional<Group> group = groupRepository.findById(id);
            if (group.isPresent()) {
                response.getWriter().write(objectMapper.writeValueAsString(group.get()));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Resource not found: " + id);
            }
            return;
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        Long groupId = URLUtils.getLastPathSegment(request, response);
        if (groupId == null) {
            return;
        }
        int id = groupId.intValue();
        if (!groupRepository.findById(id).isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        } else {
            String payload = request.getReader().lines().collect(Collectors.joining());
            Group group = new ObjectMapper().readValue(payload, Group.class);
            groupRepository.update(group, id);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        Long groupId = URLUtils.getLastPathSegment(request, response);
        if (groupId == null) {
            return;
        }
        int id = groupId.intValue();
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            if (groupRepository.isParent(id)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Grup cant be deleted,it has sub groups : " + id);
            } else {
                groupRepository.delete(group.get());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        }
    }
}
