package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import br.digitalhouse.marveltime.viewmodel.FirebaseViewModel;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_EMAIL;

public class CadastroActivity extends AppCompatActivity {
    private TextInputLayout cadastrarNome;
    private TextInputLayout cadastrarEmail;
    private TextInputLayout cadastrarSenha;
    private TextInputLayout cadastrarSenhaConfirmacao;
    private Button bntCadastrar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initViews();
        mFirebaseAuth = FirebaseAuth.getInstance();
        bntCadastrar.setOnClickListener(v -> cadastraNovoUsuario());
    }

    private void cadastraNovoUsuario() {

        if(validaCampos(Helper.getString(cadastrarNome), Helper.getString(cadastrarEmail),
                Helper.getString(cadastrarSenha), Helper.getString(cadastrarSenhaConfirmacao))){
            mFirebaseAuth.createUserWithEmailAndPassword(Helper.getString(cadastrarEmail),
                    Helper.getString(cadastrarSenhaConfirmacao))
                    .addOnCompleteListener(CadastroActivity.this, task -> {
                                if (!task.isSuccessful()){
                                    Toast.makeText(CadastroActivity.this,
                                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    viewModel.salvarNomeFirebase(Helper.getString(cadastrarNome));
                                    voltarTelaLoginActivity(Helper.getString(cadastrarEmail));
                                }
                            });
        }
    }

    private void voltarTelaLoginActivity(String email) {
        if(!email.isEmpty()) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(CHAVE_EMAIL,email);
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
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
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
}