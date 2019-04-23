package com.shark.util.util;

import com.shark.util.util.OS.OSInfo;
import com.shark.util.util.OS.Platform;

public class SeparatorUtil {

    public static String getPathSeparatorByOS(){
        Platform platform= OSInfo.getOSname();
        if (platform==Platform.Windows){
            return ";";
        }else {
            return ":";
        }
    }

}
