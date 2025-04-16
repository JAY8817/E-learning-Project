package com.codewithjay.Controller;

import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.*;
import com.codewithjay.Services.VideoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/v1/vodeoes")
public class VideoController {

    @Autowired
    public VideoServices videoServices;

    @PostMapping("/")
    public ResponseEntity<VideoDto> addVideo(@RequestBody VideoDto videoDto) {

        VideoDto videoDto1 = videoServices.addVideo(videoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(videoDto1);


    }

    @PutMapping("{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable() String id, @RequestBody VideoDto videoDto) {
        VideoDto videoDto1 = videoServices.updateVideo(id, videoDto);
        return ResponseEntity.status(HttpStatus.OK).body(videoDto1);
    }

    @GetMapping("/")
    public ResponseEntity<CustomPageResponce<VideoDto>> getAllVideos(
            @RequestParam(value = "pagenumber" , defaultValue = AppConstant.pagenumber , required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = AppConstant.pagesize, required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.sortvalue) String sortBy,
            @RequestParam(value = "order", defaultValue = AppConstant.sortorder) String order
    ) {
        CustomPageResponce<VideoDto> allVideos = videoServices.getAllVideos(pagenumber, pagesize, sortBy, order);
        return ResponseEntity.status(HttpStatus.OK).body(allVideos);
    }

    @GetMapping("{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable() String id) {
        VideoDto videoById = videoServices.getVideoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(videoById);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomMessage> deleteVideoById(@PathVariable() String id) {
        videoServices.deleteVideo(id);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Video deleted successfully by this Id:-" + id );
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }

    @PostMapping("{videoId}/video")
    public ResponseEntity<VideoDto> addVideoBanner(@PathVariable String videoId, @RequestParam("video") MultipartFile video) throws IOException {

        System.out.println(video.getName());
        System.out.println(video.getOriginalFilename());
        System.out.println(video.getContentType());
        System.out.println(video.getSize());

        System.out.println("Folder path is:- "+ Paths.get(AppConstant.VIDEO_UPLODE_BANNER, video.getOriginalFilename()));

        VideoDto videoDto = videoServices.saveVideo(video, videoId);

        return ResponseEntity.status(HttpStatus.OK).body(videoDto);

    }



    @GetMapping("{videoId}/video")
    public ResponseEntity<Resource> getVideoBanner(@PathVariable String videoId) {
        ResourceContantType resourceContantType = videoServices.getVideosByVideoId(videoId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(resourceContantType.getContantType())).body(resourceContantType.getResource());
    }
}
