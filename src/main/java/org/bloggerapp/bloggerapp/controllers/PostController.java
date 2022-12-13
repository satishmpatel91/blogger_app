package org.bloggerapp.bloggerapp.controllers;

import org.bloggerapp.bloggerapp.configs.AppConstant;
import org.bloggerapp.bloggerapp.payloads.PostDto;
import org.bloggerapp.bloggerapp.payloads.response.ApiResponse;
import org.bloggerapp.bloggerapp.payloads.response.PostsResponse;
import org.bloggerapp.bloggerapp.services.FileService;
import org.bloggerapp.bloggerapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;

    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/users/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto savedPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId) {
        List<PostDto> postDtos = postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId) {
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostsResponse> getAllPost(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortDir
    ) {
        PostsResponse postsResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId) {
        PostDto postDtos = postService.getPostById(postId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(true, String.format("Post Deleted with ID :%d", postId)), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "postId") Integer postId) {
        PostDto updatePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable String keyword) {
        List<PostDto> postDtos = postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable Integer postId) throws IOException {
        String fileName = fileService.uploadFile(path, multipartFile);
        PostDto postDto = postService.getPostById(postId);
        postDto.setImageName(fileName);
        postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream inputStream = fileService.getResource(path, imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }
}
