package com.shikhar.pizzza.model;

import com.google.gson.annotations.SerializedName;

public class PizzaResponse {

    @SerializedName("variants")

    private Variants variants;

    public PizzaResponse(Variants variants) {
        this.variants = variants;
    }

    public Variants getVariants() {
        return variants;
    }

    public void setVariants(Variants variants) {
        this.variants = variants;
    }
}
