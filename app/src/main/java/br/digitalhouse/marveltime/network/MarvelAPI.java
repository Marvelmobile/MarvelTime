package br.digitalhouse.marveltime.network;

import br.digitalhouse.marveltime.model.PersonagemResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {

    @GET( "comics?" )
    Single<PersonagemResponse> getPersonagens(
            @Query( "format" ) String format,
            @Query ( "formatType" ) String formatType,
            @Query ( "noVariants" ) boolean noVariants,
            @Query ( "orderBy" ) String orderBy,
            @Query ( "ts" ) String ts,
            @Query ( "hash" ) String hash,
            @Query ( "apikey" ) String apikey);
}
