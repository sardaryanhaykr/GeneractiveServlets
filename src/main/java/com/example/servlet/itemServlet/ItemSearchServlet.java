package com.example.servlet.itemServlet;

import com.example.config.ApplicationContainer;
import com.example.model.item.Item;
import com.example.repository.ItemRepository;
import com.example.servlet.HttpConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "ItemsSearchServlet", value = "/Item/search")
public class ItemSearchServlet extends HttpServlet {

    private final ItemRepository itemRepository = ApplicationContainer.context.getBean(ItemRepository.class);

    public static final String PARAM_NAME = "name";
    public static final String PARAM_PRICE_L = "priceL";
    public static final String PARAM_PRICE_H = "priceH";
    double priceL;
    double priceH;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(HttpConstants.CONTENT_TYPE_JSON);

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String value = request.getParameter(key);
            if (value != null && !value.isEmpty()) {
                switch (key) {
                    case PARAM_PRICE_L:
                        priceL = Double.parseDouble(value);
                        break;
                    case PARAM_PRICE_H:
                        priceH = Double.parseDouble(value);
                        break;
                }
                List<Item> items = itemRepository.search(priceL, priceH);
                if (items == null || items.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    response.getWriter().write("No content");

                    return;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().write(objectMapper.writeValueAsString(items));
            }
        }
    }
}
