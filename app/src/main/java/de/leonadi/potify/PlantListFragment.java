package de.leonadi.potify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.software.shell.fab.ActionButton;
import com.software.shell.fab.FloatingActionButton;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import de.leonadi.potify.models.Plant;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlantListFragment extends Fragment {

    RecyclerView plantList;
    PlantAdapter plantAdapter;

    ActionButton newButton;

    public PlantListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        plantList = (RecyclerView) rootView.findViewById(R.id.plant_list);
        newButton = (ActionButton) rootView.findViewById(R.id.action_button);


        plantAdapter = new PlantAdapter(this);
        plantList.setAdapter(plantAdapter);
        plantList.setLayoutManager(new LinearLayoutManager(getActivity()));
        plantList.setItemAnimator(new DefaultItemAnimator());

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(), PlantEditActivity.class);
                startActivityForResult(newIntent, 1);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        plantAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        plantAdapter.notifyDataSetChanged();
    }
}
