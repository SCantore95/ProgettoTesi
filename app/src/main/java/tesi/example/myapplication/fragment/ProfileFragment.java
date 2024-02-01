package tesi.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.ItemClickListener;
import tesi.example.myapplication.MainActivity;

public  class ProfileFragment extends Fragment implements ItemClickListener, LoaderManager.LoaderCallbacks<List<ResultsItem>> {

    private static final int LOADER_ID = 1;
    private static final String JSON_URL = "https://run.mocky.io/v3/10a41b9a-1bf9-4c72-821c-103fa720ed22";
    private static final String TAG = "ProfileFragment";
    private ItemClickListener mItemClickListener;

    private List<ResultsItem> attackLists;
    private RecyclerView recyclerView;
    private Results resultsAdapter;
    private Context mContext;
    private ProfileDataListener dataListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Altri codici del fragment


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Log.d(TAG, "ProfileFragment attached to activity");
        mContext = context;

        try {
            // Assicurati che l'Activity ospitante implementi l'interfaccia
            dataListener = (ProfileDataListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " deve implementare ProfileDataListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        attackLists = new ArrayList<>();
        mItemClickListener = this;
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigationView();
        }

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        return view;
    }

    @Override
    public Loader<List<ResultsItem>> onCreateLoader(int id, Bundle args) {
        return new GetDataLoader(mContext, JSON_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<ResultsItem>> loader, List<ResultsItem> data) {
        if (data != null) {
            attackLists = data;
            PutDataIntoRecyclerView(attackLists);
            if (dataListener != null) {
                dataListener.onProfileDataAvailable(attackLists);
                Log.d(TAG, "updateData to send: Data updated in CounterFragment. Size: " + dataListener);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ResultsItem>> loader) {
        // Implementa se necessario
    }

    private void PutDataIntoRecyclerView(List<ResultsItem> attackLists) {
        resultsAdapter = new Results(mContext, attackLists, this);
        Log.d(TAG, "attacklist put data: " + attackLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(resultsAdapter);
        if (resultsAdapter != null) {
            resultsAdapter.updateData(attackLists);
        }
        if (dataListener != null) {
            dataListener.onProfileDataAvailable(attackLists);
            Log.d(TAG, "updateData to send: Data updated in CounterFragment. Size: " + dataListener);
        }


    }



    @Override
    public void onItemClick(ResultsItem currentItem) {
        String destinationFragmentTag = currentItem.getDestinationFragmentTag();

        if ("ItemDetailsFragment".equals(destinationFragmentTag)) {
            onItemDetailsFragmentClick(currentItem);
            Log.d(TAG, "viene chiamato:ok ");

        } else if ("StatsFragmentTag".equals(destinationFragmentTag)) {
            //onStatsFragmentClick(currentItem);
        }
    }



    @Override
    public void onItemDetailsFragmentClick(ResultsItem intent) {
        // Estrai i dati dall'intent
        String id = intent.getId();
        String description = intent.getDescription();
        String offenseType = intent.getOffenseType();
        String offenseSource = intent.getOffenceSource();
        String magnitude = intent.getMagnitude();
        String sourceIPs = intent.getSourceIPs();
        String destinationIPs = intent.getDestinationIPs();
        String users = intent.getUsers();
        String events = intent.getEvents();
        String logSources = intent.getLogSources();
        String startDate = intent.getStartDate();
        String flows = intent.getFlows();
        String lastEventsFlow = intent.getLastEventsFlow();


        // Esempio: Stampa i dati nella console
        Log.d(TAG, "Item ID: " + id);
        Log.d(TAG, "Item Description: " + description);
        Log.d(TAG, "Item startDate: " + startDate);

        // Puoi anche passare questi dati al tuo ItemDetailsFragment
        ItemDetailsFragment itemDetailsFragment = null;
        if (getActivity() != null) {
            itemDetailsFragment = new ItemDetailsFragment();

            // Crea un nuovo Bundle per passare i dati
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("description", description);
            bundle.putString("offenseType", offenseType);
            bundle.putString("offenseSource", offenseSource);
            bundle.putString("magnitude", magnitude);
            bundle.putString("sourceIPs", sourceIPs);
            bundle.putString("destinationIPs", destinationIPs);
            bundle.putString("users", users);
            bundle.putString("events", events);
            bundle.putString("logSources", logSources);
            bundle.putString("startDate", startDate);
            bundle.putString("flows", flows);
            bundle.putString("lastEventsFlow", lastEventsFlow);


            itemDetailsFragment.setArguments(bundle);
        }

        // Aggiorna la lista nel tuo adapter
        if (resultsAdapter != null) {
            resultsAdapter.updateData(attackLists);
        }


        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, itemDetailsFragment)
                .addToBackStack(null)
                .commit();





    }

    public interface ProfileDataListener {
        void onDataReceived(String data);

        void onProfileDataAvailable(List<ResultsItem> attackLists);


    }
    @Override
    public void onProfileDataAvailable(List<ResultsItem> attackLists) {
        Log.d(TAG, "onProfileDataAvailable: Data available. Size: " + (attackLists != null ? attackLists.size() : 0));


        // Aggiorna l'adapter o esegui altre operazioni necessarie con attackLists
        if (resultsAdapter != null) {
            resultsAdapter.updateData(attackLists);
        }
    }



    private static class GetDataLoader extends AsyncTaskLoader<List<ResultsItem>> {
        private final String jsonUrl;

        public GetDataLoader(Context context, String jsonUrl) {
            super(context);
            this.jsonUrl = jsonUrl;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<ResultsItem> loadInBackground() {
            try {
                StringBuilder result = new StringBuilder();
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(jsonUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Gestisci l'eccezione in modo più robusto, ad esempio, restituendo una lista vuota
                    return new ArrayList<>();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("events");
                List<ResultsItem> attackLists = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ResultsItem model = new ResultsItem();
                    model.setId(jsonObject1.getString("id"));
                    model.setDescription(jsonObject1.getString("Description"));
                    model.setOffenseType(jsonObject1.getString("Offense Type"));
                    model.setOffenceSource(jsonObject1.getString("Offence Source"));
                    model.setMagnitude(jsonObject1.getString("Magnitude"));
                    model.setSourceIPs(jsonObject1.getString("Source  IPs"));
                    model.setDestinationIPs(jsonObject1.getString("Destination IPs"));
                    model.setUsers(jsonObject1.getString("Users"));
                    model.setEvents(jsonObject1.getString("Events"));
                    model.setLogSources(jsonObject1.getString("Log Sources"));
                    model.setStartDate(jsonObject1.getString("Start Date"));
                    model.setFlows(jsonObject1.getString("Flows"));
                    model.setLastEventsFlow(jsonObject1.getString("Last Events/Flow"));

                    attackLists.add(model);
                }
                return attackLists;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}