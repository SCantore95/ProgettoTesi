package tesi.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
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

public class ProfileFragment extends Fragment implements ItemClickListener, LoaderManager.LoaderCallbacks<List<ResultsItem>> {

    private static final int LOADER_ID = 1;
    private static final String JSON_URL = "https://run.mocky.io/v3/8331fd23-68c0-44ea-95da-a7cc7602b262";
    private static final String TAG = "ProfileFragment";

    private List<ResultsItem> attackLists;
    private RecyclerView recyclerView;
    private Results resultsAdapter;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        attackLists = new ArrayList<>();
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
    }

    @Override
    public void onItemClick(Intent intent) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ItemDetailsFragment.newInstance(intent))
                    .addToBackStack(null)
                    .commit();
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
