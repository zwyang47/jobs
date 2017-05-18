package com.dashlabs.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Map<String, String>> title_overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        title_overview = new ArrayList<>();

        // Read JSON
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("data.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Parse JSON
        try {
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);

                String title = object.getString("title");
                String overview = object.getString("overview");

                Map<String, String> list_item = new HashMap<>();
                list_item.put("title", title);
                list_item.put("overview", overview);
                title_overview.add(list_item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Show in ListView
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, title_overview, R.layout.list_item,
                new String[]{"titel", "overview"}, new int[]{R.id.title, R.id.overview});
        listView.setAdapter(adapter);
    }
}
