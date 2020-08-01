package com.example.sbfm;

public class User {

    private String Name;
    private String Topic;
    private String Note;
    private String BibleRefrences;
    private String WatchWord;

    public User() {
    }

    public User(String name, String topic, String note, String bibleRefrences, String watchWord) {
        Name = name;
        Topic = topic;
        Note = note;
        BibleRefrences = bibleRefrences;
        WatchWord = watchWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
