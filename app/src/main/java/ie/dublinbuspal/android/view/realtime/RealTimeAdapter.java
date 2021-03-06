package ie.dublinbuspal.android.view.realtime;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.model.livedata.LiveData;
import ie.dublinbuspal.util.CollectionUtils;
import ie.dublinbuspal.util.StringUtils;

public class RealTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT = 0;
    private static final int EMPTY = -1;

    private List<Object> realTimeData = Collections.emptyList();
    private final RealTimeView view;
    private boolean showArrivalTime;

    RealTimeAdapter(RealTimeView view) {
        this.view = view;
        this.showArrivalTime = false;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY:
                View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_realtime_empty,
                        parent, false);
                return new EmptyRealTimeStopDataViewHolder(emptyView);
            case DEFAULT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_realtime,
                        parent, false);
                RealTimeStopDataViewHolder holder = new RealTimeStopDataViewHolder(view);
                view.setOnClickListener(holder);
                return holder;
            default:
                throw new IllegalStateException(String.format(Locale.UK,
                        "Incorrect view type: %s", String.valueOf(viewType)));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position > -1 && position < realTimeData.size()) {
            Object realTimeData = this.realTimeData.get(position);
            if (holder instanceof RealTimeStopDataViewHolder) {
                RealTimeStopDataViewHolder viewHolder = (RealTimeStopDataViewHolder) holder;
                viewHolder.bind((LiveData) realTimeData);
            }
        }
    }

    @Override
    public int getItemCount() {
        return realTimeData.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object data = realTimeData.get(position);
        if (data instanceof String) {
            return EMPTY;
        }
        return DEFAULT;
    }

    public void setRealTimeData(List<LiveData> realTimeData) {
        List<Object> copy = new ArrayList<>();
        if (realTimeData == null) {
            return;
        } else if (CollectionUtils.isNullOrEmpty(realTimeData)) {
            copy.add("EMPTY");
        }
        copy.addAll(realTimeData);
        this.realTimeData = copy;
        notifyDataSetChanged();
    }

    void setShowArrivalTime(boolean showArrivalTime) {
        this.showArrivalTime = showArrivalTime;
        notifyDataSetChanged();
    }

    public class RealTimeStopDataViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView route;
        private TextView destination;
        private TextView via;
        private TextView expectedTime;
        private ImageView animation;

        private RealTimeStopDataViewHolder(View itemView) {
            super(itemView);
            this.route = itemView.findViewById(R.id.route_id);
            this.destination = itemView.findViewById(R.id.destination);
            this.via = itemView.findViewById(R.id.via);
            this.expectedTime = itemView.findViewById(R.id.expected_time);
            this.animation = itemView.findViewById(R.id.animation_view);
        }

        public void bind(LiveData realTimeData) {
            route.setText(realTimeData.getRouteId());
            destination.setText(realTimeData.getDestination().getDestination());
            if (!StringUtils.isNullOrEmpty(realTimeData.getDestination().getVia())) {
                via.setText(realTimeData.getDestination().getVia());
                via.setVisibility(View.VISIBLE);
            } else {
                via.setText(StringUtils.EMPTY_STRING);
                via.setVisibility(View.GONE);
            }
            if (showArrivalTime) {
                expectedTime.setText(realTimeData.getDueTime().getTime());
            } else {
                expectedTime.setText(realTimeData.getDueTime().minutes());
            }
            animation.setBackgroundResource(R.drawable.anim_rtpi);
            AnimationDrawable frameAnimation = (AnimationDrawable) animation.getBackground();
            frameAnimation.start();
        }

        @Override
        public void onClick(View viewItem) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition > -1 && adapterPosition < realTimeData.size()) {
                Object item = realTimeData.get(adapterPosition);
                if (item instanceof LiveData) {
                    LiveData liveData = (LiveData) item;
                    String routeId = liveData.getRouteId();
                    view.launchRouteActivity(routeId, liveData.getOperator().getCode());
                }
            }
        }

    }

    public class EmptyRealTimeStopDataViewHolder extends RecyclerView.ViewHolder {

        EmptyRealTimeStopDataViewHolder(View itemView) {
            super(itemView);
        }

    }

}
