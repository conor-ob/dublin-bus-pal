package ie.dublinbuspal.android.view.nearby;

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
import ie.dublinbuspal.android.util.LocationUtilities;
import ie.dublinbuspal.model.stop.Stop;
import ie.dublinbuspal.util.CollectionUtils;
import ie.dublinbuspal.util.StringUtils;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private final NearbyView view;
    private List<Double> distances = Collections.emptyList();
    private List<Stop> busStops = Collections.emptyList();

    NearbyAdapter(NearbyView view) {
        this.view = view;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bus_stop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > -1 && position < busStops.size()) {
            Stop busStop = busStops.get(position);
            String walkTime;
            if (CollectionUtils.isNullOrEmpty(distances)) {
                walkTime = null;
            } else {
                walkTime = LocationUtilities.getWalkTime(distances.get(position));
            }
            holder.bind(busStop, walkTime);
        }
    }

    @Override
    public int getItemCount() {
        return busStops.size();
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
            if (CollectionUtils.isNullOrEmpty(busStop.routes())) {
                routes.setText(StringUtils.EMPTY_STRING);
                routes.setVisibility(View.GONE);
            } else {
                String middleDot = String.format(Locale.UK, " %s ",
                        StringUtils.MIDDLE_DOT);
                routes.setText(StringUtils.join(busStop.routes(), middleDot));
                routes.setVisibility(View.VISIBLE);
            }
            if (walkTime != null) {
                this.walkTime.setVisibility(View.VISIBLE);
                this.walkTime.setText(walkTime);
            } else {
                this.walkTime.setVisibility(View.GONE);
                this.walkTime.setText(StringUtils.EMPTY_STRING);
            }
        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition > -1 && adapterPosition < busStops.size()) {
                Stop busStop = busStops.get(adapterPosition);
                view.launchRealTimeActivity(busStop.id());
            }
        }

    }

}
