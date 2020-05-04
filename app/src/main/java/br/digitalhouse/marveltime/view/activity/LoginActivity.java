package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;
import static br.digitalhouse.marveltime.util.Constantes.GOOGLE_SIGN;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private Button bntLogin;
    private FloatingActionButton btngoogle;
    private TextView loginRegistro;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;
    private FloatingActionButton btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.background_marvel);
        initViews();
        linkCadastroUsuario();
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null){
                irParaHome(firebaseAuth.getCurrentUser().getUid());
            }
        };

        bntLogin.setOnClickListener(v -> LoginFirebase());
        btngoogle.setOnClickListener(v -> LoginGoogle());
        btnFacebook.setOnClickListener(click -> loginFacebook());
        loginRegistro.setOnClickListener(v -> startActivityForResult(new Intent(LoginActivity.this, CadastroActivity.class),1));
    }

    private void LoginFirebase() {
        if (validaCampos()) firebaseAuth.signInWithEmailAndPassword(Helper.getString(loginUsuario),
                Helper.getString(loginSenha)).addOnCompleteListener(this,
                task -> {
                    if (!task.isSuccessful())
                        Helper.notificacao(this, task.getException().getMessage());
                    else irParaHome(firebaseAuth.getCurrentUser().getUid());
                });
    }

    void LoginGoogle() {
        GoogleSignInClient mGoogleSignInClient = Helper.google(this);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
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
                    if (task.isSuccessful()) irParaHome(firebaseAuth.getCurrentUser().getUid());
                    else Helper.notificacao(this, getString(R.string.falhagoogle));
                });
    }

    private boolean validaCampos() {
        if (Helper.isEmptyString(Helper.getString(loginUsuario)) || Helper.isEmptyString(Helper.getString(loginSenha))) {
            Helper.notificacao(this, getString(R.string.preencher_campos));
            return false;
        }
        return true;
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
        btnFacebook = findViewById(R.id.loginFacebook);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(CHAVE_EMAIL,  getString(R.string.perm_facebook)));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> irParaHome(loginResult.getAccessToken().getUserId()));
                    }

                    @Override
                    public void onCancel() {
                        Helper.notificacao(LoginActivity.this, getString(R.string.fb_cancelado));
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Helper.notificacao(LoginActivity.this, error.getMessage());
                    }
                });
    }

    private void irParaHome(String uiid) {
        Helper.salvarIdUsuario(getApplicationContext(), uiid);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}