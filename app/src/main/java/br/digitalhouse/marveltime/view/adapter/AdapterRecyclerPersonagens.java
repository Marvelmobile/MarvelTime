package br.digitalhouse.marveltime.view.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.view.Interfaces.OnClickListenerPersonagem;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderPersonagens;

public class AdapterRecyclerPersonagens extends RecyclerView.Adapter<ViewHolderPersonagens> {
    private List<PersonagemResult> personagemResultsList;
    private OnClickListenerPersonagem personagemListener;

    public AdapterRecyclerPersonagens(List<PersonagemResult> personagemResultsList, OnClickListenerPersonagem personagemListener) {
        this.personagemResultsList = personagemResultsList;
        this.personagemListener = personagemListener;
    }

    @NonNull
    @Override
    public ViewHolderPersonagens onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_personagens, parent, false);
        ViewHolderPersonagens holder = new ViewHolderPersonagens(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPersonagens holder, int position) {
        PersonagemResult personagemResult = personagemResultsList.get(position);
        holder.onBind(personagemResult);
        holder.itemView.setOnClickListener(v -> personagemListener.click(personagemResult));
    }

    @Override
    public int getItemCount() {
        return personagemResultsList.size();
    }

    public void atualizaLista(List<PersonagemResult> novaLista) {
        if (this.personagemResultsList.isEmpty()) {
            this.personagemResultsList = novaLista;
        } else {
            this.personagemResultsList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }
}