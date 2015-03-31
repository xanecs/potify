package de.leonadi.potify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.joda.time.DateTime;

import java.util.List;

import de.leonadi.potify.models.Plant;

/**
 * Created by leon on 30.03.15.
 */
public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    Fragment mContext;

    public PlantAdapter(Fragment context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_list_item, parent, false);
        return new ViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plant plant = Plant.listAll(Plant.class).get(position);
        holder.mNameView.setText(plant.getName());
        holder.mSpeciesView.setText(plant.getSpecies());
        holder.mId = plant.getId();
        if (plant.getNextWaterNeeded().getMillis() < DateTime.now().getMillis()) {
            holder.mImageView.setBackgroundColor(holder.mImageView.getContext().getResources().getColor(R.color.fab_material_deep_orange_500));
        } else {
            holder.mImageView.setBackgroundColor(holder.mImageView.getContext().getResources().getColor(R.color.potify_green));
        }
    }

    @Override
    public int getItemCount() {
        return Plant.listAll(Plant.class).size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements de.leonadi.potify.ViewHolder {
        public TextView mNameView;
        public TextView mSpeciesView;
        public ImageView mImageView;
        public long mId;
        public ViewHolder(View v, final Fragment context) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlantDetailActivity.launch(context, v, mId);
                }
            });
            mNameView = (TextView) v.findViewById(R.id.plant_list_item_name);
            mSpeciesView = (TextView) v.findViewById(R.id.plant_list_item_species);
            mImageView = (ImageView) v.findViewById(R.id.plant_list_item_image);
        }

    }
}
