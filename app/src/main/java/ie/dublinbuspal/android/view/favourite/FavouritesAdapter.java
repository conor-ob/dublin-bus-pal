package ie.dublinbuspal.android.view.favourite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.local.entity.DetailedBusStop;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.StringUtilities;

import java.util.List;
import java.util.Locale;

public class FavouritesAdapter
        extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private final FavouritesView view;
    private List<DetailedBusStop> favouriteBusStops;

    FavouritesAdapter(FavouritesView view) {
        this.view = view;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bus_stop, parent, false);
        FavouritesViewHolder viewHolder = new FavouritesViewHolder(view);
        view.setOnClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        DetailedBusStop busStop = favouriteBusStops.get(position);
        holder.bind(busStop);
    }

    @Override
    public int getItemCount() {
        return favouriteBusStops == null ? 0 : favouriteBusStops.size();
    }

    public void setFavourites(List<DetailedBusStop> favouriteBusStops) {
        this.favouriteBusStops = favouriteBusStops;
        notifyDataSetChanged();
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

        public void bind(DetailedBusStop busStop) {
            favouriteID.setText(String.format(Locale.UK, formattedStopId, busStop.getId()));
            favouriteName.setText(busStop.getCustomName());
            if (CollectionUtilities.isNullOrEmpty(busStop.getRoutes())) {
                routes.setVisibility(View.GONE);
            } else {
                String middleDot = String.format(Locale.UK, " %s ",
                        StringUtilities.MIDDLE_DOT);
                routes.setText(StringUtilities.join(busStop.getRoutes(), middleDot));
            }
        }

        @Override
        public void onClick(View itemView) {
            DetailedBusStop favouriteBusStop = favouriteBusStops.get(getAdapterPosition());
            view.launchRealTimeActivity(favouriteBusStop.getId());
        }

    }

}
