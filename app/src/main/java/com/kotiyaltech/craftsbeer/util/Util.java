package com.kotiyaltech.craftsbeer.util;

import java.util.List;

/**
 * Created by hp pc on 14-04-2018.
 */

public final class Util {
    private Util() {

    }

    public static boolean listNotNull(List<?> list){
        return list != null && !list.isEmpty();
    }

    public static String createLikeQuery(String searchTerm){
        return "%"+searchTerm+"%";
    }


}
