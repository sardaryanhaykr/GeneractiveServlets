package com.example.servlet.itemServlet;

import com.example.config.ApplicationContainer;
import com.example.model.group.Group;
import com.example.model.item.GeneractiveItem;
import com.example.model.item.Item;
import com.example.model.item.ItemDto;
import com.example.model.item.StockItem;
import com.example.repository.GroupRepository;
import com.example.repository.ItemRepository;
import com.example.servlet.HttpConstants;
import com.example.servlet.enums.ItemType;
import com.example.util.URLUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "ItemServlet", value = "/Item/*")
public class ItemServlet extends HttpServlet {

    public static final String PARAM_TYPE = "type";

    private final ItemRepository itemRepository = ApplicationContainer.context.getBean(ItemRepository.class);
    private final GroupRepository groupRepository = ApplicationContainer.context.getBean(GroupRepository.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        Item item=objectMapper.readValue(payload,Item.class);
        item.setType(itemType);
        itemRepository.create(item);

        if (item.getId() == 0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Item dont save in database");
            return;
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
        Optional<Item> item=itemRepository.findById(itemId);
        if (item.isPresent()){
            itemRepository.delete(item.get());
        }else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + itemId);
        }
    }


    @Override
    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        Long itemId=URLUtils.getLastPathSegment(request,response);
        if (itemId==null){
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String payload = request.getReader().lines().collect(Collectors.joining());
        Item item1=objectMapper.readValue(payload,Item.class);
        item1.setId(itemId);
        Optional<Item> item=itemRepository.findById(itemId);
        if (item.isPresent()){
            itemRepository.update(item1);
        }else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Resource not found: " + itemId);
        }
    }
}
