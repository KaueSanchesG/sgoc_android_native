package br.edu.utfpr.kaue.sgoc.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.model.BudgetItem;

@Dao
public interface BudgetItemDAO {

    @Insert
    long insert(BudgetItem budgetItem);

    @Delete
    int delete(BudgetItem budgetItem);

    @Update
    int update(BudgetItem budgetItem);

    @Query("SELECT * FROM budgetitem WHERE id=:id")
    BudgetItem queryForId(long id);

    @Query("SELECT budgetitem.* FROM budgetitem INNER JOIN item ON item.id = budgetitem.itemId ORDER BY item.name ASC")
    List<BudgetItem> queryAll();
}
