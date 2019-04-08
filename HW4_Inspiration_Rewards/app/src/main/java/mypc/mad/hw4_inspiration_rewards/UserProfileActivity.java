package mypc.mad.hw4_inspiration_rewards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";

    private TextView name;
    private TextView uname;
    private TextView location;
    private TextView pointsAwarded;
    private TextView dept;
    private TextView position;
    private TextView pointsAvailable;
    private TextView storyText;
    private ImageView userPhoto;
    private String imgString="";

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
            CreateProfileBean bean = (CreateProfileBean) intent.getSerializableExtra("USERPROFILE");
            Log.d(TAG, "getUserProfileAct: " + bean.getUsername()+ bean.getFirstName()+ bean.getLastName()+ bean.getLocation()+ bean.getDepartment()+ bean.getPassword()+bean.getPosition()+bean.getStory()+bean.getPointsToAward());
            try {
                name.setText(bean.getLastName() + ", " + bean.getFirstName());

                uname.setText("(" + bean.getUsername() + ")");
                location.setText(bean.getLocation());
                //Update
                pointsAwarded.setText("  "+bean.getPointsToAward());
                pointsAvailable.setText("  "+bean.getPointsToAward());
                dept.setText("  "+bean.getDepartment());
                position.setText("  "+bean.getPosition());
                storyText.setText(bean.getStory());
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


}
