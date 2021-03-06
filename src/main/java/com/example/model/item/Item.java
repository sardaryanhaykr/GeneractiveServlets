package com.example.model.item;

import com.example.model.Configuration;
import com.example.model.group.Group;
import com.example.servlet.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_id_seq")
    @SequenceGenerator(name = "items_id_seq", sequenceName = "items_id_seq", allocationSize = 1)
    private long id;

    @Column(name="price")
    private double price;

    @Column(name="name")
    private String name;

    @Column(name="image_url")
    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "group_id")
    //@JsonIgnore
    private Group parent;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;


    private Item(long id, double price, String name, String imageUrl, Group parent,ItemType type) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.parent = parent;
        this.type=type;
    }

    public Item(Item item){
        this.id = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.imageUrl = item.getImageUrl();
        this.parent = item.getParent();
        this.type=item.getType();
    }

    public Item(double price, String name, String imageUrl, Group parent) {
        this(0, price, name, imageUrl, parent,ItemType.STOCK);
    }

    public Item() {
        this(0, 0, null, null, null,ItemType.STOCK);
    }

    public double calculatePrice(Configuration configuration){
        //-------------------------implementatin expected-----------
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public Group getParent() {
        return parent;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Double.compare(item.price, price) == 0 && Objects.equals(name, item.name) && Objects.equals(imageUrl, item.imageUrl) && Objects.equals(parent, item.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, imageUrl, parent);
    }
}
