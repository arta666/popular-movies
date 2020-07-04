package com.example.moviestage.app;

import com.example.moviestage.BuildConfig;

public class Constants {


    public static final String API_KEY = BuildConfig.API_KEY;

    // API PARAMS
    public static final String ID = "id";
    public static final String RESULTS = "results";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_PATH = "poster_path";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String OVERVIEW  = "overview";
    public static final String VOTE_AVERAGE  = "vote_average";
    public static final String RELEASE_DATE  = "release_date";
    public static final String SORT_BY_POPULAR = "popular";
    public static final String SORT_BY_TOP_RATE = "top_rated";
    public static final String FETCH_REVIEWS = "reviews";
    public static final String FETCH_VIDEOS = "videos";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String SITE = "site";
    public static final String SIZE = "size";
    public static final String TYPE = "type";




    public enum videoType {
        Trailer,Clip,Featurette
    }

}
