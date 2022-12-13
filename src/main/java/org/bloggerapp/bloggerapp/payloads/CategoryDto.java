package org.bloggerapp.bloggerapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotEmpty(message = "empty name not allowed")
    @Size(min = 4, max = 50, message = "size must be 4 to 50")
    private String name;
    @NotEmpty(message = "Empty desc not allowed")
    @Size(max = 250, message = "max char is 250")
    private String description;
}
