package com.codewithjay.ServicesImpl;

import com.codewithjay.Dto.CategoryDto;
import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Entities.Category;
import com.codewithjay.Entities.Course;
import com.codewithjay.Exceptions.ResourceNotFoundException;
import com.codewithjay.Reposetories.CategoryRepo;
import com.codewithjay.Reposetories.CourseRepo;
import com.codewithjay.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        String string = UUID.randomUUID().toString();
        Date date = new Date();

        categoryDto.setId(string);
        categoryDto.setAddedDate(date);

        Category map = this.modelMapper.map(categoryDto, Category.class);
        Category save = this.categoryRepo.save(map);

        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String id) {

        Category category1 = new Category();
        category1.setId(id);
        category1.setAddedDate(new Date());
        category1.setTitle(categoryDto.getTitle());
        category1.setDesc(categoryDto.getDesc());

        Category save = categoryRepo.save(category1);
        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String id) {

        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found by this Id " + id));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CustomPageResponce<CategoryDto> getAllCategories(int pagenumber , int pagesize, String sortBy, String order) {

        Sort sort = order.equals("ascending") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()  ;
        PageRequest pageRequest = PageRequest.of(pagenumber , pagesize,sort);
        Page<Category> all = categoryRepo.findAll(pageRequest);
        List<Category> content = all.getContent();
        List<CategoryDto> categoriesDtoList = content.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        CustomPageResponce<CategoryDto> customPageRequest = new CustomPageResponce<>();

        customPageRequest.setContent(categoriesDtoList);
        customPageRequest.setTotalPages(all.getTotalPages());
        customPageRequest.setTotalElements(all.getTotalElements());
        customPageRequest.setPagesize(pageRequest.getPageSize());
        customPageRequest.setPagenumber(pageRequest.getPageNumber());


        return customPageRequest;
    }

    @Override
    public void deleteCategory(String id) {

        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not deleted by this Id "+ id));

        categoryRepo.delete(category);

    }

    @Override
    public void addCourseToCategory(String categoryId, String courseId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found by this Id " + categoryId));

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found by this Id " + courseId));

        category.addCourse(course);
        categoryRepo.save(category);

        System.out.println("Added course to Category..........");

    }

    @Override
    public List<CourseDto> getTotalCourses(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found by this Id " + categoryId));

        List<Course> courses = category.getCourses();
        

        List<CourseDto> collect = courses.stream().map(m -> modelMapper.map(m, CourseDto.class)).collect(Collectors.toList());
        System.out.println("Total coures is:- "+courses.size());

        return collect;
    }


}
