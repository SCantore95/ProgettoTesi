package tesi.example.myapplication.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.OnBackPressedListener;

public class ItemDetailsFragment extends Fragment {

    private TextView textViewId;
    private TextView textViewDescription;
    private TextView textViewOffenseType;
    private TextView textViewOffenceSource;
    private TextView textViewMagnitude;
    private TextView textViewSourceIPs;
    private TextView textViewDestinationIPs;
    private TextView textViewUsers;
    private TextView textViewLogSources;
    private TextView textViewEvents;
    private TextView textViewStartDate;
    private TextView textViewFlows;
    private TextView textViewLastEventsFlow;

    // Aggiungi altri TextView per gli altri campi
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_details, container, false);


        // Trova le TextView nel layout
        textViewId = view.findViewById(R.id.textId);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        textViewOffenseType = view.findViewById(R.id.textViewOffenseType);
        textViewOffenceSource = view.findViewById(R.id.textViewOffenceSource);
        textViewMagnitude = view.findViewById(R.id.textViewMagnitude);
        textViewSourceIPs = view.findViewById(R.id.textViewSourceIPs);
        textViewDestinationIPs = view.findViewById(R.id.textViewDestinationIPs);
        textViewUsers = view.findViewById(R.id.textUsers);
        textViewLogSources = view.findViewById(R.id.textViewLogSources);
        textViewEvents = view.findViewById(R.id.textViewEvents);
        textViewStartDate = view.findViewById(R.id.textStartDate);
        textViewFlows = view.findViewById(R.id.textFlows);
        textViewLastEventsFlow = view.findViewById(R.id.textLastEventsFlow);

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Estrai i dati dall'intent
            String id = bundle.getString("id");
            Log.d(TAG, "iditem details: " + id);
            String description = bundle.getString("description");
            String offenseType = bundle.getString("offenseType");
            String offenseSource = bundle.getString("offenseSource");
            String magnitude = bundle.getString("magnitude");
            String sourceIPs = bundle.getString("sourceIPs");
            String destinationIPs = bundle.getString("destinationIPs");
            String users = bundle.getString("users");
            String events = bundle.getString("events");
            String logSources = bundle.getString("logSources");
            String startDate = bundle.getString("startDate");
            String flows = bundle.getString("flows");
            String lastEventsFlow = bundle.getString("lastEventsFlow");

            // Aggiorna la TextView con i dati
            textViewId.setText("Id: " + id);
            textViewDescription.setText("Description: " + description);
            textViewOffenseType.setText("Offense Type: " + offenseType);
            textViewOffenceSource.setText("Offense Source: " + offenseSource);
            textViewMagnitude.setText("Magnitude: " + magnitude);
            textViewSourceIPs.setText("SourceIPs: " + sourceIPs);
            textViewDestinationIPs.setText("Offense DestinationIPs: " + destinationIPs);
            textViewUsers.setText("Users: " + users);
            textViewEvents.setText("Events: " + events);
            textViewLogSources.setText("LogSources: " + logSources);
            textViewStartDate.setText("StartDate: " + startDate);
            textViewFlows.setText("Flows:" + flows);
            textViewLastEventsFlow.setText("Last Events/Flow:" + lastEventsFlow);
        }

        // Trova altri TextView per gli altri campi

        // Ora puoi usare questi TextView per visualizzare i dati ricevuti

        // Restituisci la vista
        return view;
    }

    public static ItemDetailsFragment newInstance(Intent intent) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        fragment.setArguments(intent.getExtras());
        return fragment;
    }



}
