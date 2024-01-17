package tesi.example.myapplication.fragment;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;


public class ProfileFragment extends Fragment implements ItemClickListener {

    // json String
    List<ResultsItem> attackLists;
    RecyclerView recyclerView;
    private Context mContext;

    private Results resultsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,

                                          Bundle savedInstanceState) {

        android.view.View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);




        attackLists = new ArrayList<>();
        GetData getData = new GetData();
        getData.execute();
        return view;

    }




public class GetData extends AsyncTask<String,String,String> {
    private  String JSON_URL="https://run.mocky.io/v3/8331fd23-68c0-44ea-95da-a7cc7602b262";

    @Override
    protected String doInBackground(String... strings) {
        String current="";
        try {
            URL url;
            HttpURLConnection urlConnection=null;
            try {
                url= new URL(JSON_URL);
                urlConnection=(HttpURLConnection) url.openConnection();
                Log.d(TAG, "http:va ");
                InputStream is=urlConnection.getInputStream();

                InputStreamReader isr = new InputStreamReader(is);

                int data= isr.read();
                while(data != -1){
                    current +=(char) data;
                    data= isr.read();
                }
                Log.d(TAG, "doInBackground() returned: " + current);
                return current;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(urlConnection !=null){
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return current;
    }

    @Override
    protected void onPostExecute(String s) {

        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("events");
             attackLists = new ArrayList<>();

            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject1= jsonArray.getJSONObject(i);
                ResultsItem model= new ResultsItem();
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



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Aggiorna i dati dell'adapter con i nuovi dati ottenuti

        PutDataIntoRecyclerView(attackLists);

    }
}

    private  void PutDataIntoRecyclerView (List<ResultsItem> attackLists){

         resultsAdapter = new Results(requireContext(),attackLists,this);
        Log.d(TAG, "attacklist put data: " + attackLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (requireContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(resultsAdapter );
        if (resultsAdapter != null) {
            resultsAdapter.updateData(attackLists);
        }
    }
    @Override
    public void onItemClick(Intent intent) {
        // Usa il FragmentManager per avviare il nuovo fragment con l'Intent
        // Esempio:
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ItemDetailsFragment.newInstance(intent))
                    .addToBackStack(null)
                    .commit();
        }
    }
}




