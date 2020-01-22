package project.android.app.androidproject.base;

import android.support.v4.app.Fragment;

import project.android.app.androidproject.MainActivity;

/**
 * Created by Daria on 8/8/2018.
 **/
public class BaseFragment extends Fragment {
    public void showProgressHUD() {
        MainActivity baseActivity = (MainActivity) getActivity();
        if (baseActivity == null) return;
        baseActivity.showProgressHUD();
    }

    public void hideProgressHUD() {
        MainActivity baseActivity = (MainActivity) getActivity();
        if (baseActivity == null) return;
        baseActivity.hideProgressHUD();
    }
}
