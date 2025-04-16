package com.codewithjay.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private String id;

    @NotEmpty(message = "Title must be required")
    @Size(min = 3 , max = 20 , message = "Title required between 3 to 20 characters")
    private String title;

    @NotEmpty(message = "Description must be required")
    @Size(min = 3 , max = 10 , message = "ShortDesc required between 3 to 10 characters")
    private String shortDesc;

    @NotEmpty(message = "Description must be required")
    @Size(min = 5 , max = 50 , message = "LongDesc required between 5 to 10 characters")
    private String longDesc;

    @DecimalMin(value = "5", inclusive = false)
    @DecimalMax(value = "1000.0", inclusive = true)
    private double price;

    private boolean live = false;

    private double discount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    private Date createdDate;

    // add your fields

    private String banner;

    public String bannerContactType;


    private List<CategoryDto> categoryList = new ArrayList<>();

    //    videos
    private List<VideoDto> videos = new ArrayList<>();


}
