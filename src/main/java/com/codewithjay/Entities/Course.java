package com.codewithjay.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

     @Id
    private String id;

    private String title;

    private String shortDesc;

    @Column(length = 2000)
    private String longDesc;

    private double price;

    private boolean live = false;

    private double discount;


    private Date createdDate;

    // add your fields

    private String banner;

    private String bannerContantType;




    //
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> categoryList = new ArrayList<>();

    //    videos
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Video> videos = new ArrayList<>();



    public void addVideo(Video video) {
        videos.add(video);
        video.setCourse(this);
    }
    public void removeVideo(Video video) {
        videos.remove(video); // Remove the video from the list
        video.setCourse(null); // Break the bidirectional link
    }



}
