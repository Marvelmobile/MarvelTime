package br.digitalhouse.marveltime.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.digitalhouse.marveltime.R;

public class Helper {
    public static boolean usuarioValido (String usuario){
        return (usuario.contains("@") && usuario.contains(".com") );
    }

    public static boolean isEmptyString (String valor){
        return valor == null || valor.trim().equals("");
    }

    public static boolean senhaValida(String senha) {
        if (senha.length() < 6)
            return false;

        boolean achouNumero = false;
        boolean achouMaiuscula = false;
        boolean achouMinuscula = false;
        boolean achouSimbolo = false;

        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9')
                achouNumero = true;
            else if (c >= 'A' && c <= 'Z')
                achouMaiuscula = true;
            else if (c >= 'a' && c <= 'z')
                achouMinuscula = true;
            else
                achouSimbolo = true;
        }
        return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
    }

    public static boolean nomeValido(String nomeCompleto) {
        return (nomeCompleto.contains(" "));
    }

    public static boolean senhaIguais(String senha, String confirmacaoSenha)
    {
        return (confirmacaoSenha.equals(senha));
    }

    public static boolean verificaConexaoComInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        if (connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected() &&
                    (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                            || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        }
        return false;
    }

    public static String md5(String s) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(s.getBytes()));
        sen = hash.toString(16);
        return sen;
    }

}
