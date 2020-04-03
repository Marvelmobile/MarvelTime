package br.digitalhouse.marveltime.view.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.digitalhouse.marveltime.view.activity.MainActivity;
import br.digitalhouse.marveltime.view.activity.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.view.Interfaces.Selecionavel;
import br.digitalhouse.marveltime.model.Questao;
import br.digitalhouse.marveltime.R;
import static android.widget.Toast.LENGTH_SHORT;
import static br.digitalhouse.marveltime.R.color.branco;

public class PerguntasQuizFragment extends Fragment {
    private TextView fragment_titulo;
    private TextView fragmentPergunta;
    private Button fragmentAlternativaUm;
    private Button fragmentAlternativaDois;
    private Button fragmentAlternativaTres;
    private Button fragmentAlternativaQuatro;
    private FloatingActionButton fragmentVoltar;
    private FloatingActionButton fragmentProximo;
    private Selecionavel selecionavel;
    private List<Questao> listaperguntas;
    private List<Questao> listaperguntasfiltrada = new ArrayList<>();
    int perguntaAtual = 0;
    int correto = 0, errado = 0;
    int duracaoNotifacao = LENGTH_SHORT;

    public PerguntasQuizFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_perguntas_quiz, container, false);
        initViews(v);
        carregaTodasPerguntas();
        Collections.shuffle(listaperguntas);
        filtroLista();
        colocarPerguntasTela(perguntaAtual);

        fragmentAlternativaUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa1().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                    correto++;
                    ficaVerde(fragmentAlternativaUm);
                } else {
                    errado++;
                    ficaVermelho(fragmentAlternativaUm);
                }
            }
        });

        fragmentAlternativaDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa2().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                    correto++;
                    ficaVerde(fragmentAlternativaDois);
                } else {
                    errado++;
                    ficaVermelho(fragmentAlternativaDois);
                }
            }
        });

        fragmentAlternativaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa3().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                    correto++;
                    ficaVerde(fragmentAlternativaTres);
                } else {
                    errado++;
                    ficaVermelho(fragmentAlternativaTres);
                }
            }
        });

        fragmentAlternativaQuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaperguntasfiltrada.get(perguntaAtual).getAlternativa4().equals(listaperguntasfiltrada.get(perguntaAtual).getResposta())) {
                    correto++;
                    ficaVerde(fragmentAlternativaQuatro);
                } else {
                    errado++;
                    ficaVermelho(fragmentAlternativaQuatro);
                }
            }
        });

        fragmentVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.selecionavel = (Selecionavel) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews(View view) {
        fragment_titulo = view.findViewById(R.id.fragment_titulo_principal);
        fragmentPergunta = view.findViewById(R.id.fragement_textview_pergunta);
        fragmentAlternativaUm = view.findViewById(R.id.fragment_button_alternativaUm);
        fragmentAlternativaDois = view.findViewById(R.id.fragment_button_alternativaDois);
        fragmentAlternativaTres = view.findViewById(R.id.fragment_button_alternativaTres);
        fragmentAlternativaQuatro = view.findViewById(R.id.fragment_button_alternativaQuatro);
        fragmentVoltar = view.findViewById(R.id.fragment_floatingActionButton_voltar);
        fragmentProximo = view.findViewById(R.id.fragment_floatingActionButton_proximo);
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
        fragmentAlternativaUm.setText(listaperguntasfiltrada.get(numero).getAlternativa1());
        fragmentAlternativaDois.setText(listaperguntasfiltrada.get(numero).getAlternativa2());
        fragmentAlternativaTres.setText(listaperguntasfiltrada.get(numero).getAlternativa3());
        fragmentAlternativaQuatro.setText(listaperguntasfiltrada.get(numero).getAlternativa4());
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

                listaperguntas.add(new Questao(id, nomeString,
                        perguntaString,
                        alternativa1String,
                        alternativa2String,
                        alternativa3String,
                        alternativa4String,
                        respostaString
                ));
            }
        } catch (JSONException e) {

        }
    }

    private void ficaVermelho(Button button) {
        button.setTextColor(getResources().getColor(R.color.errado));
        voltaBranco(button);
    }

    private void ficaVerde(Button button) {

        button.setTextColor(getResources().getColor(R.color.correto));
        voltaBranco(button);
    }

    private void voltaBranco(Button button) {
        Handler handler = new Handler();
        long delay = 1000;
        handler.postDelayed(new Runnable() {
            public void run() {
                button.setTextColor(getResources().getColor(branco));
                confereEPoe();
            }
        }, delay);
    }

    private void confereEPoe() {
        if (perguntaAtual < listaperguntasfiltrada.size() - 1) {
            perguntaAtual++;
            colocarPerguntasTela(perguntaAtual);
        } else {
            Context context = getContext();
            startActivity(new Intent(context, MainActivity.class));
        }
    }
}