package br.digitalhouse.marveltime.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import io.reactivex.Observable;

@Dao
public interface PersonagemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insereListaBD(List<PersonagemResult> listaPersonagemResult);

    @Query("SELECT * FROM result")
    Observable<List<PersonagemResult>> recuperaPersonagemDoBD();

    @Delete
    void apagaDadosBd(PersonagemResponse personagemResponse);
}
