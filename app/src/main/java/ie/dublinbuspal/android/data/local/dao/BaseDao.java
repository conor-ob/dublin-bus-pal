package ie.dublinbuspal.android.data.local.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    void insert(T entity);

    @Insert(onConflict = REPLACE)
    void insertAll(List<T> entities);

    @Update
    void update(T entity);

    @Update
    void updateAll(List<T> entities);

    @Delete
    void delete(T entity);

}
