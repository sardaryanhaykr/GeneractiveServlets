package repository;

/**
 * Created by Hayk on 18.07.2021.
 */
interface CrudRepository<T, Integer> {
    void create(T t);

    void update(T t, Integer id);

    void delete(Integer id);
}
