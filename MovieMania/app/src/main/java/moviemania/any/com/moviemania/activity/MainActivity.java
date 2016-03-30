package moviemania.any.com.moviemania.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.controller.FragmentController;
import moviemania.any.com.moviemania.data.DBManager;
import moviemania.any.com.moviemania.fragment.AddNewMovieFragment;
import moviemania.any.com.moviemania.fragment.MainMenuFragment;
import moviemania.any.com.moviemania.fragment.MovieListFragment;
import moviemania.any.com.moviemania.model.Movie;
import moviemania.any.com.moviemania.model.Rate;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        DBManager.getInstance().init(this);

        // FragmentController initializing
        FragmentController.getInstance().init(this);

        setContentView(R.layout.main_activity_layout);


        Movie.fillGenres();

        // Add fragment with list of movies on main Activity
        FragmentController.getInstance().add(R.id.fl_container, new MainMenuFragment());
    }

    @Override
    public void onBackPressed() {
        LinkedList<Fragment> fragmentStack = FragmentController.getInstance().fragmentStack;
        if(fragmentStack.isEmpty()){
            super.onBackPressed();
        }
        else{
            FragmentController.getInstance().replace(R.id.fl_container, fragmentStack.get(fragmentStack.size() - 1));
            fragmentStack.remove(fragmentStack.get(fragmentStack.size()-1));
        }
    }
}
