package com.kotiyaltech.craftsbeer.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp pc on 30-06-2018.
 */
@Entity
public class BeerItem {
    @SerializedName("abv")
    @Expose
    @ColumnInfo
    private String abv;
    @SerializedName("ibu")
    @Expose
    @ColumnInfo
    private String ibu;
    @SerializedName("id")
    @Expose
    @ColumnInfo
    @PrimaryKey
    private Integer id;
    @SerializedName("name")
    @Expose
    @ColumnInfo
    private String name;
    @SerializedName("style")
    @Expose
    @ColumnInfo
    private String style;
    @SerializedName("ounces")
    @Expose
    @ColumnInfo
    private Double ounces;

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Double getOunces() {
        return ounces;
    }

    public void setOunces(Double ounces) {
        this.ounces = ounces;
    }

}
