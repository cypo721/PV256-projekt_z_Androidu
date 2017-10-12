package pv256.fi.muni.cz.movio2uco_422612;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSwitch;
    private static final String THEME = "mainTheme";
    private static final String APP = "movio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(APP, MODE_PRIVATE);
        final boolean mainTheme = preferences.getBoolean(THEME, false);
        if(mainTheme){
            setTheme(R.style.SecondTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);

        mButtonSwitch = (Button)findViewById(R.id.switch_theme_button);
        final SharedPreferences.Editor editor = preferences.edit();
        mButtonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(THEME, !mainTheme);
                editor.apply();
                Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
