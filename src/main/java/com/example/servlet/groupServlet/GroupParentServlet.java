package com.example.servlet.groupServlet;

import com.example.config.ApplicationContainer;
import com.example.model.group.Group;
import com.example.repository.GroupRepository;
import com.example.util.URLUtils;
import com.example.validator.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GroupParentServlet", value = "/Group/parent/*")
public class GroupParentServlet extends HttpServlet {
    private final GroupRepository groupRepository = ApplicationContainer.context.getBean(GroupRepository.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId;
        ObjectMapper objectMapper = new ObjectMapper();
        String lastPathSegment = URLUtils.getPathLastSegment(request.getRequestURI());
        if (lastPathSegment != null && Validator.isInt(lastPathSegment)) {
            groupId = URLUtils.getLastPathSegment(request, response);
            int id = groupId.intValue();
            Optional<List<Group>> groups = groupRepository.findByParent(id);
            if (groups.isPresent()) {
                response.getWriter().write(objectMapper.writeValueAsString(groups.get()));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Resource not found: " + id);
            }
            return;
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

}
