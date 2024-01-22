package tesi.example.myapplication.fragment;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class CounterFragment extends Fragment {

    private static final String TAG = "CounterFragment";

    private List<ResultsItem> attackLists = new ArrayList<>();
    private TextView textMonth;
    private TextView textCount;

    public CounterFragment() {
        // Vuoto per ora
    }

    public void updateCounterData(List<ResultsItem> attackLists) {
        this.attackLists = attackLists;
        Log.d(TAG, "updateCounterData: Attack lists updated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counter, container, false);

        // Ottieni riferimenti alle TextView nel layout
        textMonth = view.findViewById(R.id.textMonth);
        textCount = view.findViewById(R.id.textCount);

        // Aggiungi un listener al layout per gestire il click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostra il dialog personalizzato quando l'utente clicca sulla vista
                showMonthYearPickerDialog();
            }
        });

        return view;
    }

    private void showMonthYearPickerDialog() {
        // Crea un layout inflater
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View pickerLayout = inflater.inflate(R.layout.custom_month_year_picker, null);

        // Ottieni riferimenti ai NumberPicker nel layout personalizzato
        NumberPicker monthPicker = pickerLayout.findViewById(R.id.monthPicker);
        NumberPicker yearPicker = pickerLayout.findViewById(R.id.yearPicker);

        // Configura i NumberPicker per il mese (da 1 a 12)
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        // Configura i NumberPicker per l'anno (a partire dal 2023)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(2023);
        yearPicker.setMaxValue(currentYear);  // Puoi impostare il massimo come desideri

        // Creare e mostrare il Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Seleziona Mese e Anno");
        builder.setView(pickerLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedMonth = monthPicker.getValue();
                int selectedYear = yearPicker.getValue();

                // Esegui il conteggio dei dati per il mese e l'anno selezionati
                countDataForSelectedMonthAndYear(selectedYear, selectedMonth);
            }
        });
        builder.setNegativeButton("Annulla", null);

        builder.show();
    }

    private void countDataForSelectedMonthAndYear(int year, int month) {
        // Esegui il conteggio dei dati per il mese e anno selezionati
        if (attackLists != null) {
            Log.d(TAG, "countDataForSelectedMonthAndYear: attackLists not null");
            int count = getCountForMonthAndYear(attackLists, year, month);

            // Aggiorna le TextView con i risultati
            textMonth.setText(getString(R.string.selected_month, month, year));
            textCount.setText(getString(R.string.attack_count, count));
        } else {
            Log.e(TAG, "countDataForSelectedMonthAndYear: attackLists is null");
        }
    }

    private int getCountForMonthAndYear(List<ResultsItem> data, int year, int month) {
        if (data == null) {
            return 0;  // Tratta il caso in cui la lista sia null
        }

        int count = 0;

        for (Parcelable parcelable : data) {
            if (parcelable instanceof ResultsItem) {
                ResultsItem item = (ResultsItem) parcelable;

                // Rest of the logic remains the same
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parseDate(item.getStartDate()));
                int itemYear = calendar.get(Calendar.YEAR);
                int itemMonth = calendar.get(Calendar.MONTH) + 1;

                if (itemYear == year && itemMonth == month) {
                    count++;
                }
            }
        }

        return count;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
