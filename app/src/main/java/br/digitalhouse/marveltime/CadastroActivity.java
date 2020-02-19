package br.digitalhouse.marveltime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class CadastroActivity extends AppCompatActivity {
    public static final String CHAVE_EMAIL = "EMAIL";
    private TextInputLayout cadastrarNome;
    private TextInputLayout cadastrarEmail;
    private TextInputLayout cadastrarSenha;
    private TextInputLayout cadastrarSenhaConfirmacao;
    private FloatingActionButton bntCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initViews();

        bntCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastraNovoUsuario();
            }
        });
    }

    private void cadastraNovoUsuario() {
        String nomeCompleto = cadastrarNome.getEditText().getText().toString();
        String email = cadastrarEmail.getEditText().getText().toString();
        String senha = cadastrarSenha.getEditText().getText().toString();
        String senhaConfirmacao = cadastrarSenhaConfirmacao.getEditText().getText().toString();

        if(validaCampos(nomeCompleto, email, senha, senhaConfirmacao)){
            notificacao("Cadastro Realizado com Sucesso!");
            voltarTelaLoginActivity(email);
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
    }

    protected void notificacao(String sMensagem) {
        Context contexto = getApplicationContext();
        String textoNotificacao = sMensagem;
        int duracaoNotifacao = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    private boolean validaCampos(String nomeCompleto, String email, String senha, String senhaConfirmacao) {
        if (Helper.isEmptyString(nomeCompleto) || Helper.isEmptyString(email)
                || Helper.isEmptyString(senha) || Helper.isEmptyString(senhaConfirmacao))
            notificacao("Por favor, preencha todos os campos");
        else if (!Helper.nomeValido(nomeCompleto))
            notificacao("Por favor, preencha o nome completo para cadastro no Marvel Time");
        else if (!Helper.usuarioValido(email))
            notificacao("Por favor, insira um e-mail válido");
        else if (Helper.senhaValida(senha)) {
            if (!Helper.senhaIguais(senha, senhaConfirmacao))
                notificacao("Os dados inseridos nos campos Senha e Confirme sua senha, devem ser iguais. Tente novamente.");
            else
                return true;
        } else
            notificacao("A senha deve conter pelo menos 6 caracteres, uma letra maiuscula e minuscula, um número e um caracter especial.");

        return false;
    }
}