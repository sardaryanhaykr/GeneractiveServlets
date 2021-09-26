package servlet.GroupServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.group.ResponseGroup;
import repository.GroupJDBCRepository;
import repository.GroupRepository;
import servlet.HttpConstants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GroupsServlet", value = "/Group")
public class GroupsServlet extends HttpServlet {
    private final GroupRepository groupRepository = new GroupRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(groupRepository.findAll()));
    }
}
