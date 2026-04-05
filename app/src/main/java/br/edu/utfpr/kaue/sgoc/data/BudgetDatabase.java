package br.edu.utfpr.kaue.sgoc.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.utfpr.kaue.sgoc.model.Budget;
import br.edu.utfpr.kaue.sgoc.ui.util.DateConverter;

@Database(entities = {Budget.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BudgetDatabase extends RoomDatabase {

    public abstract BudgetDAO getBudgetDao();

    private static BudgetDatabase INSTANCE;

    public static BudgetDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, BudgetDatabase.class, "budget.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
