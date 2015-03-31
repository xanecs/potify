package de.leonadi.potify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import de.leonadi.potify.models.Plant;

/**
 * Created by leon on 30.03.15.
 */
public class PlantDetailFragment extends Fragment {
    private SeekBar waterBar;
    private TextView nameView;
    private TextView speciesView;
    private TextView intervalView;
    private TextView lastWaterView;
    private TextView remainingView;
    private Button waterBtn;
    private long plantId;


    public PlantDetailFragment() {
    }

    public void updateView(Plant plant) {
        intervalView.setText(plant.getWaterInterval().getStandardDays() + " " + getResources().getString(R.string.days));
        lastWaterView.setText(plant.getLastWatered().toString(new DateTimeFormatterBuilder().appendYear(4,4).appendLiteral("-").appendMonthOfYear(2).appendLiteral("-").appendDayOfMonth(2).toFormatter()));
        nameView.setText(plant.getName());
        speciesView.setText(plant.getSpecies());

        long lastWatered = plant.getLastWatered().getMillis();
        waterBar.setMax(((int) plant.getNextWaterNeeded().getMillis()) - ((int) lastWatered));
        waterBar.setProgress(((int) DateTime.now().getMillis()) - ((int) lastWatered));

        remainingView.setText(new Duration(DateTime.now(), plant.getNextWaterNeeded()).getStandardDays() + " " + getResources().getString(R.string.days) + " " + getResources().getString(R.string.remaining));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_detail, container, false);

        final Plant plant = Plant.findById(Plant.class, getActivity().getIntent().getLongExtra(PlantDetailActivity.EXTRA_PLANT, 0));
        plantId = plant.getId();
        ((ToolbarActivity) getActivity()).getSupportActionBar().setTitle(plant.getName());

        waterBar = (SeekBar) rootView.findViewById(R.id.waterBar);
        waterBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        nameView = (TextView) rootView.findViewById(R.id.plant_detail_name);
        speciesView = (TextView) rootView.findViewById(R.id.plant_detail_species);
        intervalView = (TextView) rootView.findViewById(R.id.plant_detail_water);
        lastWaterView = (TextView) rootView.findViewById(R.id.plant_detail_lastwater);
        remainingView = (TextView) rootView.findViewById(R.id.plant_detail_remaining);
        waterBtn = (Button) rootView.findViewById(R.id.plant_detail_water_btn);


        updateView(plant);

        waterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setLastWatered();
                plant.save();
                updateView(plant);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent editIntent = new Intent(getActivity(), PlantEditActivity.class);
            editIntent.putExtra("ID", plantId);
            startActivityForResult(editIntent, 1);
            return true;
        }

        if (id == R.id.action_delete) {
            Plant.findById(Plant.class, plantId).delete();
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        final Plant plant = Plant.findById(Plant.class, getActivity().getIntent().getLongExtra(PlantDetailActivity.EXTRA_PLANT, 0));
        updateView(plant);
    }

}
