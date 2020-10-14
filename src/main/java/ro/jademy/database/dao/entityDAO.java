package ro.jademy.database.dao;

import java.util.List;

public interface entityDAO<T> {

    List<T> findAll();

    T findById(int itemId);

    void create(T item);

    void update(T item);

    void remove(T item);

    void remove(int itemId);
}
