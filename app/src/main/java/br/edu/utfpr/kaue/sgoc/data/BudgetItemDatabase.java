package br.edu.utfpr.kaue.sgoc.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.kaue.sgoc.model.BudgetItem;
import br.edu.utfpr.kaue.sgoc.model.Item;

@Database(entities = {BudgetItem.class, Item.class}, version = 1, exportSchema = false)
public abstract class BudgetItemDatabase extends RoomDatabase {

    public abstract BudgetItemDAO getBudgetItemDAO();

    private static BudgetItemDatabase INSTANCE;

    public static BudgetItemDatabase getInstance(final Context context) {
        if (INSTANCE == null){
            synchronized (BudgetItemDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, BudgetItemDatabase.class, "budgetitem.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
