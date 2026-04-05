package br.edu.utfpr.kaue.sgoc.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.model.Budget;
import br.edu.utfpr.kaue.sgoc.model.Client;

@Dao
public interface BudgetDAO {

    @Insert
    long insert(Budget budget);

    @Delete
    int delete(Budget budget);

    @Update
    int update(Budget budget);

    @Query("SELECT * FROM budget WHERE id=:id")
    Budget queryForId(long id);

    @Query("SELECT * FROM budget ORDER BY date ASC")
    List<Budget> queryAll();

}
