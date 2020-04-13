package br.digitalhouse.marveltime.repository;
import android.content.Context;
import java.util.List;
import br.digitalhouse.marveltime.data.PersonagemDataBase;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.HASH;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Constantes.TS;

public class MarvelRepository {
    public Flowable<List<PersonagemResult>> retornaPersonagemBD(Context context, Integer offset) {
        return PersonagemDataBase.getDatabase(context).personagemDAO().recuperaPersonagemDoBD(offset);
    }

    public void inserePersonagemResponseBd(List<PersonagemResult> personagemList, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().insereListaBD(personagemList);
    }

    public void apagaOsDadosBD(PersonagemResponse personagemResponse, Context context) {
        PersonagemDataBase.getDatabase(context).personagemDAO().apagaDadosBd(personagemResponse);
    }

    public Observable<PersonagemResponse> getPersonagem(Integer offset){
        return getApiService().getPersonagens(TS, HASH, PUBLIC_KEY, offset);
    }
}