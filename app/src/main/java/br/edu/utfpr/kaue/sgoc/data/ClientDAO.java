package br.edu.utfpr.kaue.sgoc.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.model.Client;

@Dao
public interface ClientDAO {

    @Insert
    long insert(Client client);

    @Delete
    int delete(Client client);

    @Update
    int update(Client client);

    @Query("SELECT * FROM client WHERE id=:id")
    Client queryForId(long id);

    @Query("SELECT * FROM client ORDER BY name ASC")
    List<Client> queryAllAscending();

    @Query("SELECT * FROM client ORDER BY name DESC")
    List<Client> queryAllDescending();
}
