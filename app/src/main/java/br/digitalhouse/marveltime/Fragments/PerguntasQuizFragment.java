package br.digitalhouse.marveltime.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.digitalhouse.marveltime.Activitys.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.Interfaces.HelperQuiz;
import br.digitalhouse.marveltime.Models.Questao;
import br.digitalhouse.marveltime.R;

public class PerguntasQuizFragment extends Fragment {

    private HelperQuiz helperQuiz;
    private TextView fragment_titulo;
    private TextView fragmentPergunta;
    private Button alternativaUm;
    private Button alternativaDois;
    private Button alternativaTres;
    private Button alternativaQuatro;
    private List<Questao> listaperguntas;
    private List<Questao> listaperguntasfiltrada = new ArrayList<>();
    private int perguntaAtual = 0, correto = 0, errado = 0;


    public PerguntasQuizFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_perguntas_quiz, container, false);
        initViews(v);
        carregaTodasPerguntas();
        Collections.shuffle(listaperguntas);
        filtroLista();
        colocarPerguntasTela(perguntaAtual);

        alternativaUm.setOnClickListener(v1 -> {
            if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa1().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                correto++;
                ficaVerde(alternativaUm);
            } else {
                errado++;
                ficaVermelho(alternativaUm);
            }
        });

        alternativaDois.setOnClickListener(v12 -> {
            if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa2().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                correto++;
                ficaVerde(alternativaDois);
            } else {
                errado++;
                ficaVermelho(alternativaDois);
            }
        });

        alternativaTres.setOnClickListener(v13 -> {
            if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa3().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                correto++;
                ficaVerde(alternativaTres);

            } else {
                errado++;
                ficaVermelho(alternativaTres);
            }
        });

        alternativaQuatro.setOnClickListener(v14 -> {
            if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa4().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                correto++;
                ficaVerde(alternativaQuatro);
            } else {
                errado++;
                ficaVermelho(alternativaQuatro);
            }
        });
        return v;
    }

    private void initViews(View view) {
        fragment_titulo = view.findViewById(R.id.fragment_titulo_principal);
        fragmentPergunta = view.findViewById(R.id.fragement_textview_pergunta);
        alternativaUm = view.findViewById(R.id.fragment_button_alternativaUm);
        alternativaDois = view.findViewById(R.id.fragment_button_alternativaDois);
        alternativaTres = view.findViewById(R.id.fragment_button_alternativaTres);
        alternativaQuatro = view.findViewById(R.id.fragment_button_alternativaQuatro);
    }

    private String carregaJsonDoAsset(String file) {
        String json = "";
        try {
            InputStream is = getContext().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void mudaTitulo() {
        if (RecebePerguntasQuizActivity.nome.equals("HA")) {
            fragment_titulo.setText(R.string.quiz_spider_man);
        }
        if (RecebePerguntasQuizActivity.nome.equals("HF")) {
            fragment_titulo.setText(R.string.quiz_homem_ferro);
        }
        if (RecebePerguntasQuizActivity.nome.equals("TH")) {
            fragment_titulo.setText(R.string.quiz_thor);
        }
        if (RecebePerguntasQuizActivity.nome.equals("CA")) {
            fragment_titulo.setText(R.string.quiz_capitao);
        }
    }

    private void filtroLista() {
        for (int i = 0; i < listaperguntas.size(); i++) {
            if (RecebePerguntasQuizActivity.nome.equals(listaperguntas.get(i).getNome())) {
                listaperguntasfiltrada.add(listaperguntas.get(i));
            }
        }
        mudaTitulo();
    }

    private void colocarPerguntasTela(int numero) {

        fragmentPergunta.setText(listaperguntasfiltrada.get(numero).getPergunta());
        alternativaUm.setText(listaperguntasfiltrada.get(numero).getAlternativa1());
        alternativaDois.setText(listaperguntasfiltrada.get(numero).getAlternativa2());
        alternativaTres.setText(listaperguntasfiltrada.get(numero).getAlternativa3());
        alternativaQuatro.setText(listaperguntasfiltrada.get(numero).getAlternativa4());
    }

    private void carregaTodasPerguntas() {
        listaperguntas = new ArrayList<>();
        String jsonStr = carregaJsonDoAsset("perguntas.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray perguntas = jsonObject.getJSONArray("perguntas");
            for (int i = 0; i < perguntas.length(); i++) {
                JSONObject pergunta = perguntas.getJSONObject(i);
                String nomeString = pergunta.getString("nome");
                String perguntaString = pergunta.getString("pergunta");
                String alternativa1String = pergunta.getString("alternativa1");
                String alternativa2String = pergunta.getString("alternativa2");
                String alternativa3String = pergunta.getString("alternativa3");
                String alternativa4String = pergunta.getString("alternativa4");
                String respostaString = pergunta.getString("resposta");

                listaperguntas.add(new Questao(nomeString,
                        perguntaString,
                        alternativa1String,
                        alternativa2String,
                        alternativa3String,
                        alternativa4String,
                        respostaString
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ficaVermelho(Button button) {
        button.setBackground(getResources().getDrawable(R.drawable.round_button_vermelho));
        ficaNormal(button);
    }

    private void ficaVerde(Button button) {

        button.setBackground(getResources().getDrawable(R.drawable.round_button_verde));
        ficaNormal(button);
    }

    private void ficaNormal(Button button) {

        Handler handler = new Handler();
        long delay = 1000;
        handler.postDelayed(() -> {
            button.setBackground(getResources().getDrawable(R.drawable.round_button));
            confereEPoe();
        }, delay);
    }

    private void confereEPoe() {
        if (perguntaAtual < listaperguntasfiltrada.size() - 1) {
            perguntaAtual++;
            colocarPerguntasTela(perguntaAtual);
        } else {
            helperQuiz.correto(correto);
            helperQuiz.errado(errado);
            helperQuiz.titulo(fragment_titulo.getText().toString());
            helperQuiz.troca();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        helperQuiz= (HelperQuiz) getActivity();
    }
}
