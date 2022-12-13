package org.bloggerapp.bloggerapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(name = "content", nullable = true, length = 225)
    private String content;
    @Column(nullable = true)
    private String imageName;
    @Column(nullable = true)
    private Date addedDate;

    @ManyToOne
    private Users users;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
