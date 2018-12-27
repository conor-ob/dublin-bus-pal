package ie.dublinbuspal.android.view.news.rss;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import ie.dublinbuspal.model.rss.RssNews;
import ie.dublinbuspal.util.StringUtils;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {

    private List<RssNews> items = Collections.emptyList();

    RssAdapter() {
        super();
    }

    @Override
    @NonNull
    public RssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_rss, parent, false);
        RssViewHolder holder = new RssViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RssViewHolder holder, int position) {
        if (position > -1 && position < items.size()) {
            RssNews item = items.get(position);
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<RssNews> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class RssViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String LINK_HTML = "<a href=\"%s\">%s</a>";
        private static final String WHITE_SPACE = "&nbsp;";
        private static final String AMPERSAND = "&amp;";

        private TextView title;
        private TextView description;
        private TextView publishDate;

        RssViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            publishDate = itemView.findViewById(R.id.publish_date);
        }

        void bind(RssNews item) {
            String url = item.getLink();
            String link = String.format(Locale.UK, LINK_HTML, url, item.getTitle());
            title.setText(Html.fromHtml(link));
            title.setMovementMethod(LinkMovementMethod.getInstance());
            String desc = item.getDescription()
                    .replace(WHITE_SPACE, StringUtils.EMPTY_STRING)
                    .replace(AMPERSAND, StringUtils.AMPERSAND);
            description.setText(desc);
            publishDate.setText(item.getAge().getTimestamp());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition > -1 && adapterPosition < items.size()) {
                RssNews item = items.get(adapterPosition);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getLink()));
                view.getContext().startActivity(intent);
            }
        }

    }

}
