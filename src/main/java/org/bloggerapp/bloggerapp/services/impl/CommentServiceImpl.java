package org.bloggerapp.bloggerapp.services.impl;

import lombok.Setter;
import org.bloggerapp.bloggerapp.entities.Comment;
import org.bloggerapp.bloggerapp.entities.Post;
import org.bloggerapp.bloggerapp.exceptions.ResourceNotFoundException;
import org.bloggerapp.bloggerapp.payloads.CommentDto;
import org.bloggerapp.bloggerapp.repositories.CommentRepo;
import org.bloggerapp.bloggerapp.repositories.PostRepo;
import org.bloggerapp.bloggerapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepo commentRepo;
    private PostRepo postRepo;
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id :", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id :", commentId));
        commentRepo.delete(comment);
    }

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Autowired
    public void setPostRepo(PostRepo postRepo) {
        this.postRepo = postRepo;
    }
@Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}

