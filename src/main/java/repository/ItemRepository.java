package repository;

import model.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Hayk on 19.07.2021.
 */
public class ItemRepository implements CrudRepository<Item, Long> {
    private final List<Item> items;

    public ItemRepository() {
        items = new ArrayList<>();
    }

    @Override
    public void create(Item item) {
        items.add(item);
    }

    @Override
    public void update(Item item, Long id) {
        Item item1;
        if(findById(id).isPresent()){
            item1 = findById(id).get();
            if (item.getPrice() != 0) {
                item1.setPrice(item.getPrice());
            }
            if (item.getName() != null) {
                item1.setName(item.getName());
            }
            if (item.getParent() != null) {
                item1.setParent(item.getParent());
            }
            if (item.getImageUrl() != null) {
                item1.setImageUrl(item.getImageUrl());
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isPresent()) {
            items.remove(findById(id).get());
        }
    }

    public Optional<Item> findById(long id) {
        return items.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    public List<Item> findAll() {
        return items;
    }

    public Optional<Item> findByName(String name) {
        return items.stream()
                .filter(item1 -> name.equals(item1.getName()))
                .findFirst();


    }

    public List<Item> findHighestPricedItems() {
        double maxPrice = items.stream().max(Comparator.comparing(Item::getPrice)).get().getPrice();
        return items.stream()
                .filter(item -> item.getPrice() == maxPrice)
                .collect(Collectors.toList());

    }

    public List<Item> findSmallestPricedItems() {
        double minPrice = items.stream()
                .min(Comparator.comparing(Item::getPrice))
                .get()
                .getPrice();
        return items.stream()
                .filter(item -> item.getPrice() == minPrice)
                .collect(Collectors.toList());

    }

    public List<Item> findByPriceRange(double priceMin, double priceMax) {

        return items.stream()
                .filter(item -> item.getPrice() >= priceMin && item.getPrice() <= priceMax)
                .collect(Collectors.toList());

    }

    public List<Item> searchItems(String name, double priceL, double priceH){
        return findByPriceRange(priceL,priceH).stream()
                .filter(item -> !name.isEmpty()&&item.getName().equals(name))
                .collect(Collectors.toList());
    }

    public void clear(){
        items.clear();
    }
    public boolean isEmpty(){
        return items.isEmpty();
    }
}
