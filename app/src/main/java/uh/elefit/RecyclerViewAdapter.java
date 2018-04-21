package uh.elefit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pisoj on 21-Apr-18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<PodaciZaLiftFragment> mData;
    private LayoutInflater mInflater;

    private ItemClickListener mClickListener;


    public RecyclerViewAdapter(Context context, List<PodaciZaLiftFragment> data ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_lift, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PodaciZaLiftFragment animal = mData.get(position);
        holder.myTextView_id_dizala.setText(animal.id_dizala);
        holder.myTextView_datum_servisa.setText(animal.datum_servisa);
        holder.myTextView_ocjena_servisa.setText(animal.ocjena_servisa);
        holder.myTextView_faza.setText(animal.faza);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView_id_dizala;
        TextView myTextView_datum_servisa;
        TextView myTextView_ocjena_servisa;
        TextView myTextView_faza;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView_id_dizala = itemView.findViewById(R.id.ID_dizala);
            myTextView_datum_servisa = itemView.findViewById(R.id.datum_servisa);
            myTextView_ocjena_servisa = itemView.findViewById(R.id.ocjena_servisa);
            myTextView_faza = itemView.findViewById(R.id.faza);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
