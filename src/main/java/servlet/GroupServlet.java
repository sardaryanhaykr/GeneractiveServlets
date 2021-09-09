package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Group;
import repository.GroupRepository;
import util.URLUtils;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "GroupServlet", value = "/Group")
public class GroupServlet extends HttpServlet {
    private final GroupRepository groupRepository=new GroupRepository() ;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId=URLUtils.getLastPathSegment(request,response);
        if (groupId==null){
            return;
        }
        int id=groupId.intValue();
        Optional<Group> group=groupRepository.findById(id);
        if (group.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(group.get()));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId=URLUtils.getLastPathSegment(request,response);
        if (groupId==null){
            return;
        }
        int id=groupId.intValue();
        if (!groupRepository.findById(id).isPresent()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        }  else {
            String payload = request.getReader().lines().collect(Collectors.joining());
            Group group=new ObjectMapper().readValue(payload,Group.class);
            groupRepository.update(group,id);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId=URLUtils.getLastPathSegment(request,response);
        if (groupId==null){
            return;
        }
        int id=groupId.intValue();
        if (groupRepository.findById(id).isPresent()){
            groupRepository.delete(id);
        }  else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        }
    }
}
