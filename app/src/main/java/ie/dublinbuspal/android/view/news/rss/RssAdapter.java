package ie.dublinbuspal.android.view.news.rss;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.remote.rss.xml.Item;
import ie.dublinbuspal.android.util.DateUtilities;
import ie.dublinbuspal.android.util.StringUtilities;

import java.util.List;
import java.util.Locale;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {

    private List<Item> items;

    RssAdapter() {
        super();
    }

    @Override
    public RssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_rss, parent, false);
        RssViewHolder holder = new RssViewHolder(view);
        view.setOnClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RssViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    void setItems(List<Item> items) {
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

        void bind(Item item) {
            String url = item.getLink();
            String link = String.format(Locale.UK, LINK_HTML, url, item.getTitle());
            title.setText(Html.fromHtml(link));
            title.setMovementMethod(LinkMovementMethod.getInstance());
            String desc = item.getDescription()
                    .replace(WHITE_SPACE, StringUtilities.EMPTY_STRING)
                    .replace(AMPERSAND, StringUtilities.AMPERSAND);
            description.setText(desc);
            publishDate.setText(DateUtilities.getRssItemAge(item.getPubDate()));
        }

        @Override
        public void onClick(View view) {
            Item item = items.get(getAdapterPosition());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(item.getLink()));
            view.getContext().startActivity(intent);
        }

    }

}
