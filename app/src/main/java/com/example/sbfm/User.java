package com.example.sbfm;

public class User {


    private String Topic;
    private String Note;
    private String BibleRefrences;
    private String WatchWord;
    private String Name;
    private String Post;
    private String ProfileImage;

    public User() {
    }

    public User(String topic, String note, String bibleRefrences, String watchWord, String name, String post, String profileImage) {
        Topic = topic;
        Note = note;
        BibleRefrences = bibleRefrences;
        WatchWord = watchWord;
        Name = name;
        Post = post;
        ProfileImage = profileImage;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getBibleRefrences() {
        return BibleRefrences;
    }

    public void setBibleRefrences(String bibleRefrences) {
        BibleRefrences = bibleRefrences;
    }

    public String getWatchWord() {
        return WatchWord;
    }

    public void setWatchWord(String watchWord) {
        WatchWord = watchWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
