package org.bloggerapp.bloggerapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private String imageName = "default.png";
    private Date addedDate;
    private CategoryDto category;
    private UserDto users;

    private List<CommentDto> comments=new ArrayList<>();
}
