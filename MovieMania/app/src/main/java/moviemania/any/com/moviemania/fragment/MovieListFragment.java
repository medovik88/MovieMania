package moviemania.any.com.moviemania.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.controller.MovieCompare;
import moviemania.any.com.moviemania.controller.RVAdapter;
import moviemania.any.com.moviemania.data.DBManager;
import moviemania.any.com.moviemania.model.Movie;
import moviemania.any.com.moviemania.model.Rate;

/**
 * Created by ASTER-NOTUS on 09.12.2015.
 */
public class MovieListFragment extends Fragment
{
    private RecyclerView movie_list_rv;
    private RVAdapter rvAdapter;
    private ArrayList<Movie> movieList;
    private ArrayList<Movie> genresFilterList;
    private LinearLayoutManager llManager;

    private Spinner mlfl_spinner_sort;
    private Spinner mlfl_spinner_filter;
    private String[] spinner_sort;
    private ArrayList<String> spinner_filter;
    private ArrayAdapter<String> spinnersortAdapter;
    private ArrayAdapter<String> spinnerfilterAdapter;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.movie_list_fragment_layout, null);
        llManager = new LinearLayoutManager(getActivity());

        final Movie movie = new Movie("");
        spinner_filter = new ArrayList<String>();
        spinner_filter =(ArrayList<String>) movie.getAllGenres().clone();
        if(!spinner_filter.get(0).equals("All"))
        {
        spinner_filter.add(0, "All");
        }
        mlfl_spinner_filter = (Spinner)v.findViewById(R.id.mlfl_spinner_filter);
        spinnerfilterAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_filter);
        spinnerfilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mlfl_spinner_filter.setAdapter(spinnerfilterAdapter);

        spinner_sort = getResources().getStringArray(R.array.spinner_sort);
        mlfl_spinner_sort = (Spinner)v.findViewById(R.id.mlfl_spinner_sort);
        spinnersortAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_sort);
        spinnersortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mlfl_spinner_sort.setAdapter(spinnersortAdapter);

        movie_list_rv =(RecyclerView) v.findViewById(R.id.movie_list_rv);
        movie_list_rv.setLayoutManager(llManager);
        movieList = DBManager.getInstance().getAllMovies();
        genresFilterList = new ArrayList<Movie>(movieList);

        rvAdapter = new RVAdapter(genresFilterList, getActivity());
        movie_list_rv.setAdapter(rvAdapter);

        mlfl_spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        sortMovieListByAlphabetIncrease(genresFilterList);
                        break;
                    case 1:
                        sortMovieListByAlphabetReduce(genresFilterList);
                        break;
                    case 2:
                        sortMovieListByGeneralRateReduce(genresFilterList);
                        break;
                    case 3:
                        sortMovieListByGeneralRateIncrease(genresFilterList);
                        break;
                    case 4:
                        sortMovieListByAverageRateReduce(genresFilterList);
                        break;
                    case 5:
                        sortMovieListByAverageRateIncrease(genresFilterList);
                        break;
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mlfl_spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                for(int i = 0; i <  spinner_filter.size(); i++) {
                    if (position == i){
                        genresFilter(genresFilterList, spinner_filter.get(i));
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return v;
    }

    private void sortMovieListByGeneralRateIncrease(ArrayList<Movie> movieList){

        for (int i = 0; i < movieList.size(); i++) {
            for (int j = i; j < movieList.size(); j++) {
                if(movieList.get(i).getGeneralRate() > movieList.get(j).getGeneralRate()){
                    Movie movie = movieList.get(i);
                    movieList.set(i, movieList.get(j));
                    movieList.set(j, movie);
                }
            }
        }
        rvAdapter.notifyDataSetChanged();
    }
    private void sortMovieListByGeneralRateReduce(ArrayList<Movie> movieList){

        for (int i = 0; i < movieList.size(); i++) {
            for (int j = i; j < movieList.size(); j++) {
                if(movieList.get(i).getGeneralRate() < movieList.get(j).getGeneralRate()){
                    Movie movie = movieList.get(i);
                    movieList.set(i, movieList.get(j));
                    movieList.set(j, movie);
                }
            }
        }
        rvAdapter.notifyDataSetChanged();
    }
    private void sortMovieListByAverageRateIncrease(ArrayList<Movie> movieList){

        for (int i = 0; i < movieList.size(); i++) {
            for (int j = i; j < movieList.size(); j++) {
                if(movieList.get(i).getAverageRate() > movieList.get(j).getAverageRate()){
                    Movie movie = movieList.get(i);
                    movieList.set(i, movieList.get(j));
                    movieList.set(j, movie);
                }
            }
        }
        rvAdapter.notifyDataSetChanged();

    }
    private void sortMovieListByAverageRateReduce(ArrayList<Movie> movieList){

        for (int i = 0; i < movieList.size(); i++) {
            for (int j = i; j < movieList.size(); j++) {
                if(movieList.get(i).getAverageRate() < movieList.get(j).getAverageRate()){
                    Movie movie = movieList.get(i);
                    movieList.set(i, movieList.get(j));
                    movieList.set(j, movie);
                }
            }
        }
        rvAdapter.notifyDataSetChanged();
    }

    private void sortMovieListByAlphabetIncrease(ArrayList<Movie> movieList){

        Collections.sort(movieList, new MovieCompare()) ;

        rvAdapter.notifyDataSetChanged();
    }

    private void sortMovieListByAlphabetReduce(ArrayList<Movie> movieList){

        Collections.sort(movieList, new MovieCompare()) ;
        Collections.reverse(movieList);

        rvAdapter.notifyDataSetChanged();
    }

    private void genresFilter(ArrayList<Movie> genresFilterList, String genre){

        genresFilterList.clear();


        for (int i = 0; i < movieList.size(); i++) {
            for (int j = 0; j < movieList.get(i).getGenres().size(); j++) {

                if(genre.equals(spinner_filter.get(0))){
                    genresFilterList.add(movieList.get(i));
                    break;
                }

                if(movieList.get(i).getGenres().get(j).equals(genre)){
                genresFilterList.add(movieList.get(i));
                }

            }
        }
        rvAdapter.notifyDataSetChanged();

    }

}
