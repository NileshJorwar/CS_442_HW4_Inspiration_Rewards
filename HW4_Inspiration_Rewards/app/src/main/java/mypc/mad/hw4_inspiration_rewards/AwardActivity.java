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

public class AwardActivity extends AppCompatActivity {
    private static final String TAG = "AwardActivity";
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
        setContentView(R.layout.activity_award);
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.arrow_with_logo);

        name = findViewById(R.id.awardProfileName);
        uname = findViewById(R.id.awardProfileUname);
        location = findViewById(R.id.awardProfileLocation);
        pointsAwarded = findViewById(R.id.awardProfilePtAwarded);
        dept = findViewById(R.id.awardProfileDept);
        position = findViewById(R.id.awardProfilePos);
        pointsAvailable = findViewById(R.id.awardProfilePtAvail);
        storyText = findViewById(R.id.awardProfileStoryText);
        userPhoto = findViewById(R.id.userProfileImage);

        Intent intent = getIntent();
        if (intent.hasExtra("AWARDPROFILE")) {
            bean = (CreateProfileBean) intent.getSerializableExtra("AWARDPROFILE");
            Log.d(TAG, "getAwardProfileAct: " + bean.getUsername() + bean.getFirstName() + bean.getLastName() + bean.getLocation() + bean.getDepartment() + bean.getPassword() + bean.getPosition() + bean.getStory() + bean.getPointsToAward());
            try {
                name.setText(bean.getLastName() + ", " + bean.getFirstName());
                uname.setText("(" + bean.getUsername() + ")");
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
}
