package br.digitalhouse.marveltime.repository;
import android.util.Log;

import java.net.URL;

import br.digitalhouse.marveltime.model.PersonagemResponse;
import io.reactivex.Single;
import static br.digitalhouse.marveltime.network.RetrofitService.getApiService;
import static br.digitalhouse.marveltime.util.Constantes.BASE_URL;
import static br.digitalhouse.marveltime.util.Constantes.PRIVATE_KEY;
import static br.digitalhouse.marveltime.util.Constantes.PUBLIC_KEY;
import static br.digitalhouse.marveltime.util.Helper.md5;

public class MarvelRepository {
    String ts = Long.toString (System.currentTimeMillis()/1000 );
    String hash = md5 (ts + PRIVATE_KEY + PUBLIC_KEY );


    public Single<PersonagemResponse> getPersonagem(){
        Log.i("TS",ts);
        Log.i("PUBLIC_KEY",PUBLIC_KEY);
        Log.i("PRIVATE_KEY", PRIVATE_KEY);
        Log.i("HASH", hash);
        Log.i("REQUEST API", BASE_URL+"characters?"+"&ts="+ts+"&hash="+hash+"&apikey="+PUBLIC_KEY);
        return getApiService().getPersonagens(ts, hash, PUBLIC_KEY);
    }
}
