package tesi.example.myapplication.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.StatsDataListener;

public class StatsFragment extends Fragment implements View.OnClickListener, StatsDataListener {

    private StatsDataListener statsDataListener;
    private List<ResultsItem> currentData;
    PieChartFragment pieChartFragment;

    public List<ResultsItem> getCurrentData() {
        return currentData;
    }

    public void setStatsDataListener(StatsDataListener listener) {
        this.statsDataListener = listener;

    }

    @Override
    public void onStatsDataAvailable(List<ResultsItem> currentData) {
        // Chiamato quando i dati sono disponibili in StatsFragment
        // Puoi gestire i dati qui e passarli a PieChartFragment

        // Verifica se i dati correnti sono disponibili
        if (this.currentData == null) {
            this.currentData = new ArrayList<>();
        }

        this.currentData.addAll(currentData);
        Log.d(TAG, "currentDataStats1: " + currentData.size());


        if (pieChartFragment == null) {
            // Aggiungi il fragment solo se non è già stato aggiunto
            pieChartFragment = new PieChartFragment();
        }
        if (statsDataListener != null) {
            statsDataListener.onStatsDataAvailable(currentData);
            Log.d(TAG, "statsDataListener: "+statsDataListener.toString());
        }





    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int buttonId ;

        switch (v.getId()) {
            case R.id.BarChart:
                buttonId = StatsDataListener.BUTTON_BAR_CHART;


                break;
            case R.id.PieChart:

                buttonId = StatsDataListener.BUTTON_PIE_CHART;


                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }


        if (statsDataListener != null) {
            statsDataListener.onStatsButtonClick(buttonId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stats, container, false);

        ImageView button1 = view.findViewById(R.id.BarChart);
        ImageView button2 = view.findViewById(R.id.PieChart);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        Log.d(TAG, "StatsFragment onCreateView");


        return view;
    }

    @Override
    public void onStatsButtonClick(int buttonId) {
        // Chiamato quando viene cliccato un bottone in StatsFragment
        // Aggiorna la logica in base al bottone cliccato
        if (statsDataListener != null) {
            statsDataListener.onStatsButtonClick(buttonId);


        }
    }
}