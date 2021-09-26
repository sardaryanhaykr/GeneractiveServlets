package service;

import model.group.Group;
import model.group.RequestGroup;
import model.group.ResponseGroup;
import repository.GroupRepository;

import java.util.List;

/**
 * Created by Hayk on 19.07.2021.
 */
public class GroupService {
    private GroupRepository groupRepository;

    public GroupService() {
        groupRepository = new GroupRepository();
    }

//    public void add(RequestGroup group){
//        groupRepository.create(group);
//    }

//    public void update(ResponseGroup group, int id) {
//        if (getById(id) != null) {
//            groupRepository.update(group, id);
//        }
//    }

//    public void delete(int id) {
//        if (getById(id) != null) {
//            groupRepository.delete(id);
//        }
//    }

//    public List<ResponseGroup> getAll() {
//        return groupRepository.findAll();
//    }

//    public List<Group> getRoots() {
//        return groupRepository.findAllRoot();
//    }

//    public ResponseGroup getById(int id) {
//        if (groupRepository.findById(id).isPresent()) {
//            return groupRepository.findById(id).get();
//        }else{
//            return null;
//        }
//    }

//    public Group getByName(String name){
//        if (groupRepository.findByName(name).isPresent()) {
//            return groupRepository.findByName(name).get();
//        }else{
//            return null;
//        }
//    }
}
