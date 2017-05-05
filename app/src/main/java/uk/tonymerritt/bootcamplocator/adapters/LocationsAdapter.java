package uk.tonymerritt.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.tonymerritt.bootcamplocator.R;
import uk.tonymerritt.bootcamplocator.holders.LocationsViewHolder;
import uk.tonymerritt.bootcamplocator.model.Klavar;

/**
 * Created by Klavar on 05/05/2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder> {

    private ArrayList<Klavar> locations;

    public LocationsAdapter(ArrayList<Klavar>locations) {
        this.locations = locations;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        final  Klavar location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load Details page
            }
        });



    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);

        return new LocationsViewHolder(card);
    }
}
