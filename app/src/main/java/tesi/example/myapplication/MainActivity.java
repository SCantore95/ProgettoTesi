package tesi.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import tesi.example.myapplication.fragment.StatsFragment;
import tesi.example.myapplication.fragment.ItemDetailsFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.ResultsItem;
import easy.tuto.bottomnavigationfragmentdemo.R;

import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements
        ProfileFragment.ProfileDataListener {

    private static final String TAG = "mytag";
    List<ResultsItem> attackLists;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout bottomUser;
    BottomSheetDialog bottomSheetDialog;
    BottomAppBar bottomAppBar;

    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();


    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout_main_activity);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomUser=findViewById(R.id.user_details_container);
        bottomAppBar=findViewById(R.id.bottomAppBar);


        FragmentManager fragmentManager = getSupportFragmentManager();

        transaction.add(R.id.fragment_container, profile, "ProfileFragment")
                .hide(profile)
                .add(R.id.fragment_container, stats, "StatsFragment")
                .hide(stats)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {

                    case R.id.profile:
                        showBottomUser();
                        transaction.hide(stats).show(profile).commit();
                        break;
                    case R.id.stats:
                        hideBottomUser();
                        transaction.hide(profile).show(stats).commit();

                        break;

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
        if (stats.isHidden() || stats.isDetached()) {
            stats.updateCounterData(attackLists);
            Log.d(TAG, "Data passed to CounterFragment. Size w: " + attackLists.size());
        }

    }

    public void hideBottomNavigationView() {
        bottomNavigationView.setVisibility(View.GONE);
    }
    public void hideBottomNavigationAppBar() {
       bottomAppBar.setVisibility(View.GONE);
    }
    public void hideBottomUser (){
        bottomUser.setVisibility(View.GONE);
    }
   public void showBottomUser (){
        bottomUser.setVisibility(View.VISIBLE);
    }
    public void showBottomNavigationAppBar(){
        bottomAppBar.setVisibility(View.VISIBLE);
    }

    public void showBottomNavigationView(){
         bottomNavigationView.setVisibility(View.VISIBLE);
}
    @Override
    public void onBackPressed() {
        // Check if any specific fragment is visible and handle the back press accordingly
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof StatsFragment) {
            // Handle the back press for BarChartFragment, PieChartFragment
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomAppBar.setVisibility(View.VISIBLE);

            bottomUser.setVisibility(View.VISIBLE);

            getSupportFragmentManager().beginTransaction()
                    .hide(stats)
                    .hide(itemDetailsFragment)
                    .commit();
        } else {
            // If none of the specific fragments are visible, proceed with the default behavior
            super.onBackPressed();
        }
    }

}




