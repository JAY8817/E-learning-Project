package com.codewithjay.ServicesImpl;

import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.*;
import com.codewithjay.Entities.Course;
import com.codewithjay.Entities.Video;
import com.codewithjay.Exceptions.InvalidFileFormatException;
import com.codewithjay.Exceptions.ResourceNotFoundException;
import com.codewithjay.Reposetories.CourseRepo;
import com.codewithjay.Reposetories.VideoRepo;
import com.codewithjay.Services.FileServices;
import com.codewithjay.Services.VideoServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VideoImpl implements VideoServices {

    @Autowired
    public VideoRepo videoRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public CourseRepo courseRepo;

    @Autowired
    public FileServices  fileServices;

    @Override
    public VideoDto addVideo(VideoDto videoDto) {

        String id = UUID.randomUUID().toString();

       videoDto.setVideoId(id);
        Video map = modelMapper.map(videoDto, Video.class);

        Video save = videoRepo.save(map);

       return modelMapper.map(save, VideoDto.class);

    }

    @Override
    public VideoDto updateVideo(String id, VideoDto videoDto) {

        Video video1 = videoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video not Updeted successfully!!!"));


        Video video = new Video();

        video.setVideoId(id);
        video.setTitle(videoDto.getTitle());
        video.setDesc(videoDto.getDesc());
        video.setFilePath(videoDto.getFilePath());
        video.setContentType(videoDto.getContentType());

        Video save = videoRepo.save(video);
        Video map = this.modelMapper.map(save, Video.class);

        return modelMapper.map(map, VideoDto.class);
    }

    @Override
    public VideoDto getVideoById(String id) {
        Video video = videoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + id));

        return modelMapper.map(video, VideoDto.class);
    }


    @Override
    public void deleteVideo(String  id) {

        Video video = videoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + id));
        videoRepo.delete(video);
    }

    @Override
    public CustomPageResponce<VideoDto> getAllVideos(int pagenumber , int pagesize , String sortBy, String order) {

        Sort sort = order.equals("ascending") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<Video> all = videoRepo.findAll(pageable);
        List<Video> content = all.getContent();
        List<VideoDto> collect = content.stream().map(videos -> modelMapper.map(videos, VideoDto.class)).collect(Collectors.toList());


        CustomPageResponce<VideoDto> customPageRequest = new CustomPageResponce<>();

        customPageRequest.setContent(collect);
        customPageRequest.setTotalPages(all.getTotalPages());
        customPageRequest.setTotalElements(all.getTotalElements());
        customPageRequest.setPagesize(pageable.getPageSize());
        customPageRequest.setPagenumber(pageable.getPageNumber());


        return customPageRequest;
    }

    @Override
    public VideoDto saveVideo(MultipartFile file, String videoId) throws IOException {
        Video video= videoRepo.findById(videoId).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + videoId));

        String contentType = file.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png","video/mp4","video/mp3").contains(contentType)) {
            throw new InvalidFileFormatException("Only JPEG,PNG and Video are allowed");
        }


        String filePath = fileServices.saveFile(file, AppConstant.VIDEO_UPLODE_BANNER, file.getOriginalFilename());
        video.setVideo(filePath);
        video.setContentType(file.getContentType());

        Video save = videoRepo.save(video);
        return modelMapper.map(save, VideoDto.class);
    }

    @Override
    public ResourceContantType getVideosByVideoId(String videoId) {
        Video video= videoRepo.findById(videoId).orElseThrow(() -> new ResourceNotFoundException("Video not find by this id " + videoId));
        String videoBanner = video.getVideo();
        Path path = Paths.get(videoBanner);
        Resource resource = new FileSystemResource(path);
        ResourceContantType resourceContantType = new ResourceContantType();
        resourceContantType.setResource(resource);
        resourceContantType.setContantType(video.getContentType());

        return resourceContantType;
    }
}




