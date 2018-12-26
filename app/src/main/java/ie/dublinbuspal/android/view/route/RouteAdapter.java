package ie.dublinbuspal.android.view.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.model.stop.Stop;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private static final int ORIGIN = 0;
    private static final int DEFAULT = 1;
    private static final int DESTINATION = 2;

    private final RouteView view;
    private List<Stop> busStops;

    RouteAdapter(RouteView view) {
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ORIGIN:
                View beginView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_route_stop_begin, parent, false);
                ViewHolder beginViewHolder = new ViewHolder(beginView);
                beginView.setOnClickListener(beginViewHolder);
                return beginViewHolder;
            case DEFAULT:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_route_stop, parent, false);
                ViewHolder viewHolder = new ViewHolder(view);
                view.setOnClickListener(viewHolder);
                return viewHolder;
            case DESTINATION:
                View endView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_route_stop_end, parent, false);
                ViewHolder endViewHolder = new ViewHolder(endView);
                endView.setOnClickListener(endViewHolder);
                return endViewHolder;
            default:
                throw new IllegalStateException(String.format(Locale.UK,
                        "Incorrect view type: %s", String.valueOf(viewType)));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop busStop = busStops.get(position);
        holder.bind(busStop, position);
    }

    @Override
    public int getItemCount() {
        return busStops == null ? 0 : busStops.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ORIGIN;
        } else if (position == busStops.size() - 1) {
            return DESTINATION;
        }
        return DEFAULT;
    }

    public void setBusStops(List<Stop> busStops) {
        this.busStops = busStops;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stopName;
        private TextView stopId;
        private TextView stopSequence;
        private String formattedStopId;

        ViewHolder(View itemView) {
            super(itemView);
            stopName = itemView.findViewById(R.id.stop_name);
            stopId = itemView.findViewById(R.id.stop_id);
            stopSequence = itemView.findViewById(R.id.stop_count);
            formattedStopId = itemView.getContext().getString(R.string.formatted_stop_id);
        }

        @Override
        public void onClick(View itemView) {
            Stop busStop = busStops.get(getAdapterPosition());
            view.launchRealTimeActivity(busStop.id());
        }

        public void bind(Stop busStop, int position) {
            stopName.setText(busStop.name());
            stopId.setText(String.format(Locale.UK, formattedStopId, busStop.id()));
            stopSequence.setText(String.format(Locale.UK, "%d/%d", position + 1,
                    busStops.size()));
        }

    }
}
