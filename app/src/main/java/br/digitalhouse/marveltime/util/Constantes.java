package br.digitalhouse.marveltime.util;
import static br.digitalhouse.marveltime.util.Helper.md5;

public class Constantes {
    public static final String CHAVE_EMAIL = "email";
    public static final int Activity_UM_DOIS = 1;
    public static final String BASE_URL = "https://gateway.marvel.com/v1/public/";
    public static final String PUBLIC_KEY = "6eb7e8896ec5850c52515a8a23ee97f0";
    public static final String PRIVATE_KEY = "0dd0c16fedb8a02985977eafca66b49f5e6a526f";
    public static final String PERSONAGEM_KEY = "personagem";
    public static final String TS = Long.toString (System.currentTimeMillis()/1000);
    public static final String HASH = md5 (TS + PRIVATE_KEY + PUBLIC_KEY );
    public static final String CHAVE_NOME = "NOME";
    public static final String UTF8 = "UTF-8";
    public static final String IMAGEM_KEY = "detalheImagem";
    public static final int PERMISSION_CODE = 100;
    public static final String CHAVE_APP = "CHAVE_APP";
    public static final String CHAVE_UIID = "UIID";
    public static final String CHAVE_IMG_PROFILE = "/image/profile/imagem-perfil";
 }