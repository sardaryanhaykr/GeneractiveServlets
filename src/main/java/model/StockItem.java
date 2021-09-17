package model;

import model.group.Group;

public class StockItem extends Item{


    public StockItem(double price, String name, String imageUrl, Group parent) {
        super(price, name, imageUrl, parent);
    }

    public StockItem(Item item){
        super(item);
    }

    public StockItem() {
        this(0,null,null,null);
    }

    @Override
    public double calculatePrice(Configuration configuration) {
        return getPrice()*configuration.getResolution().getCoefficient();
    }
}
