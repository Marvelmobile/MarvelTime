package br.digitalhouse.marveltime;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerguntasQuizFragment extends Fragment {

    private TextView fragment_titulo;
    private ImageView fragmentBackgroundPergunta;
    private TextView fragmentPergunta;
    private Button fragmentAlternativaUm;
    private Button fragmentAlternativaDois;
    private Button fragmentAlternativaTres;
    private Button fragmentAlternativaQuatro;
    private FloatingActionButton fragmentVoltar;
    private FloatingActionButton fragmentProximo;
    private Selecionavel selecionavel;
    private int corRespostaCorreta = 0x8005850C;
    private int corRespostaErrada = 0xD09C300E;

    public PerguntasQuizFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_perguntas_quiz, container, false);
        initViews(v);

        fragmentProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionavel.selecionar(R.id.fragment_layout_quiz_outra);
            }
        });

        fragmentVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacaoParaTelaAnterior();
            }
        });

        fragmentAlternativaUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAlternativaUm.setTextColor(corRespostaErrada);
            }
        });

        fragmentAlternativaDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAlternativaDois.setTextColor(corRespostaErrada);
            }
        });

        fragmentAlternativaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAlternativaTres.setTextColor(corRespostaCorreta);
            }
        });

        fragmentAlternativaQuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAlternativaQuatro.setTextColor(corRespostaErrada);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.selecionavel = (Selecionavel) context;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews (View view){
        fragment_titulo = view.findViewById(R.id.fragment_titulo_principal);
        fragmentBackgroundPergunta = view.findViewById(R.id.fragment_background_imageview_perguntas);
        fragmentPergunta = view.findViewById(R.id.fragement_textview_pergunta);
        fragmentAlternativaUm = view.findViewById(R.id.fragment_button_alternativaUm);
        fragmentAlternativaDois = view.findViewById(R.id.fragment_button_alternativaDois);
        fragmentAlternativaTres = view.findViewById(R.id.fragment_button_alternativaTres);
        fragmentAlternativaQuatro = view.findViewById(R.id.fragment_button_alternativaQuatro);
        fragmentVoltar = view.findViewById(R.id.fragment_floatingActionButton_voltar);
        fragmentProximo = view.findViewById(R.id.fragment_floatingActionButton_proximo);
    }

    protected void notificacaoParaTelaAnterior (){
        Context contexto = getContext();
        String textoNotificacao = "Ainda estamos trabalhando no link com a tela anterior, por favor, aguarde o desenvolvimento";
        int duracaoNotifacao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }
}
