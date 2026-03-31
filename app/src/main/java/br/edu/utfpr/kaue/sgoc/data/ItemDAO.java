package br.edu.utfpr.kaue.sgoc.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.model.Item;

@Dao
public interface ItemDAO {

    @Insert
    long insert(Item item);

    @Delete
    int delete(Item item);

    @Update
    int update(Item item);

    @Query("SELECT * FROM item WHERE id=:id")
    Item queryForId(long id);

    @Query("SELECT * FROM item ORDER BY name ASC")
    List<Item> queryAllAscending();

    @Query("SELECT * FROM item ORDER BY name DESC")
    List<Item> queryAllDescending();

}
