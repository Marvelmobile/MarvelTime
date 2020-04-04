package br.digitalhouse.marveltime.repository;
import android.content.Context;
import java.util.List;
import br.digitalhouse.marveltime.data.PersonagemDataBase;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.network.RetrofitService;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MarvelRepository {
    public Flowable<List<PersonagemResult>> retornaPersonagemBD(Context context) {
        return PersonagemDataBase.getDatabase(context).personagemDAO().recuperaPersonagemDoBD();
    }

    public void inserePersonagemResponseBd(List<PersonagemResult> personagemList, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().insereListaBD(personagemList);
    }

    public void apagaOsDadosBD(PersonagemResponse personagemResponse, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().apagaDadosBd(personagemResponse);
    }
}

