package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.user.User;
import repository.UserRepository;
import util.URLUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UserServlet",value = "/user")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository=new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        Long userId= URLUtils.getLastPathSegment(request,response);
        if (userId==null){
            return;
        }
        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent()){
            ObjectMapper objectMapper=new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(user.get()));
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + userId);
        }
    }
}
