package mypc.mad.hw4_inspiration_rewards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class InspLeadAdapter extends RecyclerView.Adapter<InspLeadViewHolder> {
    private static final String TAG = "InspLeadAdapter";
    private List<CreateProfileBean> inspLeaderArrayList;
    private InspLeaderboardActivity inspLeaderboardActivity;

    public InspLeadAdapter(InspLeaderboardActivity inspLeaderboardActivity, List<CreateProfileBean> inspLeaderArrayList) {
        this.inspLeaderArrayList = inspLeaderArrayList;
        this.inspLeaderboardActivity = inspLeaderboardActivity;
    }

    @NonNull
    @Override
    public InspLeadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.leaderboard_entry, viewGroup, false);
        itemView.setOnClickListener(inspLeaderboardActivity);
        itemView.setOnLongClickListener(inspLeaderboardActivity);
        return new InspLeadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InspLeadViewHolder inspLeadViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        CreateProfileBean createProfileBean = inspLeaderArrayList.get(position);
        inspLeadViewHolder.inspLeadName.setText(createProfileBean.getLastName() + ", " + createProfileBean.getFirstName());
        inspLeadViewHolder.inspLeadPosDept.setText(createProfileBean.getPosition() + ", " + createProfileBean.getDepartment());
        inspLeadViewHolder.inspLeadPoints.setText(Integer.toString(createProfileBean.getPointsToAward()));
        String imgString = createProfileBean.getImageBytes();
        byte[] imageBytes = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        inspLeadViewHolder.inspLeadImge.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return inspLeaderArrayList.size();
    }
}
