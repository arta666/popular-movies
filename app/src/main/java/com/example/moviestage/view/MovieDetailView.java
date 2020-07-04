package com.example.moviestage.view;

import com.example.moviestage.model.Video;

import java.util.List;

public interface MovieDetailView extends BaseView {

    void onDisplayResult(List<Video> videoList);
    void onDisplayError(String error);
}
