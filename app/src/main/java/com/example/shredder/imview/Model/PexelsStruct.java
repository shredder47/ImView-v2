package com.example.shredder.imview.Model;

public class PexelsStruct {

    private String original;
    private String large;
    private String large2x;
    private String medium;
    private String small;
    private String portrait;

    public PexelsStruct(String original, String large, String large2x, String medium, String small,String portrait) {
        this.original = original;
        this.large = large;
        this.large2x = large2x;
        this.medium = medium;
        this.small = small;
        this.portrait = portrait;
    }

    public String getOriginal() {
        return original;
    }

    public String getLarge() {
        return large;
    }

    public String getLarge2x() {
        return large2x;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }

    public String getPortrait() {
        return portrait;
    }
}
