package com.example.junyoung.acointicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class WebViewFragment extends Fragment {
    private static final String TAG = "WebViewFragment";

    private String articleUrl;

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate WebViewFragment");
        super.onCreate(savedInstanceState);

        articleUrl = getActivity().getIntent().getStringExtra(DetailsActivity.ARTICLE_URL);
        Log.d(TAG, articleUrl);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout root = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        lp.setMarginStart(32);
        webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        root.addView(webView, lp);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl(articleUrl);
    }
}

