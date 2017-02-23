package com.shikhar.pizzza.model;

import com.google.gson.annotations.SerializedName;

public class ExcludeList {

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("variation_id")
    private String variationId;

    public ExcludeList(String groupId, String variationId) {
        this.groupId = groupId;
        this.variationId = variationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }
}
