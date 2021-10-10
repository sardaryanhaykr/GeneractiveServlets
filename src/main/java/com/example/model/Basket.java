package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private final List<BasketItem> items;

    public Basket(){
        items=new ArrayList<>();
    }

    public double getTotalPrice(){
        double totalPrice=0;
        for (BasketItem item:items) {
            totalPrice+=item.calculatePrice(item.getConfiguration());
        }
        return totalPrice;
    }

    public void addItem(BasketItem item){
        items.add(item);
    }

    public void deleteItem(BasketItem item){
        if (items.contains(item)){
            items.remove(item);
        }
    }

    public List<BasketItem> getItems(){
        return items;
    }
}
