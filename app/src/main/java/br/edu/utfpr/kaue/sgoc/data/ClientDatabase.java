package br.edu.utfpr.kaue.sgoc.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.kaue.sgoc.model.Client;

@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class ClientDatabase extends RoomDatabase {

    public abstract ClientDAO getClientDao();

    private static ClientDatabase INSTANCE;

    public static ClientDatabase getInstance(final Context context) {
        if (INSTANCE == null){
            synchronized (ClientDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, ClientDatabase.class, "clients.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
