package tesi.example.myapplication.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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

public class StatsFragment extends Fragment {

    private static final String TAG = "CounterFragment";

    private List<ResultsItem> attackLists = new ArrayList<>();
    private TextView textMonth;
    private BarChart barChart;
    private PieChart pieChart;
    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
List<ResultsItem> filteredData= new ArrayList<>();

    public StatsFragment() {
        // Empty for now
    }

    public void updateCounterData(List<ResultsItem> attackLists) {
        this.attackLists = attackLists;
        if (attackLists != null) {
            Log.d(TAG, "updateCounterData: Attack lists updated" + attackLists.size());
        } else {
            Log.e(TAG, "updateCounterData: Attack lists is null");
        }
        Log.d(TAG, "updateCounterData: Attack lists updated" + attackLists.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stats, container, false);
        ImageButton backButton = view.findViewById(R.id.backButton);

        // Get references to TextViews in the layout
        textMonth = view.findViewById(R.id.textMonth);
        pieChart = view.findViewById(R.id.pieChart); // Make sure the ID is correct
        barChart = view.findViewById(R.id.barChart); // Make sure the ID is correct

        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        // Add a listener to the layout to handle click
        textMonth.setOnClickListener(v -> showMonthYearPickerDialog());





        return view;
    }

    private void showMonthYearPickerDialog() {

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View pickerLayout = inflater.inflate(R.layout.custom_month_year_picker, null);

        // Get references to NumberPicker in the custom layout
        NumberPicker monthPicker = pickerLayout.findViewById(R.id.monthPicker);
        NumberPicker dayPicker = pickerLayout.findViewById(R.id.dayPicker);
        NumberPicker yearPicker = pickerLayout.findViewById(R.id.yearPicker);

        // Configure day NumberPicker (1 to 31)
        configureNumberPicker(dayPicker, 1, 31);

        // Configure month NumberPicker (1 to 12)
        configureNumberPicker(monthPicker, 1, 12);

        // Configure year NumberPicker (from 2023 to the current year)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        configureNumberPicker(yearPicker, 2023, currentYear);
        // Set default values for NumberPickers
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Adding 1 because months start from 0
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        dayPicker.setValue(currentDay);
        monthPicker.setValue(currentMonth);
        yearPicker.setValue(currentYear);

        // Update selected date
        selectedDay = currentDay;
        selectedMonth = currentMonth;
        selectedYear = currentYear;
        // Create and show the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleziona Giorno,Mese e Anno");
        builder.setView(pickerLayout);


        builder.setPositiveButton("OK", (dialog, which) -> {
            filteredData= new ArrayList<>();
            selectedMonth = monthPicker.getValue();
            selectedDay = dayPicker.getValue();
            selectedYear = yearPicker.getValue();


            // Count data for the selected date
            countDataForSelectedDate(selectedDay, selectedMonth, selectedYear);
        });
        builder.setNegativeButton("Annulla", null);


        builder.show();
    }

    private void configureNumberPicker(NumberPicker numberPicker, int minValue, int maxValue) {
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
    }

    private void countDataForSelectedDate(int day, int month, int year) {
        if (attackLists != null) {
            Log.d(TAG, "countDataForSelectedDate: attackLists not null" + attackLists.size());

            // Filter data for the selected date
            List<ResultsItem> filteredData = filterDataByDate(attackLists ,day, month,year);
            Log.d(TAG, "filteredData: "+filteredData.size());

            // Generate Pie Chart and Bar Chart for filtered data
            if (!filteredData.isEmpty()) {
                setChartData(filteredData);

                // Display Pie Chart and Bar Chart with filtered data
            } else {
                // Show a message to the user that there is no data for the selected date
            }
        } else {
            Log.e(TAG, "countDataForSelectedDate: attackLists is null");
        }
    }

    private List<ResultsItem> filterDataByDate(List<ResultsItem> data, int day, int month, int year) {


        for (ResultsItem item : data) {
            String startDate = item.getStartDate();
            Log.d(TAG, "Original date in filterDataByDate: " + startDate);
            int itemDay = extractDayFromDate(startDate);
            int itemMonth = extractMonthFromDate(startDate);
            int itemYear = extractYearFromDate(startDate);
            Log.d(TAG, "ItemYer: "+itemYear);
            Log.d(TAG, "year: "+year);
            Log.d(TAG, "itemYear: " + itemYear + ", itemMonth: " + itemMonth + ", itemDay: " + itemDay);
            if (itemYear == year && itemMonth == month && itemDay == day) {
                filteredData.add(item);

                Log.d(TAG, "filterDataByDate: "+filteredData.size());
            }
        }
       if( filteredData.isEmpty()) {
            Toast.makeText(getContext(), "No attacks found", Toast.LENGTH_SHORT).show();
        }

        return filteredData;
    }

    private int extractYearFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALIAN);
        if (dateString == null || dateString.isEmpty()) {
            return -1; // Handle the case where the date is empty or missing
        }
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing year from date: " + dateString, e);
            return -1;
        }
    }

    private int extractMonthFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALIAN);
        if (dateString == null || dateString.isEmpty()) {
            return -1; // Handle the case where the date is empty or missing
        }
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) + 1; // Add 1 because months in Calendar start from 0
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing month from date: " + dateString, e);
            return -1;
        }
    }

    private int extractDayFromDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALIAN);
        if (dateString == null || dateString.isEmpty()) {
            return -1; // Handle the case where the date is empty or missing
        }
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing day from date: " + dateString, e);
            return -1;
        }
    }





    private PieData extractPieChartData(List<ResultsItem> currentData) {
        // Array di nomi dei mesi
        String[] monthNames = new String[]{"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

        // Mappa per tenere traccia del conteggio delle categorie
        Map<String, Integer> categoryCountMap = new HashMap<>();
        int totalAttacks = currentData.size();

        // Contatori per le categorie specifiche
        int vehicleDosCount = 0;
        int vehicleFuzzyCount = 0;
        String attackDate = null;

        for (ResultsItem item : currentData) {
             attackDate = item.getStartDate();
            int itemMonth = extractMonthFromDate(attackDate);

            // Aggiorna i contatori delle categorie specifiche
            if ("Vehicle: DoS with high priority".equals(item.getDescription())) {
                vehicleDosCount++;
            } else if ("Vehicle: Fuzzy with high priority".equals(item.getDescription())) {
                vehicleFuzzyCount++;
            }

            if (itemMonth != -1) {
                String monthName = monthNames[itemMonth - 1]; // Sottrai 1 perché gli array iniziano da 0
                int count = categoryCountMap.getOrDefault(monthName, 0);
                categoryCountMap.put(monthName, count + 1);


            }

        }

        ArrayList<PieEntry> entries = new ArrayList<>();


        // Aggiungi le voci per le categorie specifiche
        if (vehicleDosCount > 0) {
            entries.add(new PieEntry(vehicleDosCount, "DoS")); // Aggiungi "DoS" come etichetta nel grafico
        }

        if (vehicleFuzzyCount > 0) {
            entries.add(new PieEntry(vehicleFuzzyCount, "Fuzzy")); // Aggiungi "Fuzzy" come etichetta nel grafico
        }



        PieDataSet dataSet = new PieDataSet(entries,attackDate); // Imposta null come etichetta del grafico a torta
        dataSet.setValueTextSize(15f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        return new PieData(dataSet);
    }




    private BarData extractBarChartData(List<ResultsItem> currentData) {
        int totalAttacks = currentData.size();
        int[] dosCountsPerMonth = new int[12];
        int[] fuzzyCountsPerMonth = new int[12];
        String attackDate=null;
        for (ResultsItem item : currentData) {
             attackDate = item.getStartDate();
            int month = extractMonthFromDate(attackDate);


            if ("Vehicle: DoS with high priority".equals(item.getDescription())) {
                dosCountsPerMonth[month - 1]++;
            } else if ("Vehicle: Fuzzy with high priority".equals(item.getDescription())) {
                fuzzyCountsPerMonth[month - 1]++;
            }
        }



        ArrayList<BarEntry> dosEntries = new ArrayList<>();
        ArrayList<BarEntry> fuzzyEntries = new ArrayList<>();
        String[] months = new String[]{"Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"};

        for (int month = 0; month < 12; month++) {
            dosEntries.add(new BarEntry(month, dosCountsPerMonth[month]));
            fuzzyEntries.add(new BarEntry(month, fuzzyCountsPerMonth[month]));
        }


        BarDataSet dosDataSet = new BarDataSet(dosEntries, "Dos  ");
        dosDataSet.setColor(Color.BLUE);
        dosDataSet.setValueTextSize(4f);

        BarDataSet fuzzyDataSet = new BarDataSet(fuzzyEntries, "Fuzzy   "+attackDate);
        fuzzyDataSet.setColor(Color.RED);
        fuzzyDataSet.setValueTextSize(4f);

        BarData barData = new BarData(dosDataSet, fuzzyDataSet);
        barData.setBarWidth(0.22f); // Imposta la larghezza delle barre
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);

        barChart.setData(barData);
        barChart.groupBars(0, 0.22f, 0.14f);

        return barData;
    }

    public void setChartData(List<ResultsItem> entries) {
        if (selectedYear == 0 || selectedMonth == 0 || selectedDay == 0) {
            return;
        }
        List<ResultsItem> filteredData = filterDataByDate(entries, selectedYear, selectedMonth, selectedDay);
        if (!filteredData.isEmpty()) {
           PieData pieData = extractPieChartData(filteredData);
            pieChart.setData(pieData);
            pieChart.invalidate();
            pieChart.getDescription().setEnabled(false);
            BarData barData = extractBarChartData(filteredData);
            barChart.setData(barData);
            barChart.invalidate();
            barChart.getDescription().setEnabled(false);
        } else {
            Log.d(TAG, "No data available for the selected date");
        }
    }
}
