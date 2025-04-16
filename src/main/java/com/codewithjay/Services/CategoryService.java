package com.codewithjay.Services;

import com.codewithjay.Dto.CategoryDto;
import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomPageResponce;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto , String id);
    CategoryDto getCategoryById(String id);
    CustomPageResponce<CategoryDto> getAllCategories(int pagenumber, int pagesize, String sortBy, String order);
    void deleteCategory(String id);
    void addCourseToCategory(String categoryId, String courseId);
    List<CourseDto> getTotalCourses(String categoryId);


}
