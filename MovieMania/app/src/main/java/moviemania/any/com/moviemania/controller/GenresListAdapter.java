package moviemania.any.com.moviemania.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import moviemania.any.com.moviemania.R;

/**
 * Created by Юра on 23.01.2016.
 */
public class GenresListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> genres;
    private LayoutInflater layoutInflater;
    public ArrayList<Integer> numbersOfCheckedGenres;
    public boolean[] marksOfCheckedElements = new boolean[20];


    public GenresListAdapter (Context context, ArrayList<String> genres){
        this.context = context;
        this.genres = genres;
        numbersOfCheckedGenres = new ArrayList<Integer>();
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Object getItem(int position) {
        return genres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_genres_list, parent, false);
        }

        CheckBox cbGenresItem = (CheckBox)view.findViewById(R.id.cbGenresItem);
        cbGenresItem.setTag(position);
        cbGenresItem.setChecked(marksOfCheckedElements[position]);
        cbGenresItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                marksOfCheckedElements[(Integer)compoundButton.getTag()] = isChecked;

            }
        });

        ((TextView) view.findViewById(R.id.tvGenresItem)).setText(genres.get(position));

        return view;
    }





}
