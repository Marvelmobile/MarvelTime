package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import br.digitalhouse.marveltime.AppUtil;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import static br.digitalhouse.marveltime.util.Constantes.Activity_UM_DOIS;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private FloatingActionButton bntLogin;
    private TextView loginRegistro;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mFirebaseAuth;
    private LoginButton loginButton;
//    private FirebaseAuth mAuth;
    //private static final String TAG = "FACELOG";
    //private AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initViews();
//        linkCadastroUsuario();

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());


        loginButton.setOnClickListener(click -> {
            loginFacebook();
        });

    }
    private void irParaMain(String uiid) {
        AppUtil.salvarIdUsuario(getApplication().getApplicationContext(), uiid);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

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




        // Initialize Firebase Auth
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        FacebookSdk.sdkInitialize(getApplicationContext());
////        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//            }
//        });
//
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null){
//                    updateUI(); //(user)
//                }
//                else{
//                    updateUI(); //(null)
//                }
//            }
//        };
//
//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null){
//                    mFirebaseAuth.signOut();
//                }
//            }
//        };

//        bntLogin.setOnClickListener(v -> {
//            if(validaCampos()){
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            }
//        });

//        loginRegistro.setOnClickListener(v -> startActivityForResult(new Intent(LoginActivity.this, CadastroActivity.class),1));



      //final do Oncreate }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
//        if (currentUser != null){
//
//            updateUI();
//        }
//        mFirebaseAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null){
//            mFirebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//
//    private void updateUI() {
//        Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mFirebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithCredential:success");
//                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
//                        updateUI(); //(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithCredential:failure", task.getException());
//                        Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                        updateUI(); //(null)
//                    }
//                });
//    }
//
//}

    //////////////////////////////////////////////////////////////////

//    private boolean validaCampos(){
//        String usuario = loginUsuario.getEditText().getText().toString();
//        String senha = loginSenha.getEditText().getText().toString();
//
//        if (Helper.isEmptyString(usuario) || Helper.isEmptyString(senha))
//            notificacao(getString(R.string.preencher_campos));
//        else if (!Helper.usuarioValido(usuario) || !Helper.senhaValida(senha))
//            notificacao( getString(R.string.user_senha_fora_regra));
//        else
//            return true;
//
//        return false;
//    }
    //////////////////////////////////////////////////////////////////////

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Activity_UM_DOIS) {
//            if (resultCode == RESULT_OK) {
//                loginUsuario.getEditText().setText(data.getStringExtra(CHAVE_EMAIL));
//            }
//        }
//    }

    //////////////////////////////////////////////////////////////////////////////
//    private void linkCadastroUsuario() {
//        String msgRegistroUsuario = getString(R.string.registre_se);
//        int i1 = msgRegistroUsuario.indexOf("[");
//        int i2 = msgRegistroUsuario.indexOf("]");
//
//        loginRegistro.setMovementMethod(LinkMovementMethod.getInstance());
//        loginRegistro.setText(msgRegistroUsuario, TextView.BufferType.SPANNABLE);
//        Spannable mySpannable = (Spannable)loginRegistro.getText();
//
//        ClickableSpan myClickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) { }
//        };
//        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//    }

//    private void initViews() {
//        loginUsuario = findViewById(R.id.loginUsuario);
//        loginSenha = findViewById(R.id.loginSenha);
//        bntLogin = findViewById(R.id.btnLogin);
//        loginRegistro = findViewById(R.id.loginRegistro);
//    }
//}         //final appcompat




//    private void notificacao (String sMensagem){
//        Context contexto = getApplicationContext();
//        Toast toast = Toast.makeText(contexto, sMensagem, Toast.LENGTH_LONG);
//        toast.show();
//    }


//}   // esse é o colchete que liga o AppCampatActivity// coloquei embaixo do initViews
