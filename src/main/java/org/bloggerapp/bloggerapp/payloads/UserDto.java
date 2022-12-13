package org.bloggerapp.bloggerapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;

    @NotEmpty
    @Size(min = 4,message = "name length more then 4 required")
    private String name;

    @Email(message = "email is required")
    @NotNull
    private String email;

    @NotEmpty
    @Size(min = 6,max = 15,message = "Password must be 6 to 15 char long")
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles=new HashSet<>();
}
