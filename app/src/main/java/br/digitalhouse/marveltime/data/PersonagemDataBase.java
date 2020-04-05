package br.digitalhouse.marveltime.data;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.digitalhouse.marveltime.model.Data;
import br.digitalhouse.marveltime.model.PersonagemImagem;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;

@Database(entities = {PersonagemResponse.class, PersonagemResult.class, PersonagemImagem.class, Data.class}, version = 1, exportSchema = false)
@TypeConverters(PersonagemTypeConverter.class)

public abstract class PersonagemDataBase extends RoomDatabase {

    private static volatile PersonagemDataBase INSTANCE;
    public abstract PersonagemDAO personagemDAO();

    public static PersonagemDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (PersonagemDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, PersonagemDataBase.class, "personagem_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

