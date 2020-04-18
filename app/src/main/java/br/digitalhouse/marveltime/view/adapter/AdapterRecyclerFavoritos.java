package br.digitalhouse.marveltime.view.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderFavoritos;
import static android.widget.Toast.LENGTH_SHORT;

public class AdapterRecyclerFavoritos extends RecyclerView.Adapter<ViewHolderFavoritos> {
    private ArrayList<CardModel> listaCards;
    private Context mContext;

    public AdapterRecyclerFavoritos(ArrayList<CardModel> listaCards, Context mContext) {
        this.listaCards = listaCards;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderFavoritos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_favoritos, parent, false);
        ViewHolderFavoritos holder = new ViewHolderFavoritos(view);
        return holder;
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(@NonNull ViewHolderFavoritos holder, int position) {
        holder.image.setImageResource(listaCards.get(position).getImagem());
        holder.texto.setText(listaCards.get(position).getNome());
        holder.favoritado.setOnClickListener(view -> {

        Toast toast = Toast.makeText(mContext,  holder.texto.getText().toString() + mContext.getString(R.string.msg_desfavoritado), LENGTH_SHORT);
        toast.show();
        removeAt(position);

=======
    public void onBindViewHolder(@NonNull ViewHolderFavoritos holder, final int position) {
        if (listaFavoritos.get(position).getCardModelquestão() instanceof CardModel) {
            holder.image.setImageResource(listaFavoritos.get(position).getCardModelquestão().getImagem());
            holder.texto.setText(listaFavoritos.get(position).getCardModelquestão().getNome());
        }

        if (listaFavoritos.get(position).getPersonagemResult() instanceof PersonagemResult) {
            String imageURL = listaFavoritos.get(position).getPersonagemResult().getThumbnail().getPath().replace("http://", "https://");
            Picasso.get().load(imageURL + "." + listaFavoritos.get(position).getPersonagemResult().getThumbnail().getExtension()).into(holder.image);
            holder.texto.setText(listaFavoritos.get(position).getPersonagemResult().getName());
        }

        holder.itemView.setOnClickListener(view -> {
            if (listaFavoritos.get(position).getCardModelquestão() instanceof CardModel) {
                Intent intent = new Intent(mContext, RecebePerguntasQuizActivity.class);
                intent.putExtra(CHAVE_NOME, verifica(listaFavoritos.get(position).getCardModelquestão().getNome()));
                mContext.startActivity(intent);
            }

            if (listaFavoritos.get(position).getPersonagemResult() instanceof PersonagemResult) {
                Intent intent = new Intent(mContext, PersonagensTelaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(PERSONAGEM_KEY, (Parcelable) listaFavoritos.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
>>>>>>> parent of 9699771... Henrique Sprint 6
        });
    }

    @Override
    public int getItemCount() {
        return listaCards.size();
    }

    public void removeAt(int position) {
        listaCards.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaCards.size());
    }
}