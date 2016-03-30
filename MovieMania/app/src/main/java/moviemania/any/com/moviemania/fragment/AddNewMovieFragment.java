package moviemania.any.com.moviemania.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.controller.FragmentController;
import moviemania.any.com.moviemania.controller.GenresListAdapter;
import moviemania.any.com.moviemania.data.DBManager;
import moviemania.any.com.moviemania.model.Movie;
import moviemania.any.com.moviemania.model.Rate;
import moviemania.any.com.moviemania.view.Stars;

/**
 * Created by Юра on 20.01.2016.
 */
public class AddNewMovieFragment extends Fragment
{

    private TextInputLayout tilMovieName;
    private EditText etName;
    private EditText etGeneralRate;

    private LinearLayout layoutActing;
    private LinearLayout layoutPlot;
    private LinearLayout layoutMontage;
    private LinearLayout layoutSpecialEffects;
    private LinearLayout layoutSoundTrack;
    private LinearLayout layoutTouchEffect;

    private ArrayList<Stars> cbActingList =  new ArrayList<Stars>();
    private ArrayList<Stars> cbPlotList =  new ArrayList<Stars>();
    private ArrayList<Stars> cbMontageList =  new ArrayList<Stars>();
    private ArrayList<Stars> cbSpecialEffectsList =  new ArrayList<Stars>();
    private ArrayList<Stars> cbSoundTrackList =  new ArrayList<Stars>();
    private ArrayList<Stars> cbTouchEffectList =  new ArrayList<Stars>();

    private TextView tvRateActing;
    private TextView tvRatePlot;
    private TextView tvRateMontage;
    private TextView tvRateSpecialEffects;
    private TextView tvRateSoundTrack;
    private TextView tvRateTouchEffect;

    private AlertDialog chooseGenresDialog;
    private ListView lvGenres;
    private GenresListAdapter genresListAdapter;

    private Button btnChooseGenres;
    private Button btnLeaveDialog;
    private Button btnBackToMaimMenu;
    private Button btnAddMovie;

    private Movie movie;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_movie_layout, null);

        Bundle bundle = getArguments();



        tilMovieName = (TextInputLayout)v.findViewById(R.id.tilMovieName);
        etName = (EditText)v.findViewById(R.id.etName);
        etGeneralRate = (EditText)v.findViewById(R.id.etGeneralRate);
        genresListAdapter = new GenresListAdapter(getActivity(), Movie.getAllGenres());

        tilMovieName.setHint("Name: ");

        tvRateActing = (TextView)v.findViewById(R.id.tvRateActing);
        tvRatePlot = (TextView)v.findViewById(R.id.tvRatePlot);
        tvRateMontage = (TextView)v.findViewById(R.id.tvRateMontage);
        tvRateSpecialEffects = (TextView)v.findViewById(R.id.tvRateSpecialEffects);
        tvRateSoundTrack = (TextView)v.findViewById(R.id.tvRateSoundTrack);
        tvRateTouchEffect = (TextView)v.findViewById(R.id.tvRateTouchEffect);

        layoutActing = (LinearLayout) v.findViewById(R.id.layoutActing);
        layoutPlot = (LinearLayout) v.findViewById(R.id.layoutPlot);
        layoutMontage = (LinearLayout) v.findViewById(R.id.layoutMontage);
        layoutSpecialEffects = (LinearLayout) v.findViewById(R.id.layoutSpecialEffects);
        layoutSoundTrack = (LinearLayout) v.findViewById(R.id.layoutSoundTrack);
        layoutTouchEffect = (LinearLayout) v.findViewById(R.id.layoutTouchEffect);


        addStars(cbActingList, tvRateActing, layoutActing);
        addStars(cbPlotList, tvRatePlot, layoutPlot);
        addStars(cbMontageList, tvRateMontage, layoutMontage);
        addStars(cbSoundTrackList, tvRateSoundTrack, layoutSoundTrack);
        addStars(cbSpecialEffectsList, tvRateSpecialEffects, layoutSpecialEffects);
        addStars(cbTouchEffectList, tvRateTouchEffect, layoutTouchEffect);



        btnChooseGenres = (Button) v.findViewById(R.id.btnChooseGenres);
        btnChooseGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callChooseGenresDialog();
            }
        });

        btnAddMovie = (Button)v.findViewById(R.id.btnAddMovie);
        if(bundle.get("fragmentName").equals("MainMenuFragment")){
        btnAddMovie.setText(getString(R.string.button_add));
        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.toast_name_error), Toast.LENGTH_SHORT).show();
                }
                if (Integer.parseInt(tvRateActing.getText().toString()) == 0 ||
                        Integer.parseInt(tvRatePlot.getText().toString()) == 0 ||
                        Integer.parseInt(tvRateMontage.getText().toString()) == 0 ||
                        Integer.parseInt(tvRateSoundTrack.getText().toString()) == 0 ||
                        Integer.parseInt(tvRateSpecialEffects.getText().toString()) == 0 ||
                        Integer.parseInt(tvRateTouchEffect.getText().toString()) == 0) {
                    Toast.makeText(getActivity(), getString(R.string.toast_stars_error), Toast.LENGTH_SHORT).show();
                }
                if (etGeneralRate.getText().toString().equals("") || Float.parseFloat(etGeneralRate.getText().toString()) == 0 || Float.parseFloat(etGeneralRate.getText().toString()) > 10) {
                    Toast.makeText(getActivity(), getString(R.string.toast_rate_error), Toast.LENGTH_SHORT).show();
                } else {
                    addMovie(null, true);
                    FragmentController.getInstance().replace(R.id.fl_container, new MovieListFragment());
                }
            }
        });
        }



        if(bundle.get("fragmentName").equals("MovieListFragment")){
            movie = DBManager.getInstance().getMovieFromDBbyID(bundle.getString("movieIDtoUpdate"));

            Log.i("LogTag", movie.getId());

            etName.setText(movie.getName());

            fillStars(cbActingList, tvRateActing,  movie.getRate().getActing());
            fillStars(cbPlotList, tvRatePlot,  movie.getRate().getPlot());
            fillStars(cbMontageList, tvRateMontage, movie.getRate().getMontage());
            fillStars(cbSpecialEffectsList, tvRateSpecialEffects,  movie.getRate().getSpecialEffects());
            fillStars(cbSoundTrackList, tvRateSoundTrack,  movie.getRate().getSoundTrack());
            fillStars(cbTouchEffectList, tvRateTouchEffect, movie.getRate().getTouchEffect());

            etGeneralRate.setText(movie.getGeneralRate() + "");

            for (int i = 0; i < Movie.ALLGENRES.size() ; i++) {
                for (int j = 0; j < movie.getGenres().size(); j++ ){
                    if(Movie.ALLGENRES.get(i).equals(movie.getGenres().get(j))){
                        genresListAdapter.marksOfCheckedElements[i] = true;
                    }
                }
            }

            btnAddMovie.setText(getString(R.string.button_update));
            btnAddMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etName.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), getString(R.string.toast_name_error), Toast.LENGTH_SHORT).show();
                    }
                    if (Integer.parseInt(tvRateActing.getText().toString()) == 0 ||
                            Integer.parseInt(tvRatePlot.getText().toString()) == 0 ||
                            Integer.parseInt(tvRateMontage.getText().toString()) == 0 ||
                            Integer.parseInt(tvRateSoundTrack.getText().toString()) == 0 ||
                            Integer.parseInt(tvRateSpecialEffects.getText().toString()) == 0 ||
                            Integer.parseInt(tvRateTouchEffect.getText().toString()) == 0) {
                        Toast.makeText(getActivity(), getString(R.string.toast_stars_error), Toast.LENGTH_SHORT).show();
                    }
                    if (etGeneralRate.getText().toString().equals("") || Float.parseFloat(etGeneralRate.getText().toString()) == 0 || Float.parseFloat(etGeneralRate.getText().toString()) > 10) {
                        Toast.makeText(getActivity(), getString(R.string.toast_rate_error), Toast.LENGTH_SHORT).show();
                    } else {
                        addMovie(movie, false);

                        FragmentController.getInstance().replace(R.id.fl_container, new MovieListFragment());
                    }

                }
            });
        }



        btnBackToMaimMenu = (Button)v.findViewById(R.id.btnBackToMaimMenu);
        btnBackToMaimMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentController.getInstance().replace(R.id.fl_container, new MainMenuFragment());
            }
        });

        return v;
    }

    private void addMovie (Movie updateMovie, boolean isAdd){

        Movie currentMovie = null;

        if(isAdd){
           currentMovie = new Movie(etName.getText().toString());
        }
        if(!isAdd){
            currentMovie = updateMovie;
            currentMovie.setName(etName.getText().toString());
        }

        Rate rate = new Rate();
        rate.setActing(Integer.parseInt(tvRateActing.getText().toString()));
        rate.setTouchEffect(Integer.parseInt(tvRateTouchEffect.getText().toString()));
        rate.setSpecialEffects(Integer.parseInt(tvRateSpecialEffects.getText().toString()));
        rate.setPlot(Integer.parseInt(tvRatePlot.getText().toString()));
        rate.setMontage(Integer.parseInt(tvRateMontage.getText().toString()));
        rate.setSoundTrack(Integer.parseInt(tvRateSoundTrack.getText().toString()));
        currentMovie.setRate(rate);
        currentMovie.setGeneralRate(Float.parseFloat(etGeneralRate.getText().toString()));
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < genresListAdapter.marksOfCheckedElements.length ; i++) {
            if(genresListAdapter.marksOfCheckedElements[i]){
                genres.add(Movie.ALLGENRES.get(i));
            }
        }


        currentMovie.setGenres(genres);
        if(isAdd){
        DBManager.getInstance().addMovieToDB(currentMovie);
        }
        if(!isAdd){
        DBManager.getInstance().updateMovieToDB(currentMovie);
        }

    }

    private void addStars(ArrayList<Stars> cbStars, final TextView textViewRate, LinearLayout layoutStars){

        for (int i = 0; i < 10; i++)
        {
            cbStars.add(new Stars(getActivity()));
            cbStars.get(i).setTag(i);
            final ArrayList<Stars> finalCbStars = cbStars;
            cbStars.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag() ;
                    textViewRate.setText((position + 1) +"");
                    for (int j = 0; j < 10; j++) {
                        if(position >= j){
                            finalCbStars.get(j).setChecked(true);}
                        if(position < j ){
                            finalCbStars.get(j).setChecked(false);
                        }
                    }
                }
            });
            layoutStars.addView(cbStars.get(i));
        }
    }

    private void fillStars(ArrayList<Stars> cbStars, final TextView textViewRate,  final int rate){

        for (int i = 0; i < rate; i++)
        {
            cbStars.get(i).setChecked(true);
            textViewRate.setText(rate + "");
        }
    }

    private void callChooseGenresDialog(){
        chooseGenresDialog = new AlertDialog.Builder(getActivity()).create();
        View view =(LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.choose_genres_dialog, null);
        lvGenres =(ListView) view.findViewById(R.id.lvGenres);
        lvGenres.setAdapter(genresListAdapter);

        chooseGenresDialog.setView(view);
        chooseGenresDialog.show();

        btnLeaveDialog = (Button)view.findViewById(R.id.btnLeaveDialog);
        btnLeaveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGenresDialog.dismiss();
            }
        });


    }
}
