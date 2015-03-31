package de.leonadi.potify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.joda.time.Duration;

import de.leonadi.potify.models.Plant;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlantEditFragment extends Fragment {
    private TextView nameView;
    private TextView speciesView;
    private TextView intervalView;
    private Button saveBtn;
    private Plant plant;

    public PlantEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_edit, container, false);

        nameView = (TextView) rootView.findViewById(R.id.edit_name);
        speciesView = (TextView) rootView.findViewById(R.id.edit_species);
        intervalView = (TextView) rootView.findViewById(R.id.edit_interval);

        saveBtn = (Button) rootView.findViewById(R.id.edit_save);

        if (getActivity().getIntent().hasExtra("ID")) {
            long plantId = getActivity().getIntent().getLongExtra("ID", 0);
            plant = Plant.findById(Plant.class, plantId);
            nameView.setText(plant.getName());
            speciesView.setText(plant.getSpecies());
            intervalView.setText(String.valueOf(plant.getWaterInterval().getStandardDays()));
        } else {
            plant = new Plant();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setName(nameView.getText().toString());
                plant.setSpecies(speciesView.getText().toString());
                plant.setWaterInterval(Duration.standardDays(Long.parseLong(intervalView.getText().toString())));
                plant.save();

                getActivity().finish();
            }
        });
        return rootView;
    }
}
