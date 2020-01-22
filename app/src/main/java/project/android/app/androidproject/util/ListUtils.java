package project.android.app.androidproject.util;

import java.util.List;

/**
 * Created by Daria on 8/10/2018.
 **/

public class ListUtils {

    public static boolean checkListEmpty(List<?> arrayList) {
        if (arrayList == null) return true;
        else if (arrayList.isEmpty()) return true;
        return false;
    }
}