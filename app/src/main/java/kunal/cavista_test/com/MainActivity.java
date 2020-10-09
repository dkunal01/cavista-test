package kunal.cavista_test.com;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kunal.cavista_test.com.util.ApiClient;
import kunal.cavista_test.com.util.ApiInterface;
import kunal.cavista_test.com.util.DataResponse;
import kunal.cavista_test.com.util.Images;
import kunal.cavista_test.com.util.PhotoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgSearch)
    ImageView imgSearch;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.rvImageList)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    PhotoAdapter adapter;
    private int pageNumber = 1;
    private int itemCount = 50;
    private List<Images> imagesList = new ArrayList<>();
    private boolean isLoading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private int view_threshold = 50;
    private ApiInterface apiInterface;

    private String URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtSearch.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = edtSearch.getText().toString();
                if (item != null) {
                    URL = "https://api.imgur.com/3/gallery/search/1?q=" + item;
                    getImages();
                } else {
                    edtSearch.setError("Please enter text to search");
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;

                        }
                    }

                    if (!isLoading && (totalItemCount - visibleItemCount <= (pastVisibleItems + view_threshold))) {
                        pageNumber++;
                        performPagination();
                        isLoading = true;
                    }
                }

            }
        });


    }

    public void getImages() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DataResponse> call = apiInterface.getImages(URL, pageNumber, itemCount);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                List<Images> images = response.body().getImages();

                for (Images im : images) {
                    Log.e("images", "" + im.getId());
                    if (im.getId().contains("https://i.imgur.com/")) {
                        imagesList.add(im);
                    }
                }

                adapter = new PhotoAdapter(MainActivity.this, imagesList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {

                Log.e("responseError", t.toString());
            }
        });
    }

    private void performPagination() {
        progressBar.setVisibility(View.VISIBLE);
        Call<DataResponse> call = apiInterface.getImages(URL, pageNumber, itemCount);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                Log.e("response", response.body().toString());

                List<Images> images = response.body().getImages();

                adapter.addImages(images);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {

                Log.e("responseError", t.toString());
            }
        });
    }

}
