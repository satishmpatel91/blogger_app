package org.bloggerapp.bloggerapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class JWTRequest {
    private String email;
    private String password;
}
