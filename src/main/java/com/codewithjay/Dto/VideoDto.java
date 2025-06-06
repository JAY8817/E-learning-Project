package com.codewithjay.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {


    private String videoId;

    private String title;

    private String desc;

    private String video;

    private String filePath;

    private String contentType;

}
