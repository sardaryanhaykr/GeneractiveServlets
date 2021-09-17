package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.GeneractiveItem;
import model.group.Group;
import model.Item;
import model.StockItem;
import repository.ItemRepository;
import servlet.enums.ItemType;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ItemsServlet", value = "/Items")
public class ItemsServlet extends HttpServlet {

    public static final String PARAM_TYPE = "type";
    private final List<Item> items=new ArrayList<>();
    private final ItemRepository itemRepository =new ItemRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(itemRepository.findAll()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeParam = request.getParameter(PARAM_TYPE);
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        if (typeParam == null || typeParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing param: " + PARAM_TYPE);
            return;
        }
        Group group=new Group("group");

        ObjectMapper objectMapper = new ObjectMapper();

        ItemType itemType = ItemType.valueOf(typeParam);

        String payload = request.getReader().lines().collect(Collectors.joining());
        Item item;
        switch (itemType) {
            case GENERATIVE:
                item = objectMapper.readValue(payload, GeneractiveItem.class);
                break;
            case STOCK:
                item = objectMapper.readValue(payload, StockItem.class);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Wrong type: " + itemType);
                return;
        }

        itemRepository.create(item);

        response.getWriter().write(objectMapper.writeValueAsString(item));
    }
}
