package com.example.test.upload;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
	
	private static final long serialVersionUID = 1L; // serialVersionUID 필드 추가

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL() {
        return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, StandardCharsets.UTF_8);
    }
    /*섬네일 이미지 반환*/
    public String getThumbnailURL() {
        return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, StandardCharsets.UTF_8);
    }

}