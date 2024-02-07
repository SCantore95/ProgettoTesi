package tesi.example.myapplication.fragment;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.ItemClickListener;

public class Results extends RecyclerView.Adapter<Results.MyViewHolder> {

    private final Context mContext;
    private static ArrayList<ResultsItem> mData;
    private MyViewHolder holder;
    private int position;
    private final ItemClickListener itemClickListener;


    public Results(Context mContext, List<ResultsItem> mData,ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData) ;
        this.itemClickListener = itemClickListener;
        //Log.d(TAG, "doInBackground() returned2: " + mContext);
        Log.d(TAG, "doInBackground() returned2: " + mData);
    }
    public  void updateData(List<ResultsItem> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v;
        v = inflater.inflate(R.layout.items, parent, false);

        return  new MyViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onBindViewHolder(@NonNull Results.MyViewHolder holder, int position) {
        ResultsItem currentItem = mData.get(position);

        holder.description.setText(currentItem.getDescription());
        holder.startDate.setText(currentItem.getStartDate());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {



                    currentItem.setDestinationFragmentTag("ItemDetailsFragment");

                    // Copia i dati dall'elemento corrente

                    // Aggiungi altri campi se necessario

                    // Passa l'oggetto a onItemDetailsFragmentClick
                    itemClickListener.onItemDetailsFragmentClick(currentItem);
                }
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        TextView description;
        TextView startDate;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            description=itemView.findViewById(R.id.description_text);
            startDate=itemView.findViewById(R.id.startDate_text);

        }
    }
}
