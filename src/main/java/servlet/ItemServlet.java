package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GeneractiveItem;
import model.Item;
import model.StockItem;
import repository.ItemRepository;
import servlet.enums.ItemType;
import util.URLUtils;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "ItemServlet", value = "/Item/*")
public class ItemServlet extends HttpServlet {

    public static final String PARAM_TYPE = "type";

    private final ItemRepository itemRepository = new ItemRepository();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeParam = request.getParameter(PARAM_TYPE);
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);

        if (typeParam == null || typeParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing param: " + PARAM_TYPE);
            return;
        }

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

        Long itemId = URLUtils.getLastPathSegment(request, response);
        if (itemId == null) return;

        item.setId(itemId);
        if (!itemRepository.findById(itemId).isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + itemId);
        }else{
            itemRepository.update(item,item.getId());
        }

        response.getWriter().write(objectMapper.writeValueAsString(item));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long itemId=URLUtils.getLastPathSegment(request,response);
        if (itemId==null){
            return;
        }

        Optional<Item> item=itemRepository.findById(itemId);
        if (item.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(item.get()));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + itemId);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Long itemId=URLUtils.getLastPathSegment(request,response);
        if (itemId==null){
            return;
        }

        if (itemRepository.findById(itemId).isPresent()){
            itemRepository.delete(itemId);
        }else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + itemId);
        }
    }
}
