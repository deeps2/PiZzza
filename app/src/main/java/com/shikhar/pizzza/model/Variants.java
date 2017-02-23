package com.shikhar.pizzza.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variants {

    @SerializedName("variant_groups")
    private List<VariantGroup> variantGroups = null;

    @SerializedName("exclude_list")
    private List<List<ExcludeList>> excludeList = null;

    public Variants(List<VariantGroup> variantGroups, List<List<ExcludeList>> excludeList) {
        this.variantGroups = variantGroups;
        this.excludeList = excludeList;
    }

    public List<VariantGroup> getVariantGroups() {
        return variantGroups;
    }

    public void setVariantGroups(List<VariantGroup> variantGroups) {
        this.variantGroups = variantGroups;
    }

    public List<List<ExcludeList>> getExcludeList() {
        return excludeList;
    }

    public void setExcludeList(List<List<ExcludeList>> excludeList) {
        this.excludeList = excludeList;
    }
}
