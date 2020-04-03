package br.digitalhouse.marveltime.repository;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.util.Helper;
import io.reactivex.Single;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.PRIVATE_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Constantes.TS;

public class MarvelRepository {
    private String hash = Helper.md5(TS + PRIVATE_KEY + PUBLIC_KEY );

    public Single<PersonagemResponse> getPersonagem(){
        return getApiService().getPersonagens( TS, hash, PUBLIC_KEY);
    }
}
