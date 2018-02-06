package com.example.junyoung.acointicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {
    public static final String ARTICLE_URL = "articleUrl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
