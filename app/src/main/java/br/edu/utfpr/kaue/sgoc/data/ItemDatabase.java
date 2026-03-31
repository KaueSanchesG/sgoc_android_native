package br.edu.utfpr.kaue.sgoc.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.kaue.sgoc.model.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public abstract ItemDAO getItemDao();

    private static ItemDatabase INSTANCE;

    public static ItemDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ItemDatabase.class, "items.db").allowMainThreadQueries().build();
                }
            }
        }

        return INSTANCE;
    }
}
