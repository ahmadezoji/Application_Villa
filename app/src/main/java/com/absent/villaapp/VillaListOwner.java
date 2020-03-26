package com.absent.villaapp;

import android.net.Uri;

public interface VillaListOwner {
    void filllist();
    void deleteVilla(Villa villa);
    void editVilla(Villa villa);

    String UploadCoverToServer(Uri uri);

}
