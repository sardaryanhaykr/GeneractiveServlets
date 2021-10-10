package com.example.model.item;

import com.example.model.Configuration;
import com.example.model.group.Group;
import com.example.model.item.Item;
import com.example.servlet.enums.ItemType;

public class StockItem extends Item {

    public StockItem(double price, String name, String imageUrl, Group parent) {
        super(price, name, imageUrl, parent);
        this.setType(ItemType.STOCK);
    }

    public StockItem(Item item){
        super(item);
        this.setType(ItemType.STOCK);
    }

    public StockItem() {
        this(0,null,null,null);
        this.setType(ItemType.STOCK);
    }


    public double calculatePrice(Configuration configuration) {
        return getPrice()*configuration.getResolution().getCoefficient();
    }
}
