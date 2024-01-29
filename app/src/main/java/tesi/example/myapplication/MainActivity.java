package tesi.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.ResultsItem;
import tesi.example.myapplication.fragment.StatsFragment;
import tesi.example.myapplication.fragment.PieChartFragment;
import tesi.example.myapplication.fragment.BarChartFragment;
import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.StatsDataListener;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.ProfileDataListener, StatsDataListener {

    private static final String TAG = "mytag";
    List<ResultsItem> attackLists;
    public BottomNavigationView bottomNavigationView;
    BottomSheetDialog bottomSheetDialog;
    //TextView usernameTextView;
    CounterFragment counter = new CounterFragment();
    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();
    PieChartFragment pieChartFragment = new PieChartFragment();
    BarChartFragment barChartFragment = new BarChartFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout_main_activity);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        stats.setStatsDataListener(this);


        transaction.add(R.id.fragment_container, profile, "ProfileFragment")
                .hide(profile)
                .add(R.id.fragment_container, counter, "CounterFragment")
                .hide(counter)
                .add(R.id.fragment_container, stats, "StatsFragment")
                .hide(stats)
                .add(R.id.fragment_container, pieChartFragment, "PieChartFragment")
                .hide(pieChartFragment)
                .add(R.id.fragment_container, barChartFragment, "BarChartFragment")
                .hide(barChartFragment)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.counter:

                        transaction.hide(profile).hide(stats).show(counter).commit();

                        break;
                    case R.id.profile:

                        transaction.hide(counter).hide(stats).show(profile).commit();

                        break;
                    case R.id.stats:

                        transaction.hide(counter).hide(profile).show(stats).commit();

                        break;
                    // Aggiungi un caso per BarChartFragment se necessario
            /* case R.id.bar_chart:
                transaction.hide(stats).show(barChartFragment).commit();
                break; */
                }

                return true;
            }
        });

    }

    @Override
    public void onDataReceived(String data) {
        // Implementa la logica per gestire i dati ricevuti da ProfileFragment
    }

    @Override
    public void onProfileDataAvailable(List<ResultsItem> attackLists) {
        if (counter.isHidden() || counter.isDetached()) {
            counter.updateCounterData(attackLists);
            Log.d(TAG, "Data passed to CounterFragment. Size w: " + attackLists.size());
        }
        if (stats.isHidden() || stats.isDetached()) {
            stats.onStatsDataAvailable(attackLists);
            Log.d(TAG, "Data passed to StatsFragment. Size: " + attackLists.size());
        }
    }

    @Override
    public void onStatsDataAvailable(List<ResultsItem> data) {





    }

    @Override
    public void onStatsButtonClick(int buttonId) {
        switch (buttonId) {
            case StatsDataListener.BUTTON_PIE_CHART:
                // Verifica che pieChartFragment non sia nullo
                if (pieChartFragment != null) {
                    // Aggiorna i dati del grafico a torta nel fragment
                    pieChartFragment.setPieChartData(stats.getCurrentData());

                    if (!pieChartFragment.isAdded()) {
                        transaction.add(R.id.fragment_container, pieChartFragment, "PieChartFragment");
                        transaction.show(pieChartFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    } else {
                        // Se il fragment è già stato aggiunto, mostra semplicemente il fragment
                        getSupportFragmentManager().beginTransaction().hide(stats).commit();
                        getSupportFragmentManager().beginTransaction().show(pieChartFragment).addToBackStack(null).commit();
                       // bottomNavigationView.setVisibility(View.GONE);

                    }
                }
                break;
            case StatsDataListener.BUTTON_BAR_CHART:
                // Verifica che pieChartFragment non sia nullo
                if (barChartFragment != null) {
                    // Aggiorna i dati del grafico a torta nel fragment
                    barChartFragment.setBarChartData(stats.getCurrentData());

                    if (!barChartFragment.isAdded()) {
                        transaction.add(R.id.fragment_container, barChartFragment, "BarChartFragment");

                        transaction.show(barChartFragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    } else {
                        // Se il fragment è già stato aggiunto, mostra semplicemente il fragment
                        getSupportFragmentManager().beginTransaction().hide(stats).commit();
                        //bottomNavigationView.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().show(barChartFragment).addToBackStack(null).commit();
                        //bottomNavigationView.setVisibility(View.VISIBLE);


                    }
                    break;

                }
                // Altri casi se necessario
        }
    }
}

