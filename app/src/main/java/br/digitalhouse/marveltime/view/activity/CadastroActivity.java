package br.digitalhouse.marveltime.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
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

import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;

import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;

public class CadastroActivity extends AppCompatActivity {
    static final int GOOGLE_SIGN = 123;
    private TextInputLayout cadastrarNome;
    private TextInputLayout cadastrarEmail;
    private TextInputLayout cadastrarSenha;
    private TextInputLayout cadastrarSenhaConfirmacao;
    private Button bntCadastrar;
    private FloatingActionButton btngoogle;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initViews();
        google();
        mFirebaseAuth = FirebaseAuth.getInstance();
        bntCadastrar.setOnClickListener(v -> cadastraNovoUsuario());
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

    private void cadastraNovoUsuario() {

        if (validaCampos(Helper.getString(cadastrarNome), Helper.getString(cadastrarEmail),
                Helper.getString(cadastrarSenha), Helper.getString(cadastrarSenhaConfirmacao))) {
            mFirebaseAuth.createUserWithEmailAndPassword(Helper.getString(cadastrarEmail),
                    Helper.getString(cadastrarSenhaConfirmacao))
                    .addOnCompleteListener(CadastroActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            voltarTelaLoginActivity(Helper.getString(cadastrarEmail));
                        }
                    });
        }
    }

    private void voltarTelaLoginActivity(String email) {
        if (!email.isEmpty()) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(CHAVE_EMAIL, email);
            setResult(RESULT_OK, returnIntent);
        }
        finish();
    }

    private void initViews() {
        cadastrarNome = findViewById(R.id.cadastrarNome);
        cadastrarEmail = findViewById(R.id.cadastrarEmail);
        cadastrarSenha = findViewById(R.id.cadastrarSenha);
        cadastrarSenhaConfirmacao = findViewById(R.id.cadastrarSenhaConfirmacao);
        bntCadastrar = findViewById(R.id.bntCadastrar);
        btngoogle = findViewById(R.id.loginGoogle);
    }

    private void notificacao(String sMensagem) {
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText(contexto, sMensagem, Toast.LENGTH_LONG);
        toast.show();
    }

    private boolean validaCampos(String nomeCompleto, String email, String senha, String senhaConfirmacao) {
        if (Helper.isEmptyString(nomeCompleto) || Helper.isEmptyString(email)
                || Helper.isEmptyString(senha) || Helper.isEmptyString(senhaConfirmacao))
            notificacao(getString(R.string.preencher_campos));
        else if (!Helper.nomeValido(nomeCompleto))
            notificacao(getString(R.string.preencher_nome));
        else if (!Helper.usuarioValido(email))
            notificacao(getString(R.string.inserir_email));
        else if (Helper.senhaValida(senha)) {
            if (!Helper.senhaIguais(senha, senhaConfirmacao))
                notificacao(getString(R.string.confirmar_senhas_iguais));
            else
                return true;
        } else
            notificacao(getString(R.string.regra_senha));
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        firebaseUser = mFirebaseAuth.getCurrentUser();
                    } else {
                        notificacao("Falha ao tentar logar com o Google");
                    }
                });
    }
}