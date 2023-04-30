package tesi.example.myapplication;




import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CounterFragment counter = new CounterFragment();
    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout_main_activity);

        bottomNavigationView  = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,counter).commit();




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.counter:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,counter).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
                        return true;
                    case R.id.stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,stats).commit();
                        return true;
                }

                return false;
            }
        });

    }
}



