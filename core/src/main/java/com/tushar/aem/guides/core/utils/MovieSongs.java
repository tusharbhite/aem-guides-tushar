package com.tushar.aem.guides.core.utils;


import java.util.List;
import com.day.cq.wcm.api.Page;


public class MovieSongs {
    public String moviePath;
    public String movieTitle;
    public String moviePoster;
    public List <Page> songsList;
    public MovieSongs(String moviePath,String movieTitle,String moviePoster,List <Page> songsList){
        this.moviePath=moviePath;
        this.movieTitle=movieTitle;
        this.moviePoster=moviePoster;
        this.songsList=songsList;
    }

    public String getMoviePath() {
        return moviePath;
    }
    public String getMoviePoster() {
        return moviePoster;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public List<Page> getSongsList() {
        return songsList;
    }
}
