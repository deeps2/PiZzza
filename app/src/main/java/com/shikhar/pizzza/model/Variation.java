package com.shikhar.pizzza.model;

import com.google.gson.annotations.SerializedName;

public class Variation {

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Integer price;

    @SerializedName("default")
    private Integer _default;

    @SerializedName("id")
    private String id;

    @SerializedName("inStock")
    private Integer inStock;

    @SerializedName("isVeg")
    private Integer isVeg;

    public Variation(String name, Integer price, Integer _default, String id, Integer inStock, Integer isVeg) {
        this.name = name;
        this.price = price;
        this._default = _default;
        this.id = id;
        this.inStock = inStock;
        this.isVeg = isVeg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDefault() {
        return _default;
    }

    public void setDefault(Integer _default) {
        this._default = _default;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Integer isVeg) {
        this.isVeg = isVeg;
    }
}
