package ie.dublinbuspal.android.view.nearby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.LocationUtilities;
import ie.dublinbuspal.android.util.StringUtilities;
import ie.dublinbuspal.model.stop.Stop;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private final NearbyView view;
    private List<Double> distances;
    private List<Stop> busStops;

    NearbyAdapter(NearbyView view) {
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bus_stop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop busStop = busStops.get(position);
        String walkTime = LocationUtilities.getWalkTime(distances.get(position));
        holder.bind(busStop, walkTime);
    }

    @Override
    public int getItemCount() {
        return busStops == null ? 0 : busStops.size();
    }

    public void setBusStops(List<Stop> busStops) {
        this.busStops = busStops;
        notifyDataSetChanged();
    }

    void setDistances(List<Double> distances) {
        this.distances = distances;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stopID;
        private TextView stopAddress;
        private TextView routes;
        private TextView walkTime;
        private String formattedStopId;

        ViewHolder(View itemView) {
            super(itemView);
            stopID = itemView.findViewById(R.id.stop_id);
            stopAddress = itemView.findViewById(R.id.stop_name);
            routes = itemView.findViewById(R.id.routes);
            walkTime = itemView.findViewById(R.id.walk_time);
            formattedStopId = itemView.getContext().getString(R.string.formatted_stop_id);
        }

        public void bind(Stop busStop, String walkTime) {
            stopID.setText(String.format(Locale.UK, formattedStopId, busStop.id()));
            stopAddress.setText(busStop.name());
            if (CollectionUtilities.isNullOrEmpty(busStop.routes())) {
                routes.setVisibility(View.GONE);
            } else {
                String middleDot = String.format(Locale.UK, " %s ",
                        StringUtilities.MIDDLE_DOT);
                routes.setText(StringUtilities.join(busStop.routes(), middleDot));
            }
            this.walkTime.setVisibility(View.VISIBLE);
            this.walkTime.setText(walkTime);
        }

        @Override
        public void onClick(View itemView) {
            Stop busStop = busStops.get(getAdapterPosition());
            view.launchRealTimeActivity(busStop.id());
        }

    }

}
