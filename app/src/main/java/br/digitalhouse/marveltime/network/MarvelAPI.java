package br.digitalhouse.marveltime.network;

import br.digitalhouse.marveltime.model.PersonagemResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {

    @GET( "comics?" )
    Observable<PersonagemResponse> getPersonagens(
            @Query ( "orderBy" ) String orderBy,
            @Query ( "ts" ) String ts,
            @Query ( "hash" ) String hash,
            @Query ( "apikey" ) String apikey,
            @Query ( "offset" ) Integer offset);
}
