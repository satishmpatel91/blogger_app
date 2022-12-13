package org.bloggerapp.bloggerapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JWTResponse {
    private String token;
}
