package mypc.mad.hw4_inspiration_rewards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InspLeaderboardActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "InspLeaderboardActivity";
    private static final int INSP_REQUEST_CODE = 1;
    private RecyclerView recyclerView; // Layout's recyclerview
    private final List<InspLeaderBoardBean> inspLeaderArrayList = new ArrayList<>();
    CreateProfileBean bean = null;
    private InspLeadAdapter inspLeadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insp_leaderboard);
        //Setting ActionBar icon and title
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_with_logo);
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
        Intent rewardIntent = getIntent();
        if (intent.hasExtra("STIDLOGIN")) {
            Log.d(TAG, "getInspLeaderboard: to refresh Leaderboard" );
            try {
                new GetAllProfilesAPIAsyncTask(InspLeaderboardActivity.this).execute(rewardIntent.getStringExtra("STIDLOGIN")
                        , rewardIntent.getStringExtra("USERLOGIN"), rewardIntent.getStringExtra("PSSLOGIN"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);

        InspLeaderBoardBean beanRec=inspLeaderArrayList.get(pos);
        Log.d(TAG, "onClick: ");
        Intent intent = new Intent(this, RewardActivity.class);
        Log.d(TAG, "onClick: Login Details "+bean.getStudentId()+ " "+bean.getUsername()+ " "+bean.getPassword()+ " ");
        intent.putExtra("AWARDPROFILE", beanRec);
        intent.putExtra("STUDENTIDLOGIN",bean.getStudentId());
        intent.putExtra("PASSLOGIN",bean.getPassword());
        intent.putExtra("UNAMELOGIN",bean.getUsername());
        intent.putExtra("NAMELOGIN",bean.getFirstName()+" "+bean.getLastName());
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public void getAllProfilesAPIResp(List<InspLeaderBoardBean> respBeanList,String connectionResult) {
        Log.d(TAG, "getAllProfilesAPIResp: " + connectionResult);
        if(respBeanList!=null)
        {
            for(int i=0;i<respBeanList.size();i++)
            {
                Log.d(TAG, "getAllProfilesAPIResp: Usernames: "+respBeanList.get(i).getUsername());
            }
        }
        Collections.sort(respBeanList);
        inspLeaderArrayList.addAll(respBeanList);
        inspLeadAdapter.notifyDataSetChanged();
    }

}
