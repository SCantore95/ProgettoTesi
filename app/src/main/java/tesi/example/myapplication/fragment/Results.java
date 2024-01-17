package tesi.example.myapplication.fragment;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

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

    @Override
    public void onBindViewHolder(@NonNull Results.MyViewHolder holder, int position) {
        ResultsItem currentItem = mData.get(position);


        holder.id.setText(currentItem.getId());
        Log.d(TAG, "getId(): " + mData.get(position).getId());





        holder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {

                    Intent intent = new Intent(mContext, ItemDetailsFragment.class);
                    intent.putExtra("id", (currentItem.getId()));
                    intent.putExtra("description", (currentItem.getDescription()));
                    intent.putExtra("offenseType", (currentItem.getOffenseType()));
                    intent.putExtra("offenceSource", (currentItem.getOffenceSource()));
                    intent.putExtra("magnitude", (currentItem.getMagnitude()));
                    intent.putExtra("sourceIPs", (currentItem.getSourceIPs()));
                    intent.putExtra("destinationIPs", (currentItem.getDestinationIPs()));
                    intent.putExtra("users", (currentItem.getUsers()));
                    intent.putExtra("events", (currentItem.getEvents()));
                    intent.putExtra("logSources", (currentItem.getLogSources()));
                    intent.putExtra("startDate", (currentItem.getStartDate()));
                    intent.putExtra("flows", (currentItem.getFlows()));
                    intent.putExtra("lastEventsFlow", (currentItem.getLastEventsFlow()));
                    itemClickListener.onItemClick(intent);
                    Log.d(TAG, "doInBackground() returned: " + intent);
                }

            }
        });

    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            id=itemView.findViewById(R.id.id_text);


        }
    }
}
