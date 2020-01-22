package project.android.app.androidproject.util;

import java.util.Collections;
import java.util.List;

/**
 * Created by Daria on 8/10/2018.
 **/

public class MinMaxValue {

    public static Float getMin(List<? extends Double> list) { return Collections.min(list).floatValue(); }

    public static Float getMax(List<? extends Double> list) { return Collections.max(list).floatValue(); }
}