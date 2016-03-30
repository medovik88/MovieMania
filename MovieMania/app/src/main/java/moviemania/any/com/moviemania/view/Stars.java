package moviemania.any.com.moviemania.view;

import android.content.Context;
import android.widget.CheckBox;

import moviemania.any.com.moviemania.R;

/**
 * Created by Юра on 21.01.2016.
 */
public class Stars extends CheckBox
{

    public Stars(Context context)
    {
        super(context);

//        setWidth(40);
        setButtonDrawable(R.drawable.star_selector);
    }
}
