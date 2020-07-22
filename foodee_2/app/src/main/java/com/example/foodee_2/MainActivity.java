package com.example.foodee_2;

import com.example.foodee_2.R;
import com.example.foodee_2.api.ImageDownloader;
import com.example.foodee_2.api.RetrievalProcedure;
import com.example.foodee_2.data.ClassItem;
import com.example.foodee_2.ui.MenuListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SQLiteDatabase FoodeDatabase;

    private ArrayList menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        menuList = new ArrayList<ClassItem>();


        this.get_menu_list();

        recyclerView.setHasFixedSize(true);
        mAdapter = new MenuListAdapter(menuList);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(layoutManager);

    }

    public void get_menu_list()
    {
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        RetrievalProcedure task = new RetrievalProcedure();
        String result = "";

        try {
            result = task.execute("https://astried.space/others/resto/sql_get_item.php?auth=1").get();
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.i("Result====", result);

        try {
            JSONObject jsonObj = new JSONObject(result);

            String resp_status = "";
            resp_status = jsonObj.getString("success");

            if( resp_status.equals("1") )
            {
                JSONArray menuObj = jsonObj.getJSONArray("items");

                for (int i = 0; i < menuObj.length(); i++)
                {
                    JSONObject m = menuObj.getJSONObject(i);

                    ClassItem menu = new ClassItem();

                    menu.title = m.getString("title");
                    menu.thumbnail = m.getString("thumbnail");
                    menu.itemId = m.getString("item_id");
                    menu.itemId = m.getString("prices");

                    menuList.add(menu);
                }

            }else{
            }
        }catch (JSONException ex)
        {

        }

    }//get menu list
}
