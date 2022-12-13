package org.bloggerapp.bloggerapp.services;

import org.bloggerapp.bloggerapp.payloads.PostDto;
import org.bloggerapp.bloggerapp.payloads.response.PostsResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    public PostDto updatePost(PostDto postDto, Integer postId);

    public void deletePost(Integer postId);

    public PostsResponse getAllPost(int pageNumber, int pageSize,String sortBy,String sortDir);

    public PostDto getPostById(Integer postId);

    public List<PostDto> getPostByCategory(Integer categoryId);

    public List<PostDto> getPostByUser(Integer userId);

    public List<PostDto> searchPost(String keyword);
}
