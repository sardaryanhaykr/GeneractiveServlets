package repository;

import model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Hayk on 19.07.2021.
 */
public class GroupRepository implements CrudRepository<Group, Integer> {

    private final List<Group> groups;

    public GroupRepository() {
        groups = new ArrayList<>();
    }

    @Override
    public void create(Group group) {
        groups.add(group);
    }

    @Override
    public void update(Group group, Integer id) {
        Group group1;
        if (findById(id).isPresent()) {
            group1 = findById(id).get();
            if (group.getName() != null) {
                group1.setName(group.getName());
            }
            if (group.getParent() != null) {
                group1.setParent(group.getParent());
            }
            if (group.getSubGroups() != null) {
                group1.setSubGroups(group.getSubGroups());
            }
            if (group.getItems() != null) {
                group1.setItems(group.getItems());
            }
        }
    }

    @Override
    public void delete(Integer id) {
        if (findById(id).isPresent()) {
            groups.remove(findById(id).get());
        }
    }

    public Optional<Group> findById(int id) {
        return groups.stream()
                .filter(group -> group.getId() == id)
                .findFirst();
    }

    public List<Group> findAll() {
        return groups;
    }

    public List<Group> findAllRoot() {
        return groups.stream()
                .filter(group -> group.getParent() == null)
                .collect(Collectors.toList());
    }

    public Optional<Group> findByName(String name) {
        return groups.stream()
                .filter(group1 -> group1.getName().equals(name))
                .findFirst();
    }

    public List<Group> findByParent(Group parent) {
        return groups.stream().filter(group -> group.getParent() != null)
                .filter(group -> group.getParent().equals(parent))
                .collect(Collectors.toList());
    }

    public void clear() {
        groups.clear();
    }
}
