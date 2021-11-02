package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.activities.MovieDetailsActivity;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView lbl_detail_fragment_title, lbl_detail_fragment_overview, lbl_detail_fragment_rating, lbl_detail_release_date;
    private String movie_id = "";
    private MovieViewModel viewModel;
    private ImageView img_poster_fragment, img_backdrop_fragment;
    private LinearLayout ll_maker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);

        movie_id = getArguments().getString("movieId");
        //lbl_movie_id = view.findViewById(R.id.lbl_movie_id_movie_details_fragment);
        lbl_detail_fragment_title = view.findViewById(R.id.lbl_title_movie_details_fragment);
        lbl_detail_fragment_overview = view.findViewById(R.id.lbl_overview_movie_details_fragment);
        lbl_detail_fragment_rating = view.findViewById(R.id.lbl_rating_movie_details_fragment);
        lbl_detail_release_date = view.findViewById(R.id.lbl_release_date_fragment);
        img_poster_fragment = view.findViewById(R.id.img_poster_movie_details_fragment);
        img_backdrop_fragment = view.findViewById(R.id.img_backdrop_fragment);
        ll_maker = view.findViewById(R.id.ll_movieProduction_detail);

        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showResultMovie);

        String movieId = getArguments().getString("movieId");
        //lbl_movie_id.setText(movieId);


        return view;
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            String img_backdrop = Const.IMG_URL + movies.getBackdrop_path();
            Glide.with(MovieDetailsFragment.this).load(img_path).into(img_poster_fragment);
            Glide.with(MovieDetailsFragment.this).load(img_backdrop).into(img_backdrop_fragment);
            lbl_detail_fragment_title.setText(movies.getTitle());
            lbl_detail_fragment_overview.setText(movies.getOverview());
            lbl_detail_fragment_rating.setText(""+movies.getVote_average());
            lbl_detail_release_date.setText(movies.getRelease_date());


            for(int i = 0; i < movies.getProduction_companies().size();i++){
                ImageView img_prod = new ImageView(ll_maker.getContext());
                String productionLogo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String productionName = movies.getProduction_companies().get(i).getName();
                if(movies.getProduction_companies().get(i).getLogo_path()==null){
                    img_prod.setImageDrawable(getResources().getDrawable(R.drawable.ic_upp_coming_24, getActivity().getTheme()));
                }else if(productionLogo != "https://image.tmdb.org/3/t/p/w500/null"){
                    Glide.with(getActivity()).load(productionLogo).into(img_prod);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250,250);
                lp.setMargins(25,0,25,0);
                img_prod.setLayoutParams(lp);
                ll_maker.addView(img_prod);
                img_prod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar = Snackbar.make(view, productionName, Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(R.id.bottom_nav_main_menu);
                        snackbar.show();
                    }
                });
            }
        }
    };
}