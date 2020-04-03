package br.digitalhouse.marveltime.repository;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import io.reactivex.Single;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.PRIVATE_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Helper.md5;

public class MarvelRepository {
    String ts = Long.toString (System.currentTimeMillis()/1000 );
    String hash = md5 (ts + PRIVATE_KEY + PUBLIC_KEY );

    public Single<PersonagemResponse> getPersonagem(){
        return getApiService().getPersonagens(ts, hash, PUBLIC_KEY);
    }
}
