package com.codewithjay.Controller;


import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.CategoryDto;
import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomMessage;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Entities.Category;
import com.codewithjay.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categorydto) {

        CategoryDto category = categoryService.createCategory(categorydto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto categorydto,@PathVariable String id) {

        CategoryDto categoryDto = categoryService.updateCategory(categorydto, id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);

    }

    @GetMapping("/")
    public ResponseEntity<CustomPageResponce<CategoryDto>> getAllCategories(
            @RequestParam(value = "pagenumber" , defaultValue = AppConstant.pagenumber , required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = AppConstant.pagesize, required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.sortvalue) String sortBy,
            @RequestParam(value = "order", defaultValue = AppConstant.sortorder) String order
    )
    {
        CustomPageResponce<CategoryDto> allCategories = categoryService.getAllCategories(pagenumber,pagesize,sortBy,order);
        return ResponseEntity.status(HttpStatus.OK).body(allCategories);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String id) {
        CategoryDto categoryById = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryById);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomMessage> deleteCategory(@PathVariable String id) {

        categoryService.deleteCategory(id);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category deleted successfully by this id:- " + id);
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);

    }

    @PostMapping("{categoriesId}/coures/{couresId}")
    public ResponseEntity<CustomMessage> addCoures(@PathVariable String categoriesId, @PathVariable String couresId) {

        categoryService.addCourseToCategory(categoriesId,couresId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Coures added successfully");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);

    }


    @GetMapping("totalCourse/{catId}")
    public ResponseEntity<List<CourseDto>> getAllCoures(@PathVariable String catId) {

        List<CourseDto> totalCourses = categoryService.getTotalCourses(catId);
        return ResponseEntity.status(HttpStatus.OK).body(totalCourses);


    }



}
