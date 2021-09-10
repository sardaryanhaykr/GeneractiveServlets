package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.user.RequestUser;
import model.user.User;
import repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet",value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserRepository userRepository=new UserRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper=new ObjectMapper();
        String payload=request.getReader().lines().collect(Collectors.joining());
        RequestUser requestUser=objectMapper.readValue(payload,RequestUser.class);
        Optional<User> user= userRepository.findByUserNameandPassword(requestUser.getUserName(),requestUser.getPassword());
        if (user.isPresent()&&!user.get().isAuthorized()){
            HttpSession session=request.getSession(false);

            session.setAttribute(requestUser.getUserName(),requestUser);
            user.get().setAuthorized(true);
        }else{
            response.setStatus(204);
        }
    }
}
