package br.digitalhouse.marveltime.repository;
import android.content.Context;
import java.util.List;
import br.digitalhouse.marveltime.data.PersonagemDataBase;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import io.reactivex.Observable;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.PRIVATE_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Helper.md5;

public class MarvelRepository {
    String ts = Long.toString (System.currentTimeMillis()/1000 );
    String hash = md5 (ts + PRIVATE_KEY + PUBLIC_KEY );
  
    public Observable<List<PersonagemResult>> retornaPersonagemBD(Context context) {
        return PersonagemDataBase.getDatabase(context).personagemDAO().recuperaPersonagemDoBD();
    }

    public void inserePersonagemResponseBd(List<PersonagemResult> personagemList, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().insereListaBD(personagemList);
    }

    public void apagaOsDadosBD(PersonagemResponse personagemResponse, Context context) {
        PersonagemDataBase.getDatabase(context).personagemDAO().apagaDadosBd(personagemResponse);
    }

    public Observable<PersonagemResponse> getPersonagem(Integer offset){
        return getApiService().getPersonagens(ts, hash, PUBLIC_KEY, offset);
    }
}