package com.example.italiandreambackend.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class EmailRequest {
    private String to ;
    private String subject ;
    private String body ;
}
