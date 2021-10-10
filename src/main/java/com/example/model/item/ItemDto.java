package com.example.model.item;

import com.example.servlet.enums.ItemType;

public class ItemDto {
    private Long id;
    private  Double price;
    private Double complexity;
    private String name;
    private String imageUrl;
    private Integer group_id;
    private ItemType type;

    public ItemDto(Long id,Double price,String name,String imageUrl,Integer group_id,ItemType type,Double complexity){
        this.id=id;
        this.price=price;
        this.complexity=complexity;
        this.name=name;
        this.imageUrl=imageUrl;
        this.group_id=group_id;
        this.type=type;
    }

    public ItemDto(Item item){
        this(item.getId(),item.getPrice(),item.getName(),item.getImageUrl(),item.getParent().getId(),item.getType(),null);
        if (item instanceof GeneractiveItem){
            this.complexity=((GeneractiveItem) item).getComplexity();
        }
    }

    public static Item mapToItem(ItemDto item){
        Item item1;
        if(item.getType()==ItemType.GENERATIVE){
             item1=new GeneractiveItem();
        }else{
            item1=new StockItem();
        }
        item1.setId(item.getId());
        item1.setPrice(item.getPrice());
        item1.setName(item.getName());
        item1.setImageUrl(item.getImageUrl());
        return item1;
    }

    public ItemDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
