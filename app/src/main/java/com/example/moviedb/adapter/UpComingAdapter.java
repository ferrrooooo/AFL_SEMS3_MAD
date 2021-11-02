package com.example.moviedb.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;
    private List<UpComing.Results> getListUpComing(){
        return listUpComing;
    }
    public void setListUpComing(List<UpComing.Results> listUpComing){
        this.listUpComing = listUpComing;
    }

    public UpComingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UpComingAdapter.UpComingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_up_coming, parent, false);
        return new UpComingAdapter.UpComingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.UpComingHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        holder.lbl_title_upcom.setText(results.getTitle());
        holder.lbl_overview_upcom.setText(results.getOverview());
        holder.lbl_realese_date_upcom.setText(results.getRelease_date());
        Glide.with(context)
                .load(Const.IMG_URL+results.getPoster_path())
                .into(holder.img_poster_upcom);
        holder.cv_upcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", ""+results.getId());
                Navigation.findNavController(view).navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public class UpComingHolder extends RecyclerView.ViewHolder {
        ImageView img_poster_upcom;
        TextView lbl_title_upcom, lbl_overview_upcom, lbl_realese_date_upcom;
        CardView cv_upcom;
        public UpComingHolder(@NonNull View itemView) {
            super(itemView);
            img_poster_upcom = itemView.findViewById(R.id.img_poster_card_upcom);
            lbl_title_upcom = itemView.findViewById(R.id.lbl_title_card_upcom);
            lbl_overview_upcom = itemView.findViewById(R.id.lbl_overview_card_upcom);
            lbl_realese_date_upcom = itemView.findViewById(R.id.lbl_releasedate_card_upcom);
            cv_upcom = itemView.findViewById(R.id.cv_card_upcom);
        }
    }
}
