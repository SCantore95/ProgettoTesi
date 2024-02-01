package tesi.example.myapplication.fragment;

import androidx.fragment.app.Fragment;

public class YearPickerFragment extends Fragment {
/*

    private NumberPicker yearPicker;
    private Button selectYearButton;
    public interface OnYearSelectedListener {
        void onYearSelected(int year);
    }
    private OnYearSelectedListener yearSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.year_picker_dialog, container, false);

        yearPicker = view.findViewById(R.id.yearPicker);
        selectYearButton = view.findViewById(R.id.selectYearButton);

        // Configurazione del NumberPicker per selezionare l'anno
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(2023); // Anno minimo
        yearPicker.setMaxValue(currentYear); // Anno massimo
        yearPicker.setValue(currentYear); // Valore predefinito

        // Ascoltatore per il pulsante di selezione dell'anno
        selectYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yearSelectedListener != null) {
                    int selectedYear = yearPicker.getValue();
                    yearSelectedListener.onYearSelected(selectedYear);
                }
            }
        });

        return view;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener listener) {
        this.yearSelectedListener = listener;
    }

*/
}
