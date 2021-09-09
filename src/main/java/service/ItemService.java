package service;

import model.Group;
import model.Item;
import repository.GroupRepository;
import repository.ItemRepository;

import java.util.List;

/**
 * Created by Hayk on 19.07.2021.
 */
public class ItemService {
    private ItemRepository itemRepository;
    private GroupRepository groupRepository;

    public ItemService() {
        itemRepository = new ItemRepository();
        groupRepository = new GroupRepository();
    }

    public void add(Item item, Group group) {
        if (getById(item.getId()) == null) {
            ItemUtil itemUtil = new ItemUtil();
            itemUtil.addRelation(item, group);
            itemRepository.create(item);
        }
    }

    public void update(Item item, long id) {
        Item item1 = getById(id);
        if (item1 != null) {
            itemRepository.update(item, id);
        }
    }

    public void delete(long id) {
        Item item = getById(id);
        if (item != null) {
            itemRepository.delete(id);
        }
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public List<Item> getAllByGroup(Group group) {
        return group.getItems();
    }

    public Item getById(long id) {
        if (itemRepository.findById(id).isPresent()) {
            return itemRepository.findById(id).get();
        }else{
            return null;
        }
    }

    private class ItemUtil {
        private ItemUtil() {
        }

        private void addRelation(Item item, Group group) {
            item.setParent(group);
            if (group != null) {
                group.getItems().add(item);
            }
        }
    }
}
