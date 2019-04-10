package mypc.mad.hw4_inspiration_rewards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class InspLeaderboardActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "InspLeaderboardActivity";
    private static final int INSP_REQUEST_CODE = 1;
    private RecyclerView recyclerView; // Layout's recyclerview
    private final List<CreateProfileBean> inspLeaderArrayList = new ArrayList<>();
    CreateProfileBean bean = null;
    private InspLeadAdapter inspLeadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_leaderboard);
        //Setting ActionBar icon and title
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.arrow_with_logo);
        setTitle("  Inspiration Leaderboard");

        //RecyclerView
        recyclerView = findViewById(R.id.recycler);
        inspLeadAdapter = new InspLeadAdapter(this, inspLeaderArrayList);
        recyclerView.setAdapter(inspLeadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (intent.hasExtra("INSPLEADPROFILE")) {
            bean = (CreateProfileBean) intent.getSerializableExtra("INSPLEADPROFILE");
            Log.d(TAG, "getInspLeaderboard: " + bean.getUsername() + bean.getFirstName() + bean.getLastName() + bean.getLocation() + bean.getDepartment() + bean.getPassword() + bean.getPosition() + bean.getStory() + bean.getPointsToAward());
            try {
                new GetAllProfilesAPIAsyncTask(InspLeaderboardActivity.this).execute(bean.getStudentId(), bean.getUsername(), bean.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        CreateProfileBean beanRec=inspLeaderArrayList.get(pos);
        Log.d(TAG, "onClick: ");
        Intent intent = new Intent(this, AwardActivity.class);
        intent.putExtra("AWARDPROFILE", beanRec);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public void getAllProfilesAPIResp(List<CreateProfileBean> respBeanList) {
        Log.d(TAG, "getAllProfilesAPIResp: " + respBeanList.get(0).getUsername());
        inspLeaderArrayList.addAll(respBeanList);
        inspLeadAdapter.notifyDataSetChanged();
    }

}
