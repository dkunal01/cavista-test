package kunal.cavista_test.com.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {

    @SerializedName("data")
    List<Images> Images;

    public List<Images> getImages() {
        return Images;
    }
}


