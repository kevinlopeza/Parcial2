package com.example.kevin.parcial2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kevin.parcial2.ModelsAndEntities.News;
import com.example.kevin.parcial2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>  {

    private Context context;
    private List<News> newsArray;

    private final onNewsClickHandler mClickHandler;
    public interface onNewsClickHandler{
        void onNewsClick(News mNew);
        void onNewsChecked(ImageView v, String id);
    }

    public NewsListAdapter (Context context, List<News> newsArray, onNewsClickHandler clickHandler) {
        this.context = context;
        this.newsArray = newsArray;
        mClickHandler = clickHandler;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout card;
        TextView txtTitle, txtSubtitle;
        ImageView imgPicture, btnFav;


        public NewsViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtSubtitle = itemView.findViewById(R.id.txt_subtitle);
            imgPicture = itemView.findViewById(R.id.img_cover);
            btnFav = itemView.findViewById(R.id.btn_favorite);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onNewsClick(newsArray.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return (new NewsViewHolder(v));
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        final News _new = newsArray.get(position);
        if(_new.getTitle()!=null)
            holder.txtTitle.setText(_new.getTitle().trim());
        else
            holder.txtTitle.setText(context.getResources().getString(R.string.no_title_available));


        if(_new.getBody()!=null)
            holder.txtSubtitle.setText(_new.getBody().trim());
        else
            holder.txtSubtitle.setText(context.getResources().getString(R.string.no_description_available));

        if(_new.getCoverImage() != null){
            Picasso.get().load(_new.getCoverImage())
                    .error(R.drawable.no_image)
                    .placeholder(R.drawable.no_image)
                    .into(holder.imgPicture);
        } else
            holder.imgPicture.setImageResource(R.drawable.no_image);

        if(_new.isFavorite()){
            holder.btnFav.setImageResource(R.drawable.ic_star);
            holder.btnFav.setTag("y");
        } else {
            holder.btnFav.setImageResource(R.drawable.ic_star_border);
            holder.btnFav.setTag("n");
        }

        holder.btnFav.setOnClickListener(v-> mClickHandler.onNewsChecked(holder.btnFav, _new.getId()));

    }

    @Override
    public int getItemCount() {
        return newsArray != null ? newsArray.size(): 0;
    }


}
