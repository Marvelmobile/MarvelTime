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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;

import static android.widget.Toast.LENGTH_LONG;
import static br.digitalhouse.marveltime.util.Constantes.Activity_UM_DOIS;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;

public class LoginActivity extends AppCompatActivity {
    static final int GOOGLE_SIGN = 123;
    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private Button bntLogin;
    private FloatingActionButton btngoogle;
    private TextView loginRegistro;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        linkCadastroUsuario();
        google();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };
        bntLogin.setOnClickListener(v -> {
            if (validaCampos()) {
                firebaseAuth.signInWithEmailAndPassword(Helper.getString(loginUsuario),
                        Helper.getString(loginSenha)).addOnCompleteListener(LoginActivity.this,
                        task -> {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        });
            }
        });
        loginRegistro.setOnClickListener(v -> startActivityForResult(new Intent(LoginActivity.this, CadastroActivity.class), 1));
    }

    private void google() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btngoogle.setOnClickListener(v -> SignInGoogle());
    }

    void SignInGoogle() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity_UM_DOIS) {
            if (resultCode == RESULT_OK) {
                loginUsuario.getEditText().setText(data.getStringExtra(CHAVE_EMAIL));
            }
        }
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                    } else {
                        notificacao("Falha ao tentar logar com o Google");
                    }
                });
    }


    private boolean validaCampos() {
        if (Helper.isEmptyString(Helper.getString(loginUsuario)) || Helper.isEmptyString(Helper.getString(loginSenha)))
            notificacao(getString(R.string.preencher_campos));
        else if (!Helper.usuarioValido(Helper.getString(loginUsuario)) || !Helper.senhaValida(Helper.getString(loginSenha)))
            notificacao(getString(R.string.user_senha_fora_regra));
        else
            return true;

        return false;
    }

    private void linkCadastroUsuario() {
        String msgRegistroUsuario = getString(R.string.registre_se);
        int i1 = msgRegistroUsuario.indexOf("[");
        int i2 = msgRegistroUsuario.indexOf("]");

        loginRegistro.setMovementMethod(LinkMovementMethod.getInstance());
        loginRegistro.setText(msgRegistroUsuario, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) loginRegistro.getText();

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View widget) {
            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void initViews() {
        loginUsuario = findViewById(R.id.loginUsuario);
        loginSenha = findViewById(R.id.loginSenha);
        bntLogin = findViewById(R.id.btnLogin);
        loginRegistro = findViewById(R.id.loginRegistro);
        btngoogle = findViewById(R.id.loginGoogle);
    }

    private void notificacao(String sMensagem) {
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText(contexto, sMensagem, LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}