package com.codewithjay.Services;

import com.codewithjay.Dto.CourseDto;
import com.codewithjay.Dto.CustomPageResponce;
import com.codewithjay.Dto.ResourceContantType;
import com.codewithjay.Dto.VideoDto;
import com.codewithjay.Entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoServices {

    VideoDto addVideo(VideoDto videoDto);
    VideoDto updateVideo(String id , VideoDto videoDto);
    VideoDto getVideoById(String  id);
    void deleteVideo(String id);
    CustomPageResponce<VideoDto> getAllVideos(int pagenumber , int pagesize , String sortby, String order);
    VideoDto saveVideo(MultipartFile file , String videoId) throws IOException;
    ResourceContantType getVideosByVideoId(String videoId);

}
