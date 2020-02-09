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
                String nomeCompleto = cadastrarNome.getEditText().getText().toString();
                String email = cadastrarEmail.getEditText().getText().toString();
                String senha = cadastrarSenha.getEditText().getText().toString();
                String senhaConfirmacao = cadastrarSenhaConfirmacao.getEditText().getText().toString();

                if (ehVazio(nomeCompleto) || ehVazio(email) || ehVazio(senha) || ehVazio(senhaConfirmacao))
                    notificacao();
                else if (!nomeValido(nomeCompleto))
                    notificacaoNomeCompleto();
                else if (!emailValido(email))
                    notificacaoEmailInvalido();
                else if (senhaValida(senha)) {
                    if (!senhaIguais(senha, senhaConfirmacao))
                        notificacaoSenhaDiferente();
                    else{
                        notificacaoParaProximaTela("Cadastro Realizado com Sucesso!");

                        voltarTelaLoginActivity(email);
                    }
                 } else
                    notificacaoSenhaInvalida();
            }


        });
    }

    //ADELANIA SANTOS - 09/02/20 - BDEV06 - LINK TELAS
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

    //INGHRIDY SANTOS  - 05/02/20 - BDEV02 - VALIDAÇÃO USUARIO
    private boolean nomeValido(String nomeCompleto) {

        if (nomeCompleto.contains(" "))
            return true;
        else
            return false;
    }

    private boolean emailValido(String email) {
        if (email.contains("@") && email.contains(".com"))
            return true;
        else
            return false;
    }
    //INGHRIDY SANTOS  - 05/02/20 - BDEV03 - VALIDAÇÃO SENHA
    private boolean senhaValida(String senha) {
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

    private boolean senhaIguais(String senha, String confirmacaoSenha) {
        if (confirmacaoSenha.equals(senha))
            return true;
        else
            return false;
    }

    private boolean ehVazio(String valorComparacao) {
        if (valorComparacao.isEmpty())
            return true;
        else
            return false;
    }


    //INGHRIDY SANTOS  - 05/02/20 - BDEV02 - NOTIFICAÇÕES
    protected void notificacao() {
        Context contexto = getApplicationContext();
        String textoNotificacao = "Por favor, preencha todos os campos";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoNomeCompleto() {
        Context contexto = getApplicationContext();
        String textoNotificacao = "Por favor, preencha o nome completo para cadastro no Marvel Time";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoSenhaInvalida() {
        Context contexto = getApplicationContext();
        String textoNotificacao = "A senha deve conter pelo menos 6 caracteres, uma letra maiuscula e minuscula, um número e um caracter especial.";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoSenhaDiferente() {
        Context contexto = getApplicationContext();
        String textoNotificacao = "Os dados inseridos nos campos Senha e Confirme sua senha, devem ser iguais. Tente novamente.";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoParaProximaTela(String mensagem) {
        Context contexto = getApplicationContext();
        String textoNotificacao = mensagem;
        int duracaoNotifacao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoEmailInvalido() {
        Context contexto = getApplicationContext();
        String textoNotificacao = "Por favor, insira um e-mail válido";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }
}
