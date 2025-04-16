package com.codewithjay.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Video {

    @Id
    private String videoId;

    private String title;

    @Column(name = "description", length = 1000)
    private String desc;

    private String video;

    private String filePath;

    private String contentType;

    // add your choice of field

    @ManyToOne(cascade = CascadeType.ALL)
    private  Course course;



}
