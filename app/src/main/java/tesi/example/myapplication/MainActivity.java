package tesi.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import easy.tuto.bottomnavigationfragmentdemo.R;
import tesi.example.myapplication.fragment.CounterFragment;
import tesi.example.myapplication.fragment.ProfileFragment;
import tesi.example.myapplication.fragment.StatsFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mytag";
    BottomNavigationView bottomNavigationView;
    BottomSheetDialog bottomSheetDialog;
    TextView usernameTextView;
    CounterFragment counter = new CounterFragment();

    StatsFragment stats = new StatsFragment();
    ProfileFragment profile = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Log.d(line, "ci entra null ");
        // new APITokenExample()

        //{// public void onSuccess(String response){// }//public void onError(){//             System.out.println("failure"+Log.d(a, "non ci entra: "));//}//     };
        super.onCreate(savedInstanceState);


        //if (a == null) {
        //  Log.d(TAG, "non entra nella funzione ma salta ");
        //} else {
        //  Log.d(TAG, "no  salta ");
        //}

        // Log.d(TAG, "ci entra dopo funzione ");


        setContentView(R.layout.basic_layout_main_activity);

        //usernameTextView = findViewById(R.id.email_logn_field);

        //extracted();

    //    new Thread(new Runnable() {
      //      @Override
        ////      APITokenExample apiToken = new APITokenExample();
            //    apiToken.connectionQradar();
            //}
        //}).start();


        //Log.d(TAG, "prima del lancio del metodo");


        Log.d(TAG, "dopo del lancio del metodo");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, counter).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.counter:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, counter).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profile).commit();
                        return true;
                    case R.id.stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stats).commit();
                        return true;
                }

                return false;
            }
        });

    }
}

