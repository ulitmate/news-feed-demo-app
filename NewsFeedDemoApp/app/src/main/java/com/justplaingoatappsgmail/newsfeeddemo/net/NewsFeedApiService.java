package com.justplaingoatappsgmail.newsfeeddemo.net;

import com.justplaingoatappsgmail.newsfeeddemo.net.models.News;
import java.util.List;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsFeedApiService {

    @GET("news")
    Flowable<List<News>> getNews(@Query("start") int start);

}
