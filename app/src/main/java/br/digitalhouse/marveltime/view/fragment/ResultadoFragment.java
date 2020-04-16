package br.digitalhouse.marveltime.view.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.text.DecimalFormat;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.Interfaces.HelperQuiz;

public class ResultadoFragment extends Fragment {
    DecimalFormat formato = new DecimalFormat("#");
    private HelperQuiz helperQuiz;
    private TextView porcentagem;
    private String rtitulo="";
    private TextView questao;
    private TextView titulo;
    double rcorreto=0, rerrado=0, resultado=0;
    private String porcento, porcento2;
    private ImageView share_Personagem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resultado, container, false);
        initViews(v);
        Bundle bundle = this.getArguments();
        rtitulo=bundle.getString(getString(R.string.titulo));
        rerrado=bundle.getInt(getString(R.string.errado));
        rcorreto=bundle.getInt(getString(R.string.correto));
        rerrado+=rcorreto;

        titulo.setText(rtitulo);
        porcento = String.valueOf(formato.format(rerrado));
        porcento2 = String.valueOf(formato.format(rcorreto));

        TesteCompartilhar();

        clickBtnShared();

        return v;
    }

    private void TesteCompartilhar() {
        questao.setText(porcento2+"/"+porcento+ " " +getString(R.string.resp_corretas));
        resultado= (rcorreto/rerrado)*100;
        porcento = String.valueOf(formato.format(resultado));
        porcentagem.setText(porcento+" %");
    }

    private void initViews(View v) {
        porcentagem = v.findViewById(R.id.resultado_numero);
        questao = v.findViewById(R.id.resultado_numero_questao);
        titulo = v.findViewById(R.id.titulo_resultado);
        share_Personagem = v.findViewById(R.id.share_Personagem);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        helperQuiz= (HelperQuiz) getActivity();
    }

    private void shareMarvel() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.result));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void clickBtnShared() {
        share_Personagem.setOnClickListener(v -> shareMarvel());
    }
}