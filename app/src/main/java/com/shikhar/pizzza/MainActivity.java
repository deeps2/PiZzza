package com.shikhar.pizzza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shikhar.pizzza.model.PizzaResponse;
import com.shikhar.pizzza.model.VariantGroup;
import com.shikhar.pizzza.model.Variation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.myjson.com/";

    List<VariantGroup> mlistVariantGroup = new ArrayList<>();
    List<Variation> mlistVariations = new ArrayList<>();

    LinearLayout mLinearLayout;

    RadioGroup rg;
    RadioButton[] rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinearLayout = (LinearLayout) findViewById(R.id.activity_main);

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

                int status = response.code();

                if (status != 200) {
                    Log.d("Status code NOT Ok. ", "Status Code: " + status);
                    return ;
                }

                mlistVariantGroup = response.body().getVariants().getVariantGroups();

                for (int i = 0 ; i < mlistVariantGroup.size(); i++){

                    rg = new RadioGroup(MainActivity.this);

                   // rg.setId(Integer.parseInt(mlistVariantGroup.get(i).getGroupId()));
                    //rg.setTag(mlistVariantGroup.get(i).getName());
                    rg.setOrientation(RadioGroup.HORIZONTAL);

                    mlistVariations = mlistVariantGroup.get(i).getVariations();

                    rb = new RadioButton[mlistVariations.size()];
                    for(int j = 0; j < mlistVariations.size(); j++){



                        rb[j] = new RadioButton(MainActivity.this);
                        rb[j].setText(mlistVariations.get(j).getName());

                        rg.addView(rb[j]);
                    }

                    mLinearLayout.addView(rg);
                   // rg.setId(Integer.parseInt(mlistVariantGroup.get(i).getGroupId()));
                }
            }

            @Override
            public void onFailure(Call<PizzaResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
