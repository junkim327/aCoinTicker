package com.example.junyoung.acointicker;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class InfoCardPresenter extends Presenter {
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();

        final BaseCardView cardView = new BaseCardView(context, null, R.style.SideInfoCardStyle);
        cardView.setFocusable(true);
        cardView.addView(LayoutInflater.from(context).inflate(R.layout.info_card, null));

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Article article = (Article) item;
        ImageView newsMainImage = viewHolder.view.findViewById(R.id.main_image);

        if (article.getUrlToImage() != null) {
            int width = (int) context.getResources().getDimension(R.dimen.info_card_image_width);
            int height = (int) context.getResources().getDimension(R.dimen.info_card_image_height);
            RequestOptions options = new RequestOptions().override(width, height);
            Glide.with(context)
                    .asBitmap()
                    .load(article.getUrlToImage())
                    .apply(options)
                    .into(newsMainImage);
        }

        TextView titleText = viewHolder.view.findViewById(R.id.title_text);
        titleText.setText(article.getTitle());

        TextView descriptionText = viewHolder.view.findViewById(R.id.description_text);
        descriptionText.setText(article.getDescription());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
