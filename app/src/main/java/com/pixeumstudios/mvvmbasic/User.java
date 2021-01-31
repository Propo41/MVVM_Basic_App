package com.pixeumstudios.mvvmbasic;

public class User {
    private String name;
    private String university;
    private int imgRes;

    public User(){}

    public User(String name, String university, int imgRes) {
        this.name = name;
        this.university = university;
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
