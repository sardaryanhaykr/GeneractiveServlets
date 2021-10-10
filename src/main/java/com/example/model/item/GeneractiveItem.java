package com.example.model.item;

import com.example.model.Configuration;
import com.example.model.group.Group;
import com.example.servlet.enums.ItemType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class GeneractiveItem extends Item{
    @Column(name="complexity")
    private double complexity;

    public GeneractiveItem(double price, String name, String imageUrl, Group parent, double complexity){
        super(price,name,imageUrl,parent);
        this.complexity=complexity;
        this.setType(ItemType.GENERATIVE);
    }

    public GeneractiveItem(Item item,double complexity){
        super(item);
        this.complexity=complexity;
        this.setType(ItemType.GENERATIVE);
    }

    public GeneractiveItem(){

        this(0,null,null,null,0);
        this.setType(ItemType.GENERATIVE);
    }

    @Override
    public double calculatePrice(Configuration configuration) {
        return getPrice()*configuration.getResolution().getCoefficient()*complexity;
    }

    public double getComplexity() {
        return complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }
}
