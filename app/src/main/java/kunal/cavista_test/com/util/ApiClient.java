package kunal.cavista_test.com.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String BASE_URL = "https://api.imgur.com/3/gallery/search/";

    public static Retrofit retrofit = null;

    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();


    public static Retrofit getApiClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;
    }
}
