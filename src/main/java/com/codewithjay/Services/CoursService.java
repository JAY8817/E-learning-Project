package com.codewithjay.Services;

import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Dto.ResourceContantType;
import com.codewithjay.Entities.Course;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CoursService {


    CourseDto create(CourseDto coursedto);
    CustomPageResponce<CourseDto> getAllCourses(int pagenumber, int pagesize, String sortBy, String order);
    CourseDto getCourseById(String id);
    void deleteCourseById(String id);
    CourseDto updateCourse(CourseDto course,String id);
    List<Course> serchByTitle(String title);
    void addVideoToCourse( String courseId, String videoId);
    void removeVideoFromCourse(String courseId, String videoId);
    CourseDto saveBanner(MultipartFile file , String courseId) throws IOException;
    ResourceContantType getBannerById(String coursId);
}
