/////    For Post?Signup

//package com.example.demo.controller;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/signup")
//    public String signup(@RequestBody User user) {
//        userRepository.save(user);
//        return "User saved successfully!";
//    }
//}


/////     Post/Login


/////    using service layer

//package com.example.demo.controller;
//import com.example.demo.model.User;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/signup")
//    public String signup(@RequestBody User user) {
//        return userService.signup(user);
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        return userService.login(user);
//    }
//}
//


//////   Another method by using


//package com.example.demo.controller;
//import com.example.demo.model.User;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.example.demo.dto.UserDTO;
//import org.springframework.http.ResponseEntity;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.signup(userDTO));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.login(userDTO));
//    }
//}







////    using CRUD

//package com.example.demo.controller;
//import com.example.demo.model.User;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.example.demo.dto.UserDTO;
//import org.springframework.http.ResponseEntity;
//import java.util.List;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // SIGNUP
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.signup(userDTO));
//    }
//
//    // LOGIN
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.login(userDTO));
//    }
//
//    // GET all users
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    // GET by id
//    @GetMapping("/users/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//
//    // DELETE
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.deleteUser(id));
//    }
//
//    // UPDATE
//    @PutMapping("/users/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//        return ResponseEntity.ok(userService.updateUser(id, user));
//    }
//}
//


/////    Another method By using JWT (Industry Level) ---> IMP

package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
//     For PROFILE API
import org.springframework.security.core.Authentication;
// UPDATE OWN PROFILE API
import jakarta.servlet.http.HttpServletRequest;

// For Validation Implementation
import jakarta.validation.Valid;

import com.example.demo.dto.RefreshRequestDTO;
import com.example.demo.dto.LoginResponseDTO;

////  For Pagination + Sorting
import org.springframework.data.domain.Page;

// For USER Logout
import com.example.demo.dto.LogoutRequestDTO;

import org.springframework.web.bind.annotation.RequestParam;

///////    For Forget Password
import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.ResetPasswordDTO;

/// For otpDTO verification
import com.example.demo.dto.VerifyOtpDTO;

////   For email, password to OTP verification rather than
import com.example.demo.dto.LoginRequestDTO;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

         // Signup
//    @PostMapping("/signup")
//     public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.signup(userDTO));
//    }


    ////       For Validation
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(userService.signup(userDTO));
    }

    // Login (JWT token)
//    @PostMapping("/login")
//    // public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        public ResponseEntity<String> login(@Valid @RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.login(userDTO));
//    }

    // For Validation
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(
//            @Valid @RequestBody UserDTO userDTO) {
//
//        return ResponseEntity.ok(userService.login(userDTO));
//    }

    ////    For Email Login Verification

//    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @Valid @RequestBody UserDTO userDTO) {
//
//        try {
//
//            return ResponseEntity.ok(
//                    userService.login(userDTO)
//            );
//
//        } catch (RuntimeException e) {
//
//            return ResponseEntity
//                    .status(403)
//                    .body(e.getMessage());
//        }
//    }


/////   For email, password  to OTP verification =>  rather than username, password
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        try {

            return ResponseEntity.ok(
                    userService.login(loginRequestDTO)
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(403)
                    .body(e.getMessage());
        }
    }

        ////// For verify-login-otp

        @PostMapping("/verify-login-otp")
        public ResponseEntity<?> verifyLoginOtp(
                @Valid @RequestBody VerifyOtpDTO verifyOtpDTO){

            try {
                return ResponseEntity.ok(
                        userService.verifyLoginOtp(
                                verifyOtpDTO.getEmail(),
                                verifyOtpDTO.getOtp()
                        )
                );

            } catch (RuntimeException e) {
                return ResponseEntity
                        .status(403)
                        .body(e.getMessage());
            }
        }

    // GET all users
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET by id
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")    // Role-based Authorization
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    // UPDATE
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // PROFILE API
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(userService.getProfile(username));
    }

    //    Update PROFILE API
    @PutMapping("/profile")
    public User updateProfile(
            @RequestBody User updatedUser,
            HttpServletRequest request
    ) {
        return userService.updateProfile(updatedUser, request);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @RequestBody RefreshRequestDTO requestDTO
    ) {

        return ResponseEntity.ok(
                userService.refreshToken(requestDTO)
        );
    }

    @PostMapping("/api/user-logout")
    public ResponseEntity<String> logout(
            @RequestBody LogoutRequestDTO requestDTO) {

        return ResponseEntity.ok(
                userService.logout(requestDTO)
        );
    }

    @GetMapping("/users/paginated")
    public ResponseEntity<Page<User>> getUsersPaginated(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy
    ) {

        return ResponseEntity.ok(
                userService.getUsersWithPagination(
                        page,
                        size,
                        sortBy
                )
        );
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam String username) {

        return ResponseEntity.ok(
                userService.searchUsers(username)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequestDTO requestDTO) {

        return ResponseEntity.ok(
                userService.forgotPassword(requestDTO)
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordDTO requestDTO) {

        return ResponseEntity.ok(
                userService.resetPassword(requestDTO)
        );
    }
}