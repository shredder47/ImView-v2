package com.example.shredder.imview.Interfaces;

import com.example.shredder.imview.Model.PexelsStruct;

import java.util.ArrayList;

public interface OnImageFetchCompleteListener {

     void onFetchCompleted(ArrayList<PexelsStruct> allPhotoList);
}
