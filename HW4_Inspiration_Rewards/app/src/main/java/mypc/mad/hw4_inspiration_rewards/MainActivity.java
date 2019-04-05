package mypc.mad.hw4_inspiration_rewards;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RewardsPreferences preferences;
    private EditText unameView;
    private EditText passView;
    private TextView newAccView;
    private CheckBox credChkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        setTitle("  Rewards");

        //Getting Login Ids
        unameView=(EditText)findViewById(R.id.usernameView);
        passView=(EditText)findViewById(R.id.passowrdView);
        credChkBox=(CheckBox)findViewById(R.id.remCredChk);
        //Storing the Login Credentials to SharedPreferences
        preferences=new RewardsPreferences(this);

        Log.d(TAG, "onCreate: "+preferences.getValue(getString(R.string.pref_user)));

        unameView.setText(preferences.getValue(getString(R.string.pref_user)));
        passView.setText(preferences.getValue(getString(R.string.pref_pass)));
        credChkBox.setChecked(preferences.getBoolValue(getString(R.string.pref_check)));
    }

    public void saveLoginCredentials(View view)
    {
        if(credChkBox.isChecked())
        {
            preferences.save(getString(R.string.pref_user),unameView.getText().toString());
            preferences.save(getString(R.string.pref_pass),passView.getText().toString());
            preferences.saveBool(getString(R.string.pref_check),credChkBox.isChecked());
        }
    }
}
