package dao;

import helpers.queries.Queries;

import java.util.List;

public interface Dao<T> extends Queries {
    void save(T object);

    void deleteById(Long id);

    void deleteAll();

    List<T> getAllRecords();

    T getById(Long id);

    List<T> getByIds(List<Long> ids);

    int getAllRecordsCount();

    T getRandomId();

    List<T> getRandomIds(int randomCount);

}
