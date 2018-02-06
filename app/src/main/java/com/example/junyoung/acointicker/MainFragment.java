package com.example.junyoung.acointicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends RowsFragment {
    private static final String TAG = "MainFragment";
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String KEYWORD = "bitcoin";
    private static final String SORT = "popularity";
    private static final String NUM_NEWS = "10";
    private static final String HEADER_NAME = "Bitcoin News";

    private ArrayObjectAdapter rowsAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateData();
    }

    private Call<NewsCollection> requestHttpApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService newsApiService = retrofit.create(NewsApiService.class);

        return newsApiService.newsCollection(KEYWORD, SORT, NUM_NEWS, ApiKey.KEY);
    }

    private void updateData() {
        Call<NewsCollection> news = requestHttpApi();

        news.enqueue(new Callback<NewsCollection>() {
            @Override
            public void onResponse(Call<NewsCollection> call, Response<NewsCollection> response) {
                NewsCollection newsCollection = response.body();

                loadRows(newsCollection.getArticleList());
            }

            @Override
            public void onFailure(Call<NewsCollection> call, Throwable t) {

            }
        });
    }

    private void loadRows(List<Article> articleList) {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        Presenter cardPresenter = new InfoCardPresenter();

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (Article article : articleList) {
            listRowAdapter.add(article);
        }
        HeaderItem header = new HeaderItem(0, HEADER_NAME);
        rowsAdapter.add(new ListRow(header, listRowAdapter));

        setAdapter(rowsAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {
                Article article = (Article) item;
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.ARTICLE_URL, article.getUrl());

                getActivity().startActivity(intent);
            }
        });
    }
}
