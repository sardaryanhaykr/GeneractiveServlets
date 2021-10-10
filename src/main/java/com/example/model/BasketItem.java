package com.example.model;

import com.example.model.item.GeneractiveItem;
import com.example.model.item.Item;
import com.example.model.item.StockItem;

public class BasketItem {
    private final double complexity;
    private final Item item;
    private  final Configuration configuration;
    public BasketItem(Item item,Configuration configuration, double complexity){
        this.item=item;
        this.complexity=complexity;
        this.configuration=configuration;
    }

    public double calculatePrice(Configuration configuration){
        if (complexity==0){
           return new StockItem(item).calculatePrice(configuration);
        }else{
            return new GeneractiveItem(item,complexity).calculatePrice(configuration);
        }
    }

    public double getComplexity() {
        return complexity;
    }

    public Item getItem() {
        return item;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
