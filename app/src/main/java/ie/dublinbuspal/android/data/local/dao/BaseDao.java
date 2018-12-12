package ie.dublinbuspal.android.data.local.dao;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

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
