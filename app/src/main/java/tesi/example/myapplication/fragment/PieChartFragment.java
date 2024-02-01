package tesi.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.OnBackPressedListener;
import tesi.example.myapplication.MainActivity;

public class PieChartFragment extends Fragment {
    private List<ResultsItem> currentData=new ArrayList<>();
    private PieChart pieChart;
    private OnBackPressedListener onBackPressedListener;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnBackPressedListener) {
            onBackPressedListener = (OnBackPressedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackPressedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.pie_chart_fragment, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gestisci l'azione del pulsante Indietro
                if (onBackPressedListener != null && mContext instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) mContext;
                    // Ora puoi utilizzare mainActivity.getSupportFragmentManager() per ottenere il FragmentManager e gestire i fragment.
                    onBackPressedListener.onBackButtonPressed(MainActivity.class);
                }
            }
        });

        return view;
    }



    // Metodo di utilità per estrarre i dati relativi ai vari mesi dalla lista di ResultsItem

    private PieData extractPieChartData(List<ResultsItem> currentData) {
        // Mappa per tenere traccia del conteggio degli attacchi per ciascun mese
        Map<Integer, Integer> attackCountMap = new HashMap<>();

        // Itera attraverso i risultati per conteggiare gli attacchi per ID
        for (ResultsItem item : currentData) {
            // Estrarre il mese dalla data dell'attacco
            String attackDate = item.getStartDate();
            int month = extractMonthFromDate(attackDate);

            // Incrementa il conteggio degli attacchi per il mese corrente
            if (month != -1) {
                int count = attackCountMap.getOrDefault(month, 0);
                attackCountMap.put(month, count + 1);
            }
        }

        // Lista di PieEntry per i dati del grafico a torta
        ArrayList<PieEntry> entries = new ArrayList<>();

        // Calcola il totale degli attacchi
        int totalAttacks = currentData.size();

        // Aggiungi i dati al grafico a torta con la percentuale
        for (Map.Entry<Integer, Integer> entry : attackCountMap.entrySet()) {
            int month = entry.getKey();
            int count = entry.getValue();
            float percentage = totalAttacks > 0 ? (float) count / totalAttacks * 100 : 0;
            entries.add(new PieEntry(percentage, getMonthName(month)));
        }

        // Imposta i colori per le fette del grafico
        PieDataSet dataSet = new PieDataSet(entries, "Attacks");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        // Crea e restituisci l'oggetto PieData per il grafico a torta
        return new PieData(dataSet);
    }

    private int extractMonthFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALIAN);
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) + 1; // Aggiungi 1 perché i mesi in Calendar partono da 0
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Restituisci -1 in caso di errore o fallback
        return -1;
    }

    private String getMonthName(int month) {
        // Array con i nomi dei mesi
        String[] monthNames = new String[]{"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"};

        // Verifica che il mese sia nell'intervallo corretto
        if (month >= 1 && month <= 12) {
            return monthNames[month - 1];
        } else {
            return "Invalid Month";
        }
    }





    public void setPieChartData(List<ResultsItem>  entries  ) {
        PieData entrie = extractPieChartData(entries);
       // PieData data = new PieData(dataSet);
        pieChart.setData(entrie);
        pieChart.invalidate();
        pieChart.getDescription().setEnabled(false);

    }




}
