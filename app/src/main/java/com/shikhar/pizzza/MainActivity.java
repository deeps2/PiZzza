package com.shikhar.pizzza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.shikhar.pizzza.model.PizzaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.myjson.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callWebService();
    }

    public interface PizzaApiEndpointInterface {
        @GET("bins/3b0u2")
        Call<PizzaResponse> getVariantsExcludeList();
    }

    void callWebService (){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PizzaApiEndpointInterface serviceRequest = retrofit.create(PizzaApiEndpointInterface.class);

        Call<PizzaResponse> call = serviceRequest.getVariantsExcludeList();

        call.enqueue(new Callback<PizzaResponse>() {
            @Override
            public void onResponse(Call<PizzaResponse> call, Response<PizzaResponse> response) {
                
            }

            @Override
            public void onFailure(Call<PizzaResponse> call, Throwable t) {

            }
        });
    }
}
