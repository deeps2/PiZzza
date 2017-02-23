package com.shikhar.pizzza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.myjson.com/";

    List<VariantGroup> mListVariantGroup = new ArrayList<>();
    List<Variation> mListVariations = new ArrayList<>();
    List<List<ExcludeList>> mListExcludeList = new ArrayList<>();

    LinearLayout mLinearLayout;
    RadioGroup rg;
    RadioButton[] rb;

    //this List will store those radio buttons which are supposed to be hidden because of a match in exclude_list
    List<RadioButton> mHiddenButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinearLayout = (LinearLayout) findViewById(R.id.activity_main);

        callWebService();
    }

    //Retrofit Interface
    public interface PizzaApiEndpointInterface {
        @GET("bins/3b0u2")
        Call<PizzaResponse> getVariantsExcludeList();
    }

    void callWebService() {

        //create Retrofit Instance
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

                //check status code. If its NOT OK(i.e. !200) then return from the function
                if (status != 200) {
                    Log.d("Status code NOT Ok. ", "Status Code: " + status);
                    return;
                }

                //get List of variant_groups and list of exclude_list from the JSON response that you got
                mListVariantGroup = response.body().getVariants().getVariantGroups();
                mListExcludeList = response.body().getVariants().getExcludeList();

                //iterate through each variant_group inside variant_groups array
                for (int i = 0; i < mListVariantGroup.size(); i++) {

                    //create a RadioGroup for each variant_group
                    rg = new RadioGroup(MainActivity.this);
                    rg.setOrientation(RadioGroup.VERTICAL);
                    rg.setId(1000 + i); //set the ID for RadioGroup. 1000(or any random number) + i(0,1,..)

                    mLinearLayout.addView(rg);

                    //get the list of variations under each variant_group
                    mListVariations = mListVariantGroup.get(i).getVariations();

                    rb = new RadioButton[mListVariations.size()];

                    //iterate through each variation under a particular variant_group
                    for (int j = 0; j < mListVariations.size(); j++) {

                        //create  RadioButton for each variation inside a particular variant_group
                        rb[j] = new RadioButton(MainActivity.this);
                        rb[j].setText(mListVariations.get(j).getName());
                        rb[j].setId(Integer.parseInt(mListVariations.get(j).getId()));

                        //add some additional data as tag to radio button TagFormat:("group_id","radio_group_id")
                        //group_id (before , in Tag) helps us to know the group_id associated with a variation
                        //radio_group_id (after , in Tag) helps us to know the parent RadioGroup for the radio button
                        rb[j].setTag(mListVariantGroup.get(i).getGroupId() + "," + rg.getId());

                        //attach click listener to each radio buttons
                        rb[j].setOnClickListener(mThisButtonListener);

                        rg.addView(rb[j]);
                    }
                }
            }

            @Override
            public void onFailure(Call<PizzaResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    //un-hide the radio button(which were hidden because of a match in exclude_list) when a new click is performed
    void unHideButtons(String currentClickedRadioGroupId) {

        for (RadioButton rb : mHiddenButtons) {

            //make each radio button visible which were previously Invisible because of exclude_list
            rb.setVisibility(View.VISIBLE);

            //get the radio group id of that radio button which was previously invisible
            String tag = (String) rb.getTag();
            String[] parts = tag.split(",");
            String radioGroupID = parts[1];

            //if the radio group(say id:1000) of the above radio button doesn't matches the radio group(say id:2000) which is just clicked
            //then un-check all radio buttons corresponding to the radio group id:1000
            if (!currentClickedRadioGroupId.equals((radioGroupID))) {
                RadioGroup rg = (RadioGroup) findViewById(Integer.parseInt(radioGroupID));
                rg.clearCheck();
            }
        }
    }

    private View.OnClickListener mThisButtonListener = new View.OnClickListener() {

        public void onClick(View v) {

            String tag = (String) v.getTag(); //TagFormat: (group_id, radio_group_id) ex:(1,1000)
            String[] parts = tag.split(",");
            String group_id = parts[0];  //group_id of currently clicked radio button
            String radioGroupID = parts[1];

            //un-hide the radio buttons and clear the mHiddenButtons List
            unHideButtons(radioGroupID);
            mHiddenButtons.clear();

            int variation_id = v.getId(); //variation id of currently clicked radio button

            for (int i = 0; i < mListExcludeList.size(); i++) {
                //get the first,second and so on... "exclude_list" JSON array
                List<ExcludeList> excludeListPair = mListExcludeList.get(i);

                //iterate through each JSON array(above)
                for (int j = 0; j < excludeListPair.size(); j++) {

                    //compare "group_id & variation_id" of each member in JSON array
                    // with "group_id & variation_id" of currently clicked radio button
                    if (excludeListPair.get(j).getGroupId().equals(group_id) &&
                            excludeListPair.get(j).getVariationId().equals(Integer.toString(variation_id))) {

                        //if it's a match, iterate through each JSON object inside the exclude_list array
                        for (int k = 0; k < excludeListPair.size(); k++) {

                            if (k == j)
                                continue;

                            int var_id = Integer.parseInt(excludeListPair.get(k).getVariationId());

                            //find the radio button corresponding to above variation_id and make it invisible.
                            //Also add it to mHiddenButtons List.
                            RadioButton rb = (RadioButton) findViewById(var_id);
                            mHiddenButtons.add(rb);
                            rb.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }
    };
}
