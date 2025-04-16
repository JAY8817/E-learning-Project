package com.codewithjay.Reposetories;

import com.codewithjay.Entities.Category;
import com.codewithjay.Entities.Course;
import com.codewithjay.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, String> {

    List<Course> findByTitleContaining(String title);


    List<Course> findByLive(boolean live);

}
