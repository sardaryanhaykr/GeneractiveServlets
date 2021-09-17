package model;

import model.group.Group;

public class GeneractiveItem extends Item{
    private double complexity;

    public GeneractiveItem(double price, String name, String imageUrl, Group parent, double complexity){
        super(price,name,imageUrl,parent);
        this.complexity=complexity;
    }

    public GeneractiveItem(Item item,double complexity){
        super(item);
        this.complexity=complexity;
    }

    public GeneractiveItem(){
        this(0,null,null,null,0);
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
