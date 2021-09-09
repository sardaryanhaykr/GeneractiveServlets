package repository;

import model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository implements CrudRepository<User,Long>{
    private final List<User> users;

    public UserRepository(){
        users=new ArrayList<>();
    }

    public Optional<User> findById(long id){
        return users.stream()
                .filter(user -> user.getId()==id)
                .findFirst();
    }

    public Optional<User> findByUserNameandPassword(String userName,String password){
        return users.stream()
                .filter(user -> user.getUserName()==userName&&user.getPassword()==password)
                .findFirst();
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void update(User user, Long id) {
        Optional<User> user1=findById(id);
        if (!user1.isPresent()) return;
        user1.get().setUserName(user.getUserName());
        user1.get().setPassword(user.getPassword());
        user1.get().setRole(user.getRole());
        user1.get().setAuthorized(user.isAuthorized());
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isPresent()){
            users.remove(findById(id).get());
        }
    }
}
