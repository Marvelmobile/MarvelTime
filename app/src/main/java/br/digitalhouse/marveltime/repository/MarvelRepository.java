package br.digitalhouse.marveltime.repository;

import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.util.Helper;
import io.reactivex.Observable;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.PRIVATE_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Constantes.TS;

public class MarvelRepository {
    String hash = Helper.md5(TS + PRIVATE_KEY + PUBLIC_KEY );

    public Observable<PersonagemResponse> getPersonagem(Integer offset){
        return getApiService().getPersonagens("name", TS, hash, PUBLIC_KEY, offset);
    }
}
