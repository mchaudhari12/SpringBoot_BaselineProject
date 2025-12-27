package org.studyeasy.SpringStarter.util.Constant;

import java.io.File;

public class AppUtils {
    
    public static String get_upload_path(String fileName){
        return new File("src\\main\\resources\\static\\upload").getAbsolutePath() + "\\" + fileName;

    }
}
