package com.scottbishop.hotoffthedialer;

import android.net.Uri;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class ContactInfo {

    private String name;
    private String id;
    private Uri photoUri;

    ContactInfo(String id, String name, Uri photoUri) {
        this.id = id;
        this.name = name;
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }
}
