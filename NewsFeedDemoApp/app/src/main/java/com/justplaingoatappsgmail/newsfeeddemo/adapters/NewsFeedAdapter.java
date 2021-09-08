package com.justplaingoatappsgmail.newsfeeddemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.justplaingoatappsgmail.newsfeeddemo.net.models.News;
import com.justplaingoatappsgmail.newsfeeddemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    private Context context;
    private List<News> newsList;

    public NewsFeedAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void addToNewsList(List<News> addedNewsList) {
        newsList.addAll(addedNewsList);
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.category.setText(news.getCategory());
        Picasso.with(context).load(news.getImage()).into(holder.picture);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.source.setText(news.getSource());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_feed_category_id)
        TextView category;
        @BindView(R.id.news_feed_picture_id)
        ImageView picture;
        @BindView(R.id.news_feed_title_id)
        TextView title;
        @BindView(R.id.news_feed_desc_id)
        TextView description;
        @BindView(R.id.news_feed_source_id)
        TextView source;

        public NewsFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
