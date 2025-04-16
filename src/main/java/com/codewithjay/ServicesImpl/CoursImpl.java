package com.codewithjay.ServicesImpl;

import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.CategoryDto;
import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Dto.ResourceContantType;
import com.codewithjay.Entities.Category;
import com.codewithjay.Entities.Course;
import com.codewithjay.Entities.Video;
import com.codewithjay.Exceptions.InvalidFileFormatException;
import com.codewithjay.Exceptions.ResourceNotFoundException;
import com.codewithjay.Reposetories.CourseRepo;
import com.codewithjay.Reposetories.VideoRepo;
import com.codewithjay.Services.CoursService;
import com.codewithjay.Services.FileServices;
import com.codewithjay.Services.VideoServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoursImpl implements CoursService {



    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private VideoRepo videoRepo;

    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private FileServices fileServices;



    @Override
    public CourseDto create(CourseDto coursedto) {

        String id = UUID.randomUUID().toString();

        coursedto.setId(id);
        Course map = this.modelmapper.map(coursedto, Course.class);

        Course save = courseRepo.save(map);

        return this.modelmapper.map(save, CourseDto.class);

    }

    @Override
    public CustomPageResponce<CourseDto> getAllCourses(int pagenumber, int pagesize, String sortBy, String order) {

        Sort sort = order.equals("ascending") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()  ;
        PageRequest pageRequest = PageRequest.of(pagenumber , pagesize,sort);
        Page<Course> all = this.courseRepo.findAll(pageRequest);
        List<Course> content = all.getContent();
        List<CourseDto> courses = content.stream().map(course -> this.modelmapper.map(course, CourseDto.class)).collect(Collectors.toList());

        CustomPageResponce<CourseDto> customPageRequest = new CustomPageResponce<>();

        customPageRequest.setContent(courses);
        customPageRequest.setTotalPages(all.getTotalPages());
        customPageRequest.setTotalElements(all.getTotalElements());
        customPageRequest.setPagesize(pageRequest.getPageSize());
        customPageRequest.setPagenumber(pageRequest.getPageNumber());


        return customPageRequest;
    }

    @Override
    public CourseDto getCourseById(String id) {
        Course course = this.courseRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return  modelmapper.map(course, CourseDto.class);
    }

    @Override
    public void deleteCourseById(String id) {

        Course course = this.courseRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course can't be deleted"));
                        this.courseRepo.delete(course);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto ,String id) {


        Course course1 = new Course();
        course1.setId(id);
        course1.setShortDesc(courseDto.getShortDesc());
        course1.setBanner(courseDto.getBanner());
        course1.setDiscount(courseDto.getDiscount());
        course1.setLive(courseDto.isLive());
        course1.setTitle(courseDto.getTitle());
        course1.setPrice(courseDto.getPrice());
        course1.setBannerContantType(courseDto.getBannerContactType());
        course1.setCreatedDate(courseDto.getCreatedDate());
        course1.setLongDesc(courseDto.getLongDesc());

        Course save = this.courseRepo.save(course1);
        return modelmapper.map(save, CourseDto.class);
    }

    @Override
    public List<Course> serchByTitle(String title) {

        List<Course> byTitleContaining = this.courseRepo.findByTitleContaining(title);
        return byTitleContaining;
    }

    @Override
    public void addVideoToCourse(String courseId, String videoId) {

        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + videoId));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not find by this id " + courseId));

        course.addVideo(video);
        courseRepo.save(course);

        System.out.println("Added Video to Course..........");
    }

    @Override
    public void removeVideoFromCourse(String courseId, String videoId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not find by this id " + courseId));
        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + videoId));

       course.removeVideo(video);
       courseRepo.save(course);

       System.out.println("Removed Video from Course..........");
    }

    @Override
    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException {

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not find by this id " + courseId));
        String contentType = file.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png","video/mp4","video/mp3").contains(contentType)) {
            throw new InvalidFileFormatException("Only JPEG,PNG and Video are allowed");
        }


        String filePath = fileServices.saveFile(file, AppConstant.COURSE_UPLODE_BANNER, file.getOriginalFilename());
        course.setBanner(filePath);
        course.setBannerContantType(file.getContentType());
//        System.out.println("contant type " + file.getContentType());

        Course save = courseRepo.save(course);
        return modelmapper.map(save, CourseDto.class);
    }

    @Override
    public ResourceContantType getBannerById(String coursId) {
        Course course = courseRepo.findById(coursId).orElseThrow(() -> new ResourceNotFoundException("Course not find by this id " + coursId));
        String banner = course.getBanner();
        Path path = Paths.get(banner);
        Resource resource = new FileSystemResource(path);
        ResourceContantType resourceContantType = new ResourceContantType();
        resourceContantType.setResource(resource);
        resourceContantType.setContantType(course.getBannerContantType());

        return resourceContantType;
    }
}
