package org.bloggerapp.bloggerapp.services.impl;

import org.bloggerapp.bloggerapp.entities.Category;
import org.bloggerapp.bloggerapp.entities.Post;
import org.bloggerapp.bloggerapp.entities.Users;
import org.bloggerapp.bloggerapp.exceptions.InvalidArgumentException;
import org.bloggerapp.bloggerapp.exceptions.ResourceNotFoundException;
import org.bloggerapp.bloggerapp.payloads.PostDto;
import org.bloggerapp.bloggerapp.payloads.response.PostsResponse;
import org.bloggerapp.bloggerapp.repositories.CategoryRepo;
import org.bloggerapp.bloggerapp.repositories.PostRepo;
import org.bloggerapp.bloggerapp.repositories.UserRepository;
import org.bloggerapp.bloggerapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepo postRepo;
    private ModelMapper modelMapper;

    private UserRepository userRepository;

    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id :", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id : ", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUsers(users);
        post.setAddedDate(new Date());
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id:", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id :", postId));
        postRepo.delete(post);
    }

    @Override
    public PostsResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        if (pageSize <= 0) throw new InvalidArgumentException("PageSize", pageSize);
       // PageRequest pageRequest;
        Sort sort = "ASC".equals(sortDir)?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageRequest);
        List<Post> posts = postPage.getContent();

        List<PostDto> postDtoList = posts.stream().map(post -> {
            return modelMapper.map(post, PostDto.class);
        }).collect(Collectors.toList());

        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setContent(postDtoList);
        postsResponse.setPageNumber(postPage.getNumber());
        postsResponse.setPageSize(postPage.getSize());
        postsResponse.setTotalPage(postPage.getTotalPages());
        postsResponse.setTotalRecords(postPage.getTotalElements());
        postsResponse.setLastPage(postPage.isLast());

        return postsResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id :", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id :", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        return posts.stream().map(post -> {
            return modelMapper.map(post, PostDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id :", userId));
        List<Post> posts = postRepo.findByUsers(users);
        return posts.stream().map(post -> {
            return modelMapper.map(post, PostDto.class);
        }).collect(Collectors.toList());

    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepo.search("%"+keyword+"%");
        return posts.stream().map(post -> {
            return modelMapper.map(post,PostDto.class);
        }).collect(Collectors.toList());
    }

    @Autowired
    public void setPostRepo(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCategoryRepo(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
