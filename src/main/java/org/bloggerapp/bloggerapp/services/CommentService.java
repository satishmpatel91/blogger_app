package org.bloggerapp.bloggerapp.services;

import org.bloggerapp.bloggerapp.payloads.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto,Integer postId);

    public void deleteComment(Integer commentId);
}
