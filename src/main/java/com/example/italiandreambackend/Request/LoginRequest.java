package com.example.italiandreambackend.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class LoginRequest {
    String clientId ;
    String password ;

}