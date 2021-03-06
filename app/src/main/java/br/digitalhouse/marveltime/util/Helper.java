package br.digitalhouse.marveltime.util;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.facebook.login.LoginManager;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.activity.LoginActivity;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_APP;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_UIID;

public class Helper {
    public static boolean usuarioValido(String usuario) {
        return (usuario.contains("@") && usuario.contains(".com"));
    }

    public static boolean isEmptyString(String valor) {
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

    public static boolean senhaIguais(String senha, String confirmacaoSenha) {
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

    private static void deslogarFirebase() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    public static String getString(TextInputLayout viewName) {
        return viewName.getEditText().getText().toString();
    }

    public static String getIdUsuario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CHAVE_APP, Context.MODE_PRIVATE);
        return preferences.getString(CHAVE_UIID, "");
    }

    public static void salvarIdUsuario(Context context, String uiid) {
        SharedPreferences preferences = context.getSharedPreferences(CHAVE_APP, Context.MODE_PRIVATE);
        preferences.edit().putString(CHAVE_UIID, uiid).apply();
    }

    public static String buscaChaveQuiz(int n) {
        String sChave = "";
        switch (n) {
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

    public static GoogleSignInClient google(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, gso);
        return googleSignInClient;
    }

    public static void logout(Context context){
        GoogleSignInClient googleSignInClient = google(context);
        googleSignInClient.signOut().addOnCompleteListener(task -> {
        });
        Helper.deslogarFirebase();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void notificacao(Context contexto, String sMensagem) {
        Toast toast = Toast.makeText(contexto, sMensagem, Toast.LENGTH_LONG);
        toast.show();
    }
}