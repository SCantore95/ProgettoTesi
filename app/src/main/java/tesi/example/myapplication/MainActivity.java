package tesi.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ItemDetailsFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.ResultsItem;
import tesi.example.myapplication.fragment.StatsFragment;
import tesi.example.myapplication.fragment.PieChartFragment;
import tesi.example.myapplication.fragment.BarChartFragment;
import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.Interface.StatsDataListener;

import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements
        ProfileFragment.ProfileDataListener, StatsDataListener {

    private static final String TAG = "mytag";
    List<ResultsItem> attackLists;
    BottomNavigationView bottomNavigationView;
    BottomSheetDialog bottomSheetDialog;
    //TextView usernameTextView;
    CounterFragment counter = new CounterFragment();
    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();
    PieChartFragment pieChartFragment = new PieChartFragment();
    BarChartFragment barChartFragment = new BarChartFragment();
    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basic_layout_main_activity);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        stats.setStatsDataListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

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
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.counter:
                        transaction.hide(profile).hide(stats).show(counter).commit();
                        break;
                    case R.id.profile:
                        transaction.hide(counter).hide(stats).show(profile).commit();
                        break;
                    case R.id.stats:
                        transaction.hide(counter).hide(profile).show(stats).commit();
                        // bottomNavigationView.setVisibility(View.GONE);
                        break;
                    // Add a case for BarChartFragment if necessary
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
                        transaction.add(R.id.fragment_container, pieChartFragment, "PieChartFragment");

                        getSupportFragmentManager().beginTransaction().show(pieChartFragment).addToBackStack(null).commit();
                        bottomNavigationView.setVisibility(View.GONE);



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
                       bottomNavigationView.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().show(barChartFragment).addToBackStack(null).commit();



                    }
                    break;

                }
                // Altri casi se necessario
        }
    }




    public void hideBottomNavigationView() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNavigationView(){
         bottomNavigationView.setVisibility(View.VISIBLE);
}


    @Override
    public void onBackPressed() {
        // Check if any specific fragment is visible and handle the back press accordingly
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof BarChartFragment
                || currentFragment instanceof PieChartFragment) {
            // Handle the back press for BarChartFragment, PieChartFragment, or ItemDetailsFragment
            bottomNavigationView.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .hide(barChartFragment)
                    .hide(pieChartFragment)
                    .hide(itemDetailsFragment)
                    .commit();
        } else {
            // If none of the specific fragments are visible, proceed with the default behavior
            super.onBackPressed();
        }
    }

}




