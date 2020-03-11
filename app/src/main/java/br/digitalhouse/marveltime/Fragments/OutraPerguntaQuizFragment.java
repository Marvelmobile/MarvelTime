package br.digitalhouse.marveltime.Fragments;
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

import br.digitalhouse.marveltime.Interfaces.Selecionavel;
import br.digitalhouse.marveltime.R;

public class OutraPerguntaQuizFragment extends Fragment {

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

    public OutraPerguntaQuizFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_outra_pergunta_quiz, container, false);

        initViews(v);

        fragmentProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacaoParaProximaTela();
            }
        });

        fragmentVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionavel.selecionar(R.id.fragment_layout_quiz);
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
                fragmentAlternativaDois.setTextColor(corRespostaCorreta);
            }
        });

        fragmentAlternativaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAlternativaTres.setTextColor(corRespostaErrada);
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
        fragment_titulo = view.findViewById(R.id.fragment_titulo_principal_outra);
        fragmentBackgroundPergunta = view.findViewById(R.id.fragment_background_imageview_perguntas_outra);
        fragmentPergunta = view.findViewById(R.id.fragement_textview_pergunta_outra);
        fragmentAlternativaUm = view.findViewById(R.id.fragment_button_alternativaUm_outra);
        fragmentAlternativaDois = view.findViewById(R.id.fragment_button_alternativaDois_outra);
        fragmentAlternativaTres = view.findViewById(R.id.fragment_button_alternativaTres_outra);
        fragmentAlternativaQuatro = view.findViewById(R.id.fragment_button_alternativaQuatro_outra);
        fragmentVoltar = view.findViewById(R.id.fragment_floatingActionButton_voltar_outra);
        fragmentProximo = view.findViewById(R.id.fragment_floatingActionButton_proximo_outra);
    }

    protected void notificacaoParaProximaTela (){
        Context contexto = getContext();
        String textoNotificacao = "Ainda não existe próxima tela, por favor, aguardar o desenvolvimento.";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }
}
