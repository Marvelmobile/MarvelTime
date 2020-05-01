package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import static br.digitalhouse.marveltime.util.Constantes.Activity_UM_DOIS;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private Button bntLogin;
    private TextView loginRegistro;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mFirebaseAuth;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private FloatingActionButton btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        linkCadastroUsuario();
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };

        bntLogin.setOnClickListener(v -> {
            if(validaCampos()){
                firebaseAuth.signInWithEmailAndPassword(Helper.getString(loginUsuario),
                        Helper.getString(loginSenha)).addOnCompleteListener(LoginActivity.this,
                        task -> {
                    if (!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });
            }
        });

        loginRegistro.setOnClickListener(v -> startActivityForResult(new Intent(LoginActivity.this, CadastroActivity.class),1));

        callbackManager = CallbackManager.Factory.create();

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        btnFacebook.setOnClickListener(click -> {
            loginFacebook();
        });
    }

    private boolean validaCampos(){
        if (Helper.isEmptyString(Helper.getString(loginUsuario)) || Helper.isEmptyString(Helper.getString(loginSenha)))
            notificacao(getString(R.string.preencher_campos));
        else if (!Helper.usuarioValido(Helper.getString(loginUsuario)) || !Helper.senhaValida(Helper.getString(loginSenha)))
            notificacao(getString(R.string.user_senha_fora_regra));
        else
            return true;

        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity_UM_DOIS) {
            if (resultCode == RESULT_OK) {
                loginUsuario.getEditText().setText(data.getStringExtra(CHAVE_EMAIL));
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void linkCadastroUsuario() {
        String msgRegistroUsuario = getString(R.string.registre_se);
        int i1 = msgRegistroUsuario.indexOf("[");
        int i2 = msgRegistroUsuario.indexOf("]");

        loginRegistro.setMovementMethod(LinkMovementMethod.getInstance());
        loginRegistro.setText(msgRegistroUsuario, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)loginRegistro.getText();

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View widget) { }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void initViews (){
        loginUsuario = findViewById(R.id.loginUsuario);
        loginSenha = findViewById(R.id.loginSenha);
        bntLogin = findViewById(R.id.btnLogin);
        loginRegistro = findViewById(R.id.loginRegistro);
        btnFacebook = findViewById(R.id.loginFacebook);
    }

    private void notificacao (String sMensagem){
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText(contexto, sMensagem, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    
    private void irParaMain(String uiid) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider
                        .getCredential(loginResult.getAccessToken().getToken());
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(task -> {
                            irParaMain(loginResult.getAccessToken().getUserId());
                        });
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
