package com.example.lms.fileupload;

/*Helper class to upload and retrive files*/

import android.net.Uri;

public class UploadFiles {

    public String title;
    public String url;

    public UploadFiles() {

    }

    public UploadFiles(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
