package com.gec.system.controller;




import com.gec.model.system.SysMovie;
import com.gec.system.service.SysMovieService;
import com.gec.system.util.ThreadPoolFactory;
import com.gec.util.OssTemplate;
import com.gec.util.VodTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RequestMapping(value = "/admin/system/upload")
@RestController
@CrossOrigin //  处理 跨域问题
public class UploadController{

    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private VodTemplate vodTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysMovieService sysMovieService;



    //  http://localhost:8085/admin/system/upload/uploadImage
    // 上传文件 (图片)
    @RequestMapping(value = "/uploadImage")
    public String uploadImage(MultipartFile uploadImage) throws IOException, ExecutionException, InterruptedException {
        Callable<String> task = () -> {
            Long id = null;
            id = (Long) redisTemplate.opsForValue().get("id");
            System.out.println(id);
            Integer size = 0;
            if (id==null){
                size = ThreadPoolFactory.getUpSize();
            }
            redisTemplate.delete("id");
            String imageUrl = this.ossTemplate.upload(uploadImage.getOriginalFilename(), uploadImage.getInputStream());
            System.out.println("imageUrl=====" + imageUrl);

            if (id==null){
                id = (Long) redisTemplate.opsForValue().get("movieId");
                if (id==null){
                    return imageUrl;
                }
            }
            SysMovie byId = sysMovieService.getById(id);
            byId.setImage(imageUrl);
            sysMovieService.updateById(byId);
            return  imageUrl;
        };
        Future<String> future = ThreadPoolFactory.threadPoolFactory.submit(task);
        String videoId = future.get();
        return videoId;
    }

    //  "http://localhost:8085/admin/system/upload/uploadVideo
    // 上传视频操作
    @RequestMapping(value = "/uploadVideo")
    public String uploadVideo(MultipartFile uploadVideo) throws IOException, ExecutionException, InterruptedException {
        Callable<String> task = () -> {
            Long id = null;
            Integer size = 0;
            System.out.println("你好");
            id = (Long) redisTemplate.opsForValue().get("id");
            System.out.println(id);
            if (id==null){
                size = ThreadPoolFactory.getUpSize();
            }
            redisTemplate.delete("id");
            String videoId = this.vodTemplate.uploadVideo(uploadVideo.getOriginalFilename(), uploadVideo.getInputStream());
            System.out.println("videoId====="+videoId);

            if (id==null){
                id = (Long) redisTemplate.opsForValue().get("movieId"+size);
                if (id==null){
                    return videoId;
                }else {
                    redisTemplate.delete("movieId"+size);
                }
            }
            SysMovie byId = sysMovieService.getById(id);
            byId.setPlayId(videoId);
            sysMovieService.updateById(byId);
            return videoId;
        };
        Future<String> future = ThreadPoolFactory.threadPoolFactory.submit(task);
        String MovieName = future.get();
        return MovieName;
    }



}
