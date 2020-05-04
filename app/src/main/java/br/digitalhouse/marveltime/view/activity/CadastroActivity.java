package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;
import br.digitalhouse.marveltime.viewmodel.FirebaseViewModel;

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
        getWindow().setBackgroundDrawableResource(R.drawable.background_marvel);
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
                        if (!task.isSuccessful())
                            Helper.notificacao(CadastroActivity.this, task.getException().getMessage());
                        else {
                            viewModel.salvarNomeFirebase(Helper.getString(cadastrarNome));
                            CadastroActivity.this.irParaHome(mFirebaseAuth.getCurrentUser().getUid());
                        }
                    });
        }
    }

    private void irParaHome(String uiid) {
        Helper.salvarIdUsuario(getApplicationContext(), uiid);
        startActivity(new Intent(CadastroActivity.this, MainActivity.class));
    }

    private void initViews() {
        cadastrarNome = findViewById(R.id.cadastrarNome);
        cadastrarEmail = findViewById(R.id.cadastrarEmail);
        cadastrarSenha = findViewById(R.id.cadastrarSenha);
        cadastrarSenhaConfirmacao = findViewById(R.id.cadastrarSenhaConfirmacao);
        bntCadastrar = findViewById(R.id.bntCadastrar);
        viewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
    }

    private boolean validaCampos(String nomeCompleto, String email, String senha, String senhaConfirmacao) {
        if (Helper.isEmptyString(nomeCompleto) || Helper.isEmptyString(email)
                || Helper.isEmptyString(senha) || Helper.isEmptyString(senhaConfirmacao))
            Helper.notificacao(this, getString(R.string.preencher_campos));
        else if (!Helper.nomeValido(nomeCompleto))
            Helper.notificacao(this, getString(R.string.preencher_nome));
        else if (!Helper.usuarioValido(email))
            Helper.notificacao(this, getString(R.string.inserir_email));
        else if (Helper.senhaValida(senha)) {
            if (!Helper.senhaIguais(senha, senhaConfirmacao))
                Helper.notificacao(this, getString(R.string.confirmar_senhas_iguais));
            else
                return true;
        } else Helper.notificacao(this, getString(R.string.regra_senha));
        return false;
    }
}