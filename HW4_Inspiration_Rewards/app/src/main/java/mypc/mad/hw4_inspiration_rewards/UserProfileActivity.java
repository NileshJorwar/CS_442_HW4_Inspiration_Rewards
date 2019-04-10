package mypc.mad.hw4_inspiration_rewards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private TextView uname;
    private TextView location;
    private TextView pointsAwarded;
    private TextView dept;
    private TextView position;
    private TextView pointsAvailable;
    private TextView storyText;
    private ImageView userPhoto;
    private TextView name;
    private String imgString = "";
    CreateProfileBean bean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        setTitle("  Your Profile");


        name = findViewById(R.id.userProfileName);
        uname = findViewById(R.id.userProfileUname);
        location = findViewById(R.id.userProfileLocation);
        pointsAwarded = findViewById(R.id.userProfilePtAwarded);
        dept = findViewById(R.id.userProfileDept);
        position = findViewById(R.id.userProfilePos);
        pointsAvailable = findViewById(R.id.userProfilePtAvail);
        storyText = findViewById(R.id.userProfileStoryText);
        userPhoto = findViewById(R.id.userProfileImage);

        Intent intent = getIntent();
        if (intent.hasExtra("USERPROFILE")) {
            bean = (CreateProfileBean) intent.getSerializableExtra("USERPROFILE");
            Log.d(TAG, "getUserProfileAct: " + bean.getUsername() + bean.getFirstName() + bean.getLastName() + bean.getLocation() + bean.getDepartment() + bean.getPassword() + bean.getPosition() + bean.getStory() + bean.getPointsToAward());
            try {
                name.setText(bean.getLastName() + ", " + bean.getFirstName());
                uname.setText("(" + bean.getUsername() + ")");
                /*if (bean.getLocation().contains(",")) {
                    location.setText(bean.getLocation().replaceAll(",", "").split("")[0] + ", " + bean.getLocation().split(" ")[1]);
                    Log.d(TAG, "onCreate: " + bean.getLocation().split(" ")[0] + ", " + bean.getLocation().split(" ")[1] + " Loca " + bean.getLocation());
                    String newString = bean.getLocation().replaceAll(",", "");
                    Log.d(TAG, "onCreate: " + bean.getLocation().replaceAll(",", ""));
                    Log.d(TAG, "onCreate: " + newString.split(",")[0] + newString.split(",")[1]);
                } else*/
                location.setText(bean.getLocation());
                //Update
                pointsAwarded.setText("  " + bean.getPointsToAward());
                pointsAvailable.setText("  " + bean.getPointsToAward());
                dept.setText("  " + bean.getDepartment());
                position.setText("  " + bean.getPosition());
                storyText.setText(bean.getStory());
                //Image Transformation
                imgString = bean.getImageBytes();
                byte[] imageBytes = Base64.decode(imgString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                userPhoto.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void makeCustomToast(Context context, String message, int time) {
        Toast toast = Toast.makeText(context, "Image Size: " + message, time);
        View toastView = toast.getView();
        toastView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView tv = toast.getView().findViewById(android.R.id.message);
        tv.setPadding(100, 50, 100, 50);
        tv.setTextColor(Color.WHITE);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.editMenu:
                Log.d(TAG, "onOptionsItemSelected: EditClick" + bean.getUsername() + bean.getFirstName() + bean.getLastName() + bean.getLocation() + bean.getDepartment() + bean.getPassword() + bean.getPosition() + bean.getStory() + bean.getPointsToAward());
                Intent editIntent = new Intent(this, EditActivity.class);
                editIntent.putExtra("EDITPROFILE", bean);
                startActivity(editIntent);
                return true;
            case R.id.dashboardMenu:
                Log.d(TAG, "onOptionsItemSelected: DashboardClick" );
                Intent dashboardIntent = new Intent(this, InspLeaderboardActivity.class);
                dashboardIntent.putExtra("INSPLEADPROFILE", bean);
                startActivity(dashboardIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userprofile_menu, menu);
        return true;
    }
}
