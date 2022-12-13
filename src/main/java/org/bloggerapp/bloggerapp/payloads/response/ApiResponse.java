package org.bloggerapp.bloggerapp.payloads.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean status;
    private String message;
}
