package org.bloggerapp.bloggerapp.payloads.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bloggerapp.bloggerapp.payloads.PostDto;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class PostsResponse {
    private List<PostDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalRecords;
    private boolean lastPage;
}
