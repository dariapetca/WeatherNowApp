package project.android.app.androidproject.settings;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Locale;

import project.android.app.androidproject.MainActivity;
import project.android.app.androidproject.R;
import project.android.app.androidproject.base.BaseFragment;
import project.android.app.androidproject.helpers.PersistentUtil;

/**
 * Created by Daria on 8/8/2018.
 **/
public class SettingsFragment extends BaseFragment {
    private Spinner referenceSpinner, themeSpinner, languageSpinner;
    private UiModeManager uiManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiManager = (UiModeManager) getActivity().getSystemService(Context.UI_MODE_SERVICE);
    }

    public SettingsFragment() {}


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        referenceSpinner = view.findViewById(R.id.reference_system_spinner);
        referenceSpinner.setSelection(PersistentUtil.getTemperatureReference(getActivity()));
        referenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { persistChosenReference(position); }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
       // themeSpinner = view.findViewById(R.id.theme_spinner);
        //themeSpinner.setSelection(PersistentUtil.getTheme(getActivity()));
       /* themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                persistChosenTheme(position);

                setTheme(position == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
        languageSpinner = view.findViewById(R.id.language_spinner);
        languageSpinner.setSelection(PersistentUtil.getLanguage(getActivity()));
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                persistLanguage(position);
                changeLanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });*/
        return view;
    }

    private void changeLanguage() { }

    private void persistChosenReference(int position) { PersistentUtil.setTemperatureReference(getActivity(), position); }

    private void persistChosenTheme(int position) { PersistentUtil.setTheme(getActivity(), position); }

    private void persistLanguage(int position) {
        if (PersistentUtil.getLanguage(getActivity()) != position) {
            PersistentUtil.setLanguage(getActivity(), position);
            updateLocaleNoRestart(position == 1);
        }
    }


    private void updateLocaleNoRestart(boolean romanian) {
        Locale locale = null;
        if (!romanian) {
            locale = Locale.ENGLISH;
        } else {
            locale = new Locale("ro");
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
        ((MainActivity) getActivity()).restartActivity();
    }


    private void setTheme(boolean day) {
        if (day) uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        else uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }
}
