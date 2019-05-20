package ie.dublinbuspal.android.view.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.model.favourite.FavouriteStop;
import ie.dublinbuspal.util.CollectionUtils;
import ie.dublinbuspal.util.StringUtils;

public class FavouritesAdapter
        extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private final FavouritesView view;
    private List<FavouriteStop> favouriteBusStops = Collections.emptyList();

    FavouritesAdapter(FavouritesView view) {
        this.view = view;
    }

    @Override
    @NonNull
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bus_stop, parent, false);
        FavouritesViewHolder viewHolder = new FavouritesViewHolder(view);
        view.setOnClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        if (position > -1 && position < favouriteBusStops.size()) {
            FavouriteStop busStop = favouriteBusStops.get(position);
            holder.bind(busStop);
        }
    }

    @Override
    public int getItemCount() {
        return favouriteBusStops.size();
    }

    public void setFavourites(List<FavouriteStop> favouriteBusStops) {
        this.favouriteBusStops = favouriteBusStops;
        notifyDataSetChanged();
    }

    public List<FavouriteStop> getFavourites() {
        return favouriteBusStops;
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView favouriteID;
        private TextView favouriteName;
        private TextView routes;
        private String formattedStopId;

        FavouritesViewHolder(View itemView) {
            super(itemView);
            favouriteID = itemView.findViewById(R.id.stop_id);
            favouriteName = itemView.findViewById(R.id.stop_name);
            routes = itemView.findViewById(R.id.routes);
            formattedStopId = itemView.getContext().getString(R.string.formatted_stop_id);
        }

        public void bind(FavouriteStop busStop) {
            favouriteID.setText(String.format(Locale.UK, formattedStopId, busStop.getId()));
            favouriteName.setText(busStop.getName());
            if (CollectionUtils.isNullOrEmpty(busStop.routes())) {
                routes.setText(StringUtils.EMPTY_STRING);
                routes.setVisibility(View.GONE);
            } else {
                String middleDot = String.format(Locale.UK, " %s ",
                        StringUtils.MIDDLE_DOT);
                routes.setText(StringUtils.join(busStop.routes(), middleDot));
                routes.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition > -1 && adapterPosition < favouriteBusStops.size()) {
                FavouriteStop favouriteBusStop = favouriteBusStops.get(adapterPosition);
                view.launchRealTimeActivity(favouriteBusStop.getId());
            }
        }

    }

}
