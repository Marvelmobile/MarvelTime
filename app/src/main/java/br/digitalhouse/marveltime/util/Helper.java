package br.digitalhouse.marveltime.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static boolean verificaConexaoComInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected() &&
                    (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                            || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        }
        return false;
    }

    private static char[] HEXCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String hexEncode(byte[] bytes) {
        char[] result = new char[bytes.length * 2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++) {
            b = bytes[i] & 0xff;
            result[j++] = HEXCHARS[b >> 4];
            result[j++] = HEXCHARS[b & 0xf];
        }
        return new String(result);
    }

    public static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            return hexEncode(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String buscaChaveQuiz(int n) {
        String sChave = "";
        switch (n){
            case R.string.quiz_homem_aranha:
                sChave = "HA";
                break;
            case R.string.quiz_thor:
                sChave = "TH";
                break;
            case R.string.quiz_homem_ferro:
                sChave = "HF";
                break;
            case R.string.quiz_capitao:
                sChave = "CA";
                break;
        }
        return sChave;
     }
}