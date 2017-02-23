package com.shikhar.pizzza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shikhar.pizzza.model.ExcludeList;
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

import static android.media.CamcorderProfile.get;
import static android.os.Build.ID;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.myjson.com/";

    List<VariantGroup> mlistVariantGroup = new ArrayList<>();
    List<Variation> mlistVariations = new ArrayList<>();
    List<List<ExcludeList>> mlistExcludeList = new ArrayList<>();

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
                mlistExcludeList  =  response.body().getVariants().getExcludeList();

                for (int i = 0 ; i < mlistVariantGroup.size(); i++){

                    rg = new RadioGroup(MainActivity.this);
                    rg.setOrientation(RadioGroup.HORIZONTAL);
                    mLinearLayout.addView(rg);
                    //rg.setId(Integer.parseInt(mlistVariantGroup.get(i).getGroupId()));

                    //int id = rg.getId();
                    //rg.setTag(mlistVariantGroup.get(i).getName());


                    mlistVariations = mlistVariantGroup.get(i).getVariations();

                    rb = new RadioButton[mlistVariations.size()];
                    for(int j = 0; j < mlistVariations.size(); j++){

                        rb[j] = new RadioButton(MainActivity.this);
                        rb[j].setText(mlistVariations.get(j).getName());

                        rb[j].setId(Integer.parseInt(mlistVariations.get(j).getId()));
                        rb[j].setTag(mlistVariantGroup.get(i).getGroupId());

                        rb[j].setOnClickListener(mThisButtonListener);


                        rg.addView(rb[j]);
                    }


                   // rg.setId(Integer.parseInt(mlistVariantGroup.get(i).getGroupId()));
                    rg.setTag(mlistVariantGroup.get(i).getGroupId());
                }
            }

            @Override
            public void onFailure(Call<PizzaResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    private View.OnClickListener mThisButtonListener = new View.OnClickListener() {
        public void onClick(View v) {

            //v.setVisibility(View.GONE);
            String group_id = (String)v.getTag();
            int variation_id = v.getId();

          //  Toast.makeText(MainActivity.this, "G_Id/Tag: " + group_id +" Variation_ID: " + variation_id, Toast.LENGTH_SHORT).show();

            ExcludeList element = new ExcludeList(group_id, Integer.toString(variation_id));

            //Toast.makeText(MainActivity.this, element.getGroupId()+" "+element.getVariationId(), Toast.LENGTH_SHORT).show();


            for(int i = 0; i < mlistExcludeList.size(); i++){
                List<ExcludeList> excludeListPair = mlistExcludeList.get(i);

                for(int j = 0; j < excludeListPair.size(); j++) {

                    if (excludeListPair.get(j).getGroupId().equals(group_id) &&
                            excludeListPair.get(j).getVariationId().equals(Integer.toString(variation_id))) {


                    //if(excludeListPair.contains(element)){
                    Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
                    // }

                        for( int k = 0; k < excludeListPair.size(); k++){

                            if(k == j)
                                continue;

                            String g_id = excludeListPair.get(k).getGroupId();
                            int var_id = Integer.parseInt(excludeListPair.get(k).getVariationId());

                            RadioButton rd = (RadioButton)findViewById(var_id);
                            if(rd.getTag().equals(g_id)){
                               rd.setVisibility(View.GONE);
                            }

                        }

                    }
                }
            }
        }
    };
}
