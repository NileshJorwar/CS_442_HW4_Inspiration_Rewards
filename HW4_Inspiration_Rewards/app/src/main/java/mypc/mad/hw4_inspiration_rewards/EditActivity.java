package mypc.mad.hw4_inspiration_rewards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "EditActivity";

    private EditText userNameEdit;
    private EditText passwordEdit;
    private EditText firstEdit;
    private TextView lastEdit;
    private EditText deptEdit;
    private TextView posEdit;
    private EditText storyEdit;
    private ImageView userPhoto;
    private TextView availCharsView;
    private CheckBox adminCheck;
    private String imgString="";
    CreateProfileBean bean=null;

    //To Send over API
    private String locationFromLatLong="";
    private String studentId = "A20405042";
    private String username = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private int pointsToAward =1000;
    private String department = "";
    private String story = "";
    private String position = "";
    private boolean admin = false;
    private String rewards = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        setTitle("  Edit Profile");

        userNameEdit = findViewById(R.id.editUname);
        passwordEdit = findViewById(R.id.editPass);
        adminCheck=findViewById(R.id.editChkBox);
        firstEdit = findViewById(R.id.editFirstName);
        lastEdit = findViewById(R.id.editLastName);
        deptEdit = findViewById(R.id.editDept);
        posEdit = findViewById(R.id.editPos);
        storyEdit = findViewById(R.id.editStory);
        availCharsView = findViewById(R.id.editAvailChars);
        userPhoto= findViewById(R.id.editUserPhoto);
        storyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.toString().length();
                String countText = "( " + len + " of 360 )";
                availCharsView.setText(countText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadDatafromUserProfile();
    }
    public void loadDatafromUserProfile()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("EDITPROFILE")) {
            bean = (CreateProfileBean) intent.getSerializableExtra("EDITPROFILE");
            Log.d(TAG, "loadDatafromUserProfile: " + bean.getUsername()+ bean.getFirstName()+ bean.getLastName()+ bean.getLocation()+ bean.getDepartment()+ bean.getPassword()+bean.getPosition()+bean.getStory()+bean.getPointsToAward());
            try {
                userNameEdit.setText(bean.getUsername());
                passwordEdit.setText(bean.getPassword());
                adminCheck.setChecked(false);
                firstEdit.setText(bean.getFirstName());
                lastEdit.setText(bean.getLastName());
                deptEdit.setText(bean.getDepartment());
                posEdit.setText(bean.getPosition());
                storyEdit.setText(bean.getStory());
                //Image Transformation
                imgString=bean.getImageBytes();
                byte[] imageBytes = Base64.decode(imgString,  Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                userPhoto.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: editActivity");
        switch (item.getItemId()) {
            case R.id.saveMenu:
                saveChangesDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void saveChangesDialog() {
        Log.d(TAG, "saveChangesDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Changes?");
        builder.setIcon(R.drawable.logo);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick: OK button Clicked");
                callAsyncAPI();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN);

    }

    public void callAsyncAPI() {
        username = userNameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        firstName = firstEdit.getText().toString();
        lastName = lastEdit.getText().toString();
        department = deptEdit.getText().toString();
        story = storyEdit.getText().toString();
        position = posEdit.getText().toString();
        rewards = "";
        Log.d(TAG, "callAsyncAPI: "+posEdit.getText().toString());
        //new CreateProfileAPIAsyncTask(this, new CreateProfileBean(studentId, username, password, firstName, lastName, pointsToAward, department, story, position, admin, locationFromLatLong, imgString, rewards)).execute();
    }
}
