package br.digitalhouse.marveltime.view.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.Interfaces.OnClickQuiz;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderQuiz;

public class AdapterRecyclerQuiz extends RecyclerView.Adapter<ViewHolderQuiz> {
    private List<CardModel> listaCards;
    private OnClickQuiz onClickQuiz;

    public AdapterRecyclerQuiz(List<CardModel> listaCards, OnClickQuiz onClickQuiz) {
        this.listaCards= listaCards;
        this.onClickQuiz = onClickQuiz;
    }

    @NonNull
    @Override
    public ViewHolderQuiz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_quiz, parent, false);
        return new ViewHolderQuiz(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderQuiz holder, int position) {
        CardModel cardModel = listaCards.get(position);
        holder.bind(cardModel);
        holder.imgProximo.setOnClickListener(v -> onClickQuiz.clickAbreQuiz(cardModel));
        holder.imgViewFavorito.setOnClickListener(v -> onClickQuiz.clickFavoritar(cardModel));
    }

    @Override
    public int getItemCount() {
        return listaCards.size();
    }
}