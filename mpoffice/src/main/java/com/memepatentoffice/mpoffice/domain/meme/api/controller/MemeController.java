package com.memepatentoffice.mpoffice.domain.meme.api.controller;

import com.memepatentoffice.mpoffice.common.Exception.NotFoundException;
import com.memepatentoffice.mpoffice.domain.meme.api.request.UserMemeLikeRequest;
import com.memepatentoffice.mpoffice.domain.meme.api.request.MemeCreateRequest;
import com.memepatentoffice.mpoffice.domain.meme.api.response.MemeResponse;
import com.memepatentoffice.mpoffice.domain.meme.api.service.MemeService;
import com.memepatentoffice.mpoffice.domain.meme.api.service.GcpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/meme")
@RestController
public class MemeController {
    private final MemeService memeService;
    private final GcpService gcpService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @GetMapping("/")
    public ResponseEntity<?> getAllMemes(){
        return ResponseEntity.status(HttpStatus.OK).body(
                memeService.findAll()
        );
    }

    @GetMapping("/{title}")
    public ResponseEntity getMeme(@PathVariable String title) throws NotFoundException {
        MemeResponse result = memeService.findByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/check/{title}")
    public ResponseEntity titleDuplicatedcheck(@PathVariable String title){
        String result = memeService.titleCheck(title);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity createMeme(MemeCreateRequest memeCreateRequest) throws Exception{
        if( memeService.titleCheck(memeCreateRequest.getTitle()) == "fail"){
            return ResponseEntity.ok().body("Title is already exist");
        }
        String img = gcpService.uploadFile(memeCreateRequest.getFile());
        memeCreateRequest.setImageUrl(img);
        Long id = memeService.createMeme(memeCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<?> createLike(@RequestBody UserMemeLikeRequest userMemeLikeRequest) throws Exception {
        memeService.addMemeLike(userMemeLikeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


////    image upload test
//    @PostMapping("/upload")
//    @ResponseBody
//    public ResponseEntity upload(@RequestBody MultipartFile file)throws IOException {
//        String url = gcpService.uploadFile(file);
//        return ResponseEntity.ok().body(url);
//    }

    // Get Test
//    @GetMapping("/test/{test}")
//    public ResponseEntity testHi(@PathVariable String test){
//        System.out.println("came");
//        return ResponseEntity.ok().body(test);
//    }


}
