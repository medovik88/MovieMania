package moviemania.any.com.moviemania.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import moviemania.any.com.moviemania.ItemClickListener;
import moviemania.any.com.moviemania.R;
import moviemania.any.com.moviemania.data.DBManager;
import moviemania.any.com.moviemania.fragment.AddNewMovieFragment;
import moviemania.any.com.moviemania.fragment.MovieListFragment;
import moviemania.any.com.moviemania.fragment.ShowMovieFragment;
import moviemania.any.com.moviemania.model.Movie;

/**
 * Created by Юра on 15.01.2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MovieViewHolder>
{
    private AlertDialog dialogEdit;
    private Activity activity;

    private AddNewMovieFragment addNewMovieFragment;

    private Button dialog_btn_remove, dialog_btn_edit, dialog_btn_cancel;

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        CardView cv_item;
        TextView tv_items_name;
        TextView tv_item_general_rate;
        TextView tv_item_average_rate;
        public ItemClickListener itemClickListener;


        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            cv_item =(CardView) itemView.findViewById(R.id.cv_item);
            tv_items_name = (TextView) itemView.findViewById(R.id.tv_items_name);
            tv_item_general_rate = (TextView) itemView.findViewById(R.id.tv_item_general_rate);
            tv_item_average_rate = (TextView) itemView.findViewById(R.id.tv_item_average_rate);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getPosition(), true);
            return false;
        }
    }

    private ArrayList<Movie> movieList;


    public RVAdapter(ArrayList<Movie> movieList, Activity activity){

        addNewMovieFragment = new AddNewMovieFragment();
        this.activity = activity;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_item, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(v);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.tv_items_name.setText(movieList.get(i).getName());
        movieViewHolder.tv_item_general_rate.setText(String.format("%.1f", movieList.get(i).getGeneralRate()));
        movieViewHolder.tv_item_average_rate.setText(String.format("%.1f", movieList.get(i).calculateAverageRate(movieList.get(i).getRate())));
        movieViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if(!isLongClick){
                Bundle bundle = new Bundle();
                bundle.putString("movieID", movieList.get(position).getId());
                ShowMovieFragment showMovieFragment = new ShowMovieFragment();
                showMovieFragment.setArguments(bundle);
                FragmentController.getInstance().replace(R.id.fl_container, showMovieFragment, new MovieListFragment());
                }
                if(isLongClick){

                    dialogBuild(position);

                }

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void dialogBuild(final int position){
        dialogEdit = new AlertDialog.Builder(activity).create();
        View view = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.change_remove_movie_dialog, null);
        dialogEdit.setView(view);

        dialog_btn_remove = (Button) view.findViewById(R.id.dialog_btn_remove);
        dialog_btn_edit = (Button) view.findViewById(R.id.dialog_btn_edit);
        dialog_btn_cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);

        dialog_btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBManager.getInstance().deleteMovieFromDB(movieList.get(position));
                movieList.remove(position);
                RVAdapter.this.notifyDataSetChanged();
                dialogEdit.dismiss();
            }
        });

        dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

        dialog_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fragmentName", "MovieListFragment");
                bundle.putString("movieIDtoUpdate", movieList.get(position).getId());
                addNewMovieFragment.setArguments(bundle);
                FragmentController.getInstance().replace(R.id.fl_container, addNewMovieFragment, new MovieListFragment());
                dialogEdit.dismiss();
            }
        });



        dialogEdit.show();
    }
}
