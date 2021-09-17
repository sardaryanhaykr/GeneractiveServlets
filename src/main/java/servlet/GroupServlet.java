package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.group.Group;
import model.group.RequestGroup;
import model.group.ResponseGroup;
import model.user.RequestUser;
import repository.GroupJDBCRepository;
import util.URLUtils;
import validator.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "GroupServlet", value = "/Group/*")
public class GroupServlet extends HttpServlet {
    private final GroupJDBCRepository groupRepository=new GroupJDBCRepository() ;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();

        String payload = request.getReader().lines().collect(Collectors.joining());
        RequestGroup group=objectMapper.readValue(payload,RequestGroup.class);
        groupRepository.create(group);
        response.getWriter().write(objectMapper.writeValueAsString(group));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long groupId;
        ObjectMapper objectMapper=new ObjectMapper();
        String lastPathSegment= URLUtils.getPathLastSegment(request.getRequestURI());
        if (lastPathSegment!=null&& Validator.isInt(lastPathSegment)){
            groupId=URLUtils.getLastPathSegment(request,response);
            int id=groupId.intValue();
            Optional<ResponseGroup> group=groupRepository.findById(id);
            if (group.isPresent()){
                response.getWriter().write(objectMapper.writeValueAsString(group.get()));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Resource not found: " + id);
            }
            return;
        }

        if (lastPathSegment==null){
          List<ResponseGroup> groupList=groupRepository.findAll().get();
            for (ResponseGroup group:groupList) {
                response.getWriter().write(objectMapper.writeValueAsString(group));
            }
           return;
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
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
            ResponseGroup group=new ObjectMapper().readValue(payload,ResponseGroup.class);
            groupRepository.update(group,id);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        Long groupId=URLUtils.getLastPathSegment(request,response);
        if (groupId==null){
            return;
        }
        int id=groupId.intValue();
        if (groupRepository.findById(id).isPresent()){
            if(groupRepository.findByParent(id).isPresent()){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Grup cant be deleted,it has sub groups : " + id);
            }else{
                groupRepository.delete(id);
            }
        }  else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + id);
        }
    }
}
