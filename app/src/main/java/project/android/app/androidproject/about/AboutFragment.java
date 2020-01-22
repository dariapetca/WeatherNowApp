package project.android.app.androidproject.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.android.app.androidproject.R;
import project.android.app.androidproject.base.BaseFragment;

/**
 * Created by Daria on 8/8/2018.
 **/
public class AboutFragment extends BaseFragment {


    public AboutFragment() { }


    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


}
