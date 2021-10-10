import com.example.config.ApplicationContainer;
import com.example.model.item.Item;
import com.example.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Hayk on 14.08.2021.
 */
class ItemRepositoryTest {
    private final ItemRepository itemRepository = ApplicationContainer.context.getBean(ItemRepository.class);
    Item item = new Item(200, "item", "urlimg", null);

    @Test
    @DisplayName("CreateItem")
    void create() {
        itemRepository.create(item);
        assertEquals(item, itemRepository.findById(item.getId()).get());
    }


    @Test
    @DisplayName("UpdateItem")
    void update() {

        itemRepository.create(item);
        Item item1 = new Item(200, "UpdateName", "UpdateUrl", null);
        item1.setId(item.getId());
        itemRepository.update(item1);

        assertEquals(item1, itemRepository.findById(item.getId()).get());
    }

    @Test
    @DisplayName("DeleteItem")
    void delete() {
        itemRepository.create(item);
        assertTrue(itemRepository.findById(item.getId()).isPresent());
        itemRepository.delete(item);
        assertFalse(itemRepository.findById(item.getId()).isPresent());
    }

    @Test
    @DisplayName("FindItemById")
    void findById() {
        itemRepository.create(item);
        assertTrue(itemRepository.findById(item.getId()).isPresent());
    }

    @Test
    @DisplayName("FindAllItems")
    void findAll() {
        itemRepository.create(item);
        assertTrue(!itemRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("FindItemByName")
    void findByName() {
        itemRepository.create(item);

        assertTrue(itemRepository.findByName(item.getName()).isPresent());
    }

    @Test
    @DisplayName("FindByPriceRange")
    void search() {
        Item item1 = new Item(150, "item", "img", null);
        itemRepository.create(item1);
        itemRepository.create(item);
        assertFalse(itemRepository.search(100, 200).isEmpty());
        assertTrue(itemRepository.search(10, 20).isEmpty());

    }


}