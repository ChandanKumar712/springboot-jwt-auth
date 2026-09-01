//package com.example.demo.dto;
//import lombok.Data;
//
//@Data
//public class UserDTO {
//    private String username;
//    private String password;
//}





// For Validation Implementation
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
public class UserDTO {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}
