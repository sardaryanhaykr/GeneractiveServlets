package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.user.RequestUser;
import repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet",value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserRepository userRepository=new UserRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper=new ObjectMapper();
        String payload=request.getReader().lines().collect(Collectors.joining());
        RequestUser requestUser=objectMapper.readValue(payload,RequestUser.class);
        if (userRepository.findByUserNameandPassword(requestUser.getUserName(),requestUser.getPassword()).isPresent()){
            HttpSession session=request.getSession(false);
            session.setAttribute(requestUser.getUserName(),requestUser);
        }else{
            response.setStatus(204);
        }
    }
}
