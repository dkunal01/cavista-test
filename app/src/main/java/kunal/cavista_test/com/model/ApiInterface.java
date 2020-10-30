package kunal.cavista_test.com.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @Headers({"Authorization: Client-ID 137cda6b5008a7c"})
    @GET
    Call<DataResponse> getImages(@Url String url, @Query("page_number") int page, @Query("item_count") int item);
}
