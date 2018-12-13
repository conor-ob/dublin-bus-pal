package ie.dublinbuspal.android.view.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.util.CollectionUtilities;
import ie.dublinbuspal.android.util.StringUtilities;
import ie.dublinbuspal.model.route.Route;
import ie.dublinbuspal.model.stop.Stop;

import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TITLE = 0;
    private static final int ROUTE = 1;
    private static final int BUS_STOP = 2;

    private List<Object> searchResults;
    private final SearchQueryView view;

    SearchAdapter(SearchQueryView view) {
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE:
                View titleView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_search_title, parent, false);
                return new TitleViewHolder(titleView);
            case ROUTE:
                View routeView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_route, parent, false);
                RouteViewHolder routeViewHolder = new RouteViewHolder(routeView);
                routeView.setOnClickListener(routeViewHolder);
                return routeViewHolder;
            case BUS_STOP:
                View busStopView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_bus_stop, parent, false);
                BusStopViewHolder busStopViewHolder = new BusStopViewHolder(busStopView);
                busStopView.setOnClickListener(busStopViewHolder);
                return busStopViewHolder;
            default:
                throw new IllegalStateException(String.format(Locale.UK,
                        "Incorrect view type: %s", String.valueOf(viewType)));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object searchResult = searchResults.get(position);
        if (searchResult instanceof String && holder instanceof TitleViewHolder) {
            String text = (String) searchResult;
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.bind(text);
        } else if (searchResult instanceof Route && holder instanceof RouteViewHolder) {
            Route route = (Route) searchResult;
            RouteViewHolder routeViewHolder = (RouteViewHolder) holder;
            routeViewHolder.bind(route);
        } else if (searchResult instanceof Stop && holder instanceof BusStopViewHolder) {
            Stop busStop = (Stop) searchResult;
            BusStopViewHolder busStopViewHolder = (BusStopViewHolder) holder;
            busStopViewHolder.bind(busStop);
        }
    }

    @Override
    public int getItemCount() {
        return searchResults == null ? 0 : searchResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object searchResult = searchResults.get(position);
        if (searchResult instanceof String) {
            return TITLE;
        } else if (searchResult instanceof Route) {
            return ROUTE;
        } else if (searchResult instanceof Stop) {
            return BUS_STOP;
        }
        return super.getItemViewType(position);
    }

    void setSearchResult(List<Object> busStops) {
        this.searchResults = busStops;
        notifyDataSetChanged();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        TitleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public void bind(String text) {
            title.setText(text);
        }

    }

    public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView routeId;
        private TextView routeDescription;

        RouteViewHolder(View itemView) {
            super(itemView);
            routeId = itemView.findViewById(R.id.route_id);
            routeDescription = itemView.findViewById(R.id.route_description);
        }

        public void bind(Route route) {
            routeId.setText(route.getId());
//            if (route.getOperator().equals(Operator.GO_AHEAD_DUBLIN)) {
//                routeId.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.textColorPrimaryInverse));
//                routeId.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary)));
//            } else {
//                routeId.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.textColorPrimary));
//                routeId.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent)));
//            }
            routeDescription.setText(String.format(Locale.UK, "%s - %s",
                    route.getVariants().get(0).getOrigin(), route.getVariants().get(0).getDestination()));
        }

        @Override
        public void onClick(View itemView) {
            Object searchResult = searchResults.get(getAdapterPosition());
            if (searchResult instanceof Route) {
                Route route = (Route) searchResult;
                view.launchRouteActivity(route.getId());
            }
        }

    }

    public class BusStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView stopID;
        private TextView stopName;
        private TextView routes;
        private String formattedStopId;

        BusStopViewHolder(View itemView) {
            super(itemView);
            stopID = itemView.findViewById(R.id.stop_id);
            stopName = itemView.findViewById(R.id.stop_name);
            routes = itemView.findViewById(R.id.routes);
            formattedStopId = itemView.getContext().getString(R.string.formatted_stop_id);
        }

        public void bind(Stop busStop) {
            stopID.setText(String.format(Locale.UK, formattedStopId, busStop.id()));
            stopName.setText(busStop.name());
            if (CollectionUtilities.isNullOrEmpty(busStop.routes())) {
                routes.setVisibility(View.GONE);
            } else {
                String middleDot = String.format(Locale.UK, " %s ",
                        StringUtilities.MIDDLE_DOT);
                routes.setText(StringUtilities.join(busStop.routes(), middleDot));
            }
        }

        @Override
        public void onClick(View itemView) {
            Object searchResult = searchResults.get(getAdapterPosition());
            if (searchResult instanceof Stop) {
                Stop busStop = (Stop) searchResult;
                view.launchRealTimeActivity(busStop.id());
            }
        }

    }

}
