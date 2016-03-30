package moviemania.any.com.moviemania.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.LinkedList;

/**
 * Created by ASTER-NOTUS on 09.12.2015.
 */
public class FragmentController
{
    private static FragmentController instance;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public LinkedList<Fragment> fragmentStack;

    private FragmentController()
    {

    }

    public void init(Activity activity)
    {
        fragmentManager = activity.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentStack = new LinkedList<Fragment>();
    }

    public static FragmentController getInstance()
    {
        if(instance == null)
        {
            instance = new FragmentController();
        }

        return instance;
    }

    public void add(int container, Fragment fragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }

    public void replace(int container, Fragment fragment) {

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }
    public void replace(int container, Fragment currentFragment, Fragment previousFragment) {
        fragmentStack.add(previousFragment);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, currentFragment);
        fragmentTransaction.commit();
    }
}
