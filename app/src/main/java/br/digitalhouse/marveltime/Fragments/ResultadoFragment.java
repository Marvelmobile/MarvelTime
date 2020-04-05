package br.digitalhouse.marveltime.Fragments;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import br.digitalhouse.marveltime.Interfaces.HelperQuiz;
import br.digitalhouse.marveltime.R;
public class ResultadoFragment extends Fragment {
    DecimalFormat formato = new DecimalFormat("#");

    private HelperQuiz helperQuiz;
    private TextView porcentagem;
    private String rtitulo="";
    private TextView questao;
    private TextView titulo;
    double rcorreto=0, rerrado=0, resultado=0;
    private String porcento, porcento2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resultado, container, false);
        initViews(v);
        Bundle bundle = this.getArguments();
        rtitulo=bundle.getString("titulo");
        rerrado=bundle.getInt("errado");
        rcorreto=bundle.getInt("correto");
        rerrado+=rcorreto;

        titulo.setText(rtitulo);
        porcento = String.valueOf(formato.format(rerrado));
        porcento2 = String.valueOf(formato.format(rcorreto));

        questao.setText(porcento2+"/"+porcento+" questões corretas");
        resultado= (rcorreto/rerrado)*100;
        porcento = String.valueOf(formato.format(resultado));
        porcentagem.setText(porcento+" %");
        return v;
    }

    private void initViews(View v) {
        porcentagem = v.findViewById(R.id.resultado_numero);
        questao = v.findViewById(R.id.resultado_numero_questao);
        titulo = v.findViewById(R.id.titulo_resultado);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        helperQuiz= (HelperQuiz) getActivity();
    }
}