package com.example.foodee_2.ui;

import com.example.foodee_2.R;
import com.example.foodee_2.data.ClassItem;
import com.example.foodee_2.api.ImageDownloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuListViewHolder>
{
    public ArrayList<ClassItem> dataList;
    public String CallerName;
    public String CallerID;

    public MenuListAdapter(ArrayList<ClassItem> itemList){
        this.dataList = itemList;
        this.CallerName = "menuList";
        this.CallerID = "";
    }

    @NonNull
    @Override
    public MenuListAdapter.MenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu, parent, false);
        return new MenuListAdapter.MenuListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MenuListViewHolder holder, int position) {
        String iTitle = "";
        String iID = "";
        String imgUrl = "";


        iTitle = dataList.get(position).title;
        iID = dataList.get(position).itemId;
        imgUrl = dataList.get(position).thumbnail;


        holder.txtTitle.setText(iTitle);
        holder.txtTitle.setTag(iID);

        imgUrl = imgUrl.replaceAll("#", "/");

        String HostUrl = "https://astried.space/others/resto/images/";

        Log.i("Download Image", "start download");

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage = null;

        try{
            myImage = task.execute(HostUrl+imgUrl).get();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        holder.imageView.setImageBitmap(myImage);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MenuListViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtPrices;
        public ImageView imageView;
        public Button btnMenu;


        public MenuListViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.menu_title);
            txtPrices = (TextView) itemView.findViewById(R.id.menu_prices);
            imageView = itemView.findViewById(R.id.menu_image);
        }//constructor
    }
}

