package moviemania.any.com.moviemania.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.data.DBManager;
import moviemania.any.com.moviemania.model.Movie;
import moviemania.any.com.moviemania.view.Hexagon;

/**
 * Created by Юра on 30.01.2016.
 */
public class ShowMovieFragment extends Fragment {
    private LinearLayout ll_hexagonLayout;
    private Movie currentMovie;
    private Spinner sml_spinner_genres;
    private String[] spinner_genres;
    private ArrayAdapter<String> spinnerAdapter;

    private TextView tv_sml_movie_name;
    private TextView sml_tv_acting;
    private TextView sml_tv_plot;
    private TextView sml_tv_montage;
    private TextView sml_tv_special_effects;
    private TextView sml_tv_sound_track;
    private TextView sml_tv_touch_effect;
    private TextView sml_tv_general_rate;
    private TextView sml_tv_average_rate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_movie_layout, null);
        Bundle bundle = getArguments();
        currentMovie = DBManager.getInstance().getMovieFromDBbyID(bundle.getString("movieID"));

        ll_hexagonLayout = (LinearLayout)v.findViewById(R.id.ll_hexagonLayout);
        ll_hexagonLayout.addView(new Hexagon(getActivity(), currentMovie));

        currentMovie.getGenres().add(0, getString(R.string.genres_title));
        spinner_genres = currentMovie.getGenres().toArray(new String[currentMovie.getGenres().size()]);

        sml_spinner_genres = (Spinner)v.findViewById(R.id.sml_spinner_genres);
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_genres);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sml_spinner_genres.setAdapter(spinnerAdapter);

        tv_sml_movie_name = (TextView)v.findViewById(R.id.tv_sml_movie_name);
        tv_sml_movie_name.setText(currentMovie.getName());

        sml_tv_acting = (TextView)v.findViewById(R.id.sml_tv_acting);
        sml_tv_acting.setText(currentMovie.getRate().getActing() + "");
        sml_tv_plot = (TextView)v.findViewById(R.id.sml_tv_plot);
        sml_tv_plot.setText(currentMovie.getRate().getPlot()  + "");
        sml_tv_montage = (TextView)v.findViewById(R.id.sml_tv_montage);
        sml_tv_montage.setText(currentMovie.getRate().getMontage()  + "");
        sml_tv_special_effects = (TextView)v.findViewById(R.id.sml_tv_special_effects);
        sml_tv_special_effects.setText(currentMovie.getRate().getSpecialEffects()  + "");
        sml_tv_sound_track = (TextView)v.findViewById(R.id.sml_tv_sound_track);
        sml_tv_sound_track.setText(currentMovie.getRate().getSoundTrack()  + "");
        sml_tv_touch_effect = (TextView)v.findViewById(R.id.sml_tv_touch_effect);
        sml_tv_touch_effect.setText(currentMovie.getRate().getTouchEffect()  + "");

        sml_tv_general_rate  = (TextView)v.findViewById(R.id.sml_tv_general_rate);
        sml_tv_general_rate.setText(String.format("%.1f",currentMovie.getGeneralRate()));
        sml_tv_average_rate = (TextView)v.findViewById(R.id.sml_tv_average_rate);
        sml_tv_average_rate.setText(String.format("%.1f", currentMovie.calculateAverageRate(currentMovie.getRate())));



        return v;
    }
}
