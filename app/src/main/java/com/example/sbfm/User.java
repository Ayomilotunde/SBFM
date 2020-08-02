package com.example.sbfm;

public class User {


    private String topic;
    private String note;
    private String bibleRef;
    private String watchWord;
    private String Name;
    private String Post;
    private String ProfileImage;

    public User() {
    }

    public User(String topic, String note, String bibleRef, String watchWord, String name, String post, String profileImage) {
        this.topic = topic;
        this.note = note;
        this.bibleRef = bibleRef;
        this.watchWord = watchWord;
        Name = name;
        Post = post;
        ProfileImage = profileImage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBibleRef() {
        return bibleRef;
    }

    public void setBibleRef(String bibleRef) {
        this.bibleRef = bibleRef;
    }

    public String getWatchWord() {
        return watchWord;
    }

    public void setWatchWord(String watchWord) {
        this.watchWord = watchWord;
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
