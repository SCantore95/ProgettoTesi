package tesi.example.myapplication.fragment;


import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

// Nel tuo fragment Java
public class BarChartFragment extends Fragment {

    private BarChart barChart;
    List<ResultsItem> currentData=new ArrayList<>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_chart_fragment, container, false);

        // Inizializza il tuo PieChart
        barChart = view.findViewById(R.id.barChart);


        // Altre inizializzazioni o operazioni necessarie

        return view;
    }

    ;


    // Metodo di utilità per estrarre i dati relativi ai vari mesi dalla lista di ResultsItem
    private BarData extractBarChartData(List<ResultsItem> currentData) {
        // Mappa per tenere traccia del conteggio degli attacchi per ciascun mese
        Map<Integer, Integer> attackCountMap = new HashMap<>();

        // Calcola il totale degli attacchi
        int totalAttacks = currentData.size();

        // Itera attraverso i risultati per conteggiare gli attacchi per ID
        for (ResultsItem item : currentData) {
            Log.d(TAG, "dati calcolo per il grafico: " + currentData.size());
            int attackId = Integer.parseInt(item.getId());
            int count = attackCountMap.getOrDefault(attackId, 0);
            attackCountMap.put(attackId, count + 1);
        }

        // Lista di BarEntry per i dati del grafico a barre
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Itera attraverso i mesi e aggiungi i dati al grafico a barre con la percentuale


// Itera attraverso gli attacchi per calcolare il conteggio per ciascun mese
        // Array per tenere traccia del conteggio degli attacchi per ciascun mese (0 = gennaio, 1 = febbraio, ..., 11 = dicembre)
        int[] attackCountsPerMonth = new int[12];

        for (ResultsItem item : currentData) {
            // Estrarre il mese dalla data dell'attacco
            String attackDate = item.getStartDate();
            int month = extractMonthFromDate(attackDate);

            // Incrementa il conteggio degli attacchi per il mese corrente
            if (month != -1) {
                attackCountsPerMonth[month - 1]++;
            }
        }

// Calcola il totale degli attacchi

        float res = 0;
// Calcola la percentuale di attacchi per ogni mese e aggiungi le barre al grafico
        for (int month = 0; month <12; month++) {
            int attacksInMonth = attackCountsPerMonth[month];

            float percentage = totalAttacks > 0 ? (float) attacksInMonth / totalAttacks * 100 : 0;
            entries.add(new BarEntry(month, percentage));
        }

        // Imposta i colori per le barre del grafico
        BarDataSet dataSet = new BarDataSet(entries, "Attacks");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Crea e restituisci l'oggetto BarData per il grafico a barre
        BarData barData = new BarData(dataSet);

        // Imposta le etichette degli assi x e y
        String[] months = new String[]{"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(0.5F);
        xAxis.setLabelCount(11);

        return barData;
    }




    private int extractMonthFromDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALIAN);
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH)+1 ; // Aggiungi 1 perché i mesi in Calendar partono da 0
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Restituisci -1 in caso di errore o fallback
        return -1;
    }

    public void setBarChartData(List<ResultsItem>  entries) {
        BarData entrie = extractBarChartData(entries);
        // PieData data = new PieData(dataSet);
        barChart.setData(entrie);
        barChart.invalidate();
    }
}