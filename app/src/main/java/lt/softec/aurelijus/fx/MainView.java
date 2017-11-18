package lt.softec.aurelijus.fx;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

public class MainView extends AppCompatActivity {

    private TextView mTextMessage;


    //switching screens
    private void switchToFragment1() {
        Fragment newFragment = new ConvertView();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, newFragment).commit();
    }

    private void switchToFragment2() {
        Fragment newFragment = new ReportView();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, newFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToFragment1();
                    return true;
                case R.id.navigation_dashboard:
                    switchToFragment2();
                    return true;
            }
            return false;
        }
    };

    //convesrion is our main start screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        // for shared preferences monitoring only
        Stetho.initializeWithDefaults(this);

        Fragment newFragment = new ConvertView();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, newFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DbInit();

    }

    //sharedPreferences initialization
    private void DbInit() {

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.contains("EUR")) {
        } else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("EUR", "1000.00");
            editor.putString("USD", "0.00");
            editor.putString("JPY", "0.00");
            editor.putString("EUR_fee", "0.00");
            editor.putString("USD_fee", "0.00");
            editor.putString("JPY_fee", "0.00");
            editor.putString("fee_count", "0");
            editor.commit();
        }

    }

}
