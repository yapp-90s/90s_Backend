package com.yapp.ios2.service;

import com.yapp.ios2.config.info.PHOTO_TYPE;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PhotoBizService {
    // photoName : photoType(ORG, DEOCERATED, DEVELOPED )_photoUid.png
    String getFileName(PHOTO_TYPE photoType, Long photoUid){
        return photoType + "_" + photoUid + ".png";
    }


    // photoPath : /photoUid/photoName.png
    String getS3FilePath(PHOTO_TYPE photoType, Long photoUid){
        return photoUid + File.separator + getFileName(photoType, photoUid);
    }
    // photoPath : /photoUid/photoName.png
    String getS3Path(Long photoUid){
        return photoUid + File.separator;
    }
}
