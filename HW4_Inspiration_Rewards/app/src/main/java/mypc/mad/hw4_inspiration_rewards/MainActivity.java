package mypc.mad.hw4_inspiration_rewards;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String sId = "A20405042";
    private static final int B_REQUEST_CODE = 1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private RewardsPreferences preferences;
    private EditText unameView;
    private EditText passView;
    private TextView newAccView;
    private CheckBox credChkBox;
    private LocationManager locationManager;
    private Location currentLocation;
    private Criteria criteria;
    private static int MY_LOCATION_REQUEST_CODE = 329;
    private static int MY_EXT_STORAGE_REQUEST_CODE = 330;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        setTitle("  Rewards");

        //Setting file and location permissions
        if (isOnline())
            setFileLocationPermissions();
        else
            errorDialog("errorDialog: No Internet Connectivity!!", "No Internet Connection", "Cannot start application");
        //Getting Login Ids
        unameView = (EditText) findViewById(R.id.usernameView);
        passView = (EditText) findViewById(R.id.passowrdView);
        credChkBox = (CheckBox) findViewById(R.id.remCredChk);

        //Storing the Login Credentials to SharedPreferences
        preferences = new RewardsPreferences(this);
        unameView.setText(preferences.getValue(getString(R.string.pref_user)));
        passView.setText(preferences.getValue(getString(R.string.pref_pass)));
        credChkBox.setChecked(preferences.getBoolValue(getString(R.string.pref_check)));
    }

    public void setFileLocationPermissions()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        int permLoc=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permExt=ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permLoc!= PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (permExt!= PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS );
        }

    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public void errorDialog(String logStmt, String title, String message) {
        int d = Log.d(TAG, logStmt);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveLoginCredentials(View view) {
        if (credChkBox.isChecked()) {
            preferences.save(getString(R.string.pref_user), unameView.getText().toString());
            preferences.save(getString(R.string.pref_pass), passView.getText().toString());
            preferences.saveBool(getString(R.string.pref_check), credChkBox.isChecked());
        }
    }

    public void onLoginBtnClick(View v) {
        if(isOnline())
        {
            String uName = unameView.getText().toString();
            String pswd = passView.getText().toString();
            new LoginAPIAyncTask(this).execute(sId, uName, pswd);
        }
        else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void onNewAccCreateClick(View v) {
        Log.d(TAG, "onNewAccCreateClick: Main");
        if(isOnline())
        {
            Toast.makeText(this, "Internet Connection", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CreateActivity.class);
            startActivityForResult(intent, B_REQUEST_CODE);
        }
        else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void getLoginAPIResp(String resp) {
        Log.d(TAG, "getLoginAPIResp: " + resp);
    }
}
