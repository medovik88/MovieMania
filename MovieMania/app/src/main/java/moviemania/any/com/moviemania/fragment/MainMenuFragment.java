package moviemania.any.com.moviemania.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.controller.FragmentController;

/**
 * Created by Юра on 23.01.2016.
 */
public class MainMenuFragment extends Fragment
{
    private Button btnMyFilms;
    private Button btnNewFilm;

    private AddNewMovieFragment addNewMovieFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu_layout, null);

        addNewMovieFragment = new AddNewMovieFragment();

        btnMyFilms = (Button)v.findViewById(R.id.btnMyFilms);
        btnNewFilm = (Button)v.findViewById(R.id.btnNewFilm);

        btnMyFilms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentController.getInstance().replace(R.id.fl_container, new MovieListFragment(), MainMenuFragment.this);
            }
        });

        btnNewFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragmentName", "MainMenuFragment");
                addNewMovieFragment.setArguments(bundle);
                FragmentController.getInstance().replace(R.id.fl_container, addNewMovieFragment, MainMenuFragment.this);
            }
        });

        return v;
    }
}
