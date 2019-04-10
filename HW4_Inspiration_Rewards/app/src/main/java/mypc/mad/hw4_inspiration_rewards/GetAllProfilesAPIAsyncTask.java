package mypc.mad.hw4_inspiration_rewards;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

public class GetAllProfilesAPIAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetAllProfilesAPIAsyncTask";
    private InspLeaderboardActivity inspLeaderboardActivity;
    private final String baseurlAdress = "http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private final String loginURL = "/allprofiles";
    private List<CreateProfileBean> inspLeaderArrayList = new ArrayList<>();

    public GetAllProfilesAPIAsyncTask(InspLeaderboardActivity inspLeaderboardActivity) {
        this.inspLeaderboardActivity = inspLeaderboardActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String stuId = strings[0];
        String uName = strings[1];
        String pswd = strings[2];

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("studentId", stuId);
            jsonObject.put("username", uName);
            jsonObject.put("password", pswd);

            String ab = doAPICall(jsonObject);
            Log.d(TAG, "doInBackground: " + ab);
            return doAPICall(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String doAPICall(JSONObject jsonObject) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            String urlString = baseurlAdress + loginURL;  // Build the full URL

            Uri uri = Uri.parse(urlString);    // Convert String url to URI
            URL url = new URL(uri.toString()); // Convert URI to URL

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");  // POST - others might use PUT, DELETE, GET

            // Set the Content-Type and Accept properties to use JSON data
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            // Write the JSON (as String) to the open connection
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();

            int responseCode = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            // If successful (HTTP_OK)
            if (responseCode == HTTP_OK) {

                // Read the results - use connection's getInputStream
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while (null != (line = reader.readLine())) {
                    result.append(line).append("\n");
                }

                // Return the results (to onPostExecute)
                return result.toString();

            } else {
                // Not HTTP_OK - some error occurred - use connection's getErrorStream
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while (null != (line = reader.readLine())) {
                    result.append(line).append("\n");
                }

                // Return the results (to onPostExecute)
                return result.toString();
            }

        } catch (Exception e) {
            // Some exception occurred! Log it.
            Log.d(TAG, "doAuth: " + e.getClass().getName() + ": " + e.getMessage());

        } finally { // Close everything!
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream: " + e.getMessage());
                }
            }
        }
        return "Some error has occurred"; // Return an error message if Exception occurred
    }

    @Override
    protected void onPostExecute(String connectionResult) {
        Log.d(TAG, "onPostExecute: " );
        CreateProfileBean bean = null;
        try {
            JSONArray jsonArray = new JSONArray(connectionResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String studentId=jsonObject.getString("studentId");
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String firstName = jsonObject.getString("firstName");
                String lastName = jsonObject.getString("lastName");
                int pointsToAward = jsonObject.getInt("pointsToAward");
                String department = jsonObject.getString("department");
                String story = jsonObject.getString("story");
                String position = jsonObject.getString("position");
                boolean admin = jsonObject.getBoolean("admin");
                String location = jsonObject.getString("location");
                String rewards = jsonObject.getString("rewards");
                String imageBytes = jsonObject.getString("imageBytes");
                bean = new CreateProfileBean(studentId, username, password, firstName, lastName, pointsToAward, department, story, position, admin, location, imageBytes, rewards);
                inspLeaderArrayList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onPostExecute: " + inspLeaderArrayList.size());
        if (connectionResult.contains("error")) // If there is "error" in the results...
            inspLeaderboardActivity.getAllProfilesAPIResp(null);
        else
            inspLeaderboardActivity.getAllProfilesAPIResp(inspLeaderArrayList);


    }
}
