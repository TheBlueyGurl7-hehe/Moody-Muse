package com.moodifyx;

public class Song{
    private final String title;
    private final String filePath;

    public Song(String title, String filePath){
        this.title = title;
        this.filePath = filePath;

    }

    public String getTitle(){
        return title;
    }

    public String getfilePath(){
        return filePath;
    }

}