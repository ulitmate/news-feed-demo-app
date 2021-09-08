package com.justplaingoatappsgmail.newsfeeddemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.justplaingoatappsgmail.newsfeeddemo.net.NewsFeedApiService;
import com.justplaingoatappsgmail.newsfeeddemo.net.RestClient;
import com.justplaingoatappsgmail.newsfeeddemo.net.models.News;
import com.justplaingoatappsgmail.newsfeeddemo.adapters.NewsFeedAdapter;
import com.justplaingoatappsgmail.newsfeeddemo.R;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class NewsFeedActivity extends AppCompatActivity {

    private NewsFeedAdapter newsFeedAdapter;
    private List<News> newsList;
    private NewsFeedApiService newsFeedApiService;
    private LinearLayoutManager linearLayoutManager;
    private int start = 0;

    @BindView(R.id.news_feed_recycler_view_id)
    RecyclerView newsFeedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_activity);

        ButterKnife.bind(this);

        newsList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        newsFeedApiService = RestClient.getSteamApiInterface();
        newsFeedRecyclerView.setLayoutManager(linearLayoutManager);

        newsFeedApiService.getNews(start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<News>>() {
                    @Override
                    public void onNext(List<News> news) {
                        newsList.addAll(news);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("NewsFeed", "Error: " + t.getLocalizedMessage().toString());
                    }

                    @Override
                    public void onComplete() {
                        start += 5;
                        newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(), newsList);
                        newsFeedRecyclerView.setAdapter(newsFeedAdapter);
                        newsFeedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if(isLastItemDisplaying(recyclerView)) {
                                    newsFeedApiService.getNews(start)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSubscriber<List<News>>() {
                                                @Override
                                                public void onNext(List<News> news) {
                                                    newsFeedAdapter.addToNewsList(news);
                                                }

                                                @Override
                                                public void onError(Throwable t) {

                                                }

                                                @Override
                                                public void onComplete() {
                                                    start += 5;
                                                    newsFeedAdapter.notifyDataSetChanged();
                                                }
                                            });
                                }
                            }
                        });
                    }
                });
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

}
