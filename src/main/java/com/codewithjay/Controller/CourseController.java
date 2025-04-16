package com.codewithjay.Controller;


import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomMessage;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Dto.ResourceContantType;
import com.codewithjay.Entities.Course;
import com.codewithjay.Exceptions.ResourceNotFoundException;
import com.codewithjay.Reposetories.CourseRepo;
import com.codewithjay.Services.CoursService;
import com.codewithjay.Services.FileServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private FileServices fileServices;
    @Autowired
    private CourseRepo courseRepo;

    @PostMapping("/")
    public ResponseEntity<CourseDto> addCourse(@Valid @RequestBody CourseDto courseDto) {
        CourseDto courseDto1 = coursService.create(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto1);

    }

    @PutMapping("{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, @PathVariable String id) {
        CourseDto courseDto1 = coursService.updateCourse(courseDto,id);
        return ResponseEntity.status(HttpStatus.OK).body(courseDto1);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomMessage> deleteCourse(@PathVariable String id) {
        coursService.deleteCourseById(id);

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Course successfully deleted by this id:- "+ id);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }


    @GetMapping("/")
    public ResponseEntity<CustomPageResponce<CourseDto>> getAllCourses(
            @RequestParam(value = "pagenumber" , defaultValue = AppConstant.pagenumber , required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = AppConstant.pagesize, required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.sortvalue) String sortBy,
            @RequestParam(value = "order", defaultValue = AppConstant.sortorder) String order
    ) {
        CustomPageResponce<CourseDto> courseDtos = coursService.getAllCourses(pagenumber,pagesize,sortBy,order);
        return ResponseEntity.status(HttpStatus.OK).body(courseDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable  String id) {
        CourseDto courseById = coursService.getCourseById(id);

        return ResponseEntity.status(HttpStatus.OK).body(courseById);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Course>> getByTitle(@PathVariable String title) {

        List<Course> course = coursService.serchByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(course);

    }

    @PostMapping("{courseId}/video/{videoId}")
    public ResponseEntity<CustomMessage> addCoures(@PathVariable String courseId, @PathVariable String videoId) {

        coursService.addVideoToCourse(courseId,videoId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Coures added successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);

    }

    @DeleteMapping("{courseId}/delete/video/{videoId}")
    public ResponseEntity<CustomMessage> deleteCoures(@PathVariable String courseId, @PathVariable String videoId) {

        coursService.removeVideoFromCourse(courseId,videoId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Coures deleted successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }

    @PostMapping("{coursId}/banner")
    public ResponseEntity<CourseDto> addBanner(@PathVariable String coursId, @RequestParam("banner") MultipartFile banner) throws IOException {

        System.out.println(banner.getName());
        System.out.println(banner.getOriginalFilename());
        System.out.println(banner.getContentType());
        System.out.println(banner.getSize());

        System.out.println("Folder path is:- "+ Paths.get(AppConstant.COURSE_UPLODE_BANNER, banner.getOriginalFilename()));

        CourseDto courseDto = coursService.saveBanner(banner, coursId);

        return ResponseEntity.status(HttpStatus.OK).body(courseDto);

    }



    @GetMapping("{coursId}/banner")
    public ResponseEntity<Resource> getBanner(@PathVariable String coursId) {
        ResourceContantType resourceContantType = coursService.getBannerById(coursId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(resourceContantType.getContantType())).body(resourceContantType.getResource());
    }

}
