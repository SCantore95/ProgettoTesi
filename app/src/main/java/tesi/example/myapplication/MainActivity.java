package tesi.example.myapplication;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.ResultsItem;
import tesi.example.myapplication.fragment.StatsFragment;
import easy.tuto.bottomnavigationfragmentdemo.R;




import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.ResultsItem;
import tesi.example.myapplication.fragment.StatsFragment;
import easy.tuto.bottomnavigationfragmentdemo.R;

public class MainActivity extends AppCompatActivity implements ProfileFragment.ProfileDataListener {
    private static final String TAG = "mytag";
    BottomNavigationView bottomNavigationView;
    BottomSheetDialog bottomSheetDialog;
    TextView usernameTextView;
    CounterFragment counter = new CounterFragment();
    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout_main_activity);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Inizializza i fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, profile, "ProfileFragment")
                .hide(profile)
                .add(R.id.fragment_container, counter, "CounterFragment")
                .hide(counter)
                .add(R.id.fragment_container, stats, "StatsFragment")
                .hide(stats)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.counter:
                        transaction.hide(profile).hide(stats).show(counter).commit();
                        return true;
                    case R.id.profile:
                        transaction.hide(counter).hide(stats).show(profile).commit();
                        return true;
                    case R.id.stats:
                        transaction.hide(counter).hide(profile).show(stats).commit();
                        return true;
                }

                return false;
            }
        });
    }

    // Implementa il metodo dell'interfaccia per ricevere dati da ProfileFragment
    @Override
    public void onDataReceived(String data) {
        // Implementa la logica per gestire i dati ricevuti da ProfileFragment
        // Può essere vuoto se non è necessario gestire questa callback
    }

    @Override
    public void onProfileDataAvailable(List<ResultsItem> attackLists) {
        // Chiamato quando i dati sono disponibili in ProfileFragment
        // Puoi ora passare questi dati a CounterFragment o fare qualsiasi altra operazione necessaria
        if (counter.isVisible()) {
            counter.updateCounterData(attackLists);
            Log.d(TAG, "Data passed to CounterFragment. Size: " + attackLists.size());
        }
    }
}
