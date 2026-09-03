////    Only service layer


//package com.example.demo.service;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    // Signup logic
//    public String signup(User user) {
//
//        User existing = userRepository.findByUsername(user.getUsername());
//        if (existing != null) {
//            return "Username already exists";
//        }
//
//        user.setPassword(encoder.encode(user.getPassword()));
//        userRepository.save(user);
//
//        return "User saved successfully";
//    }
//
//    // Login logic
//    public String login(User user) {
//
//        User existingUser = userRepository.findByUsername(user.getUsername());
//
//        if (existingUser == null) {
//            return "User not found";
//        }
//
//        if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
//            return "Login successful";
//        }
//
//        return "Wrong password";
//    }
//}



/////   Another method by using DTO

//package com.example.demo.service;
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.example.demo.dto.UserDTO;
//
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//
//    public String signup(UserDTO userDTO) {
//
//        User existing = userRepository.findByUsername(userDTO.getUsername());
//        if (existing != null) {
//            return "Username already exists";
//        }
//
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(encoder.encode(userDTO.getPassword()));
//
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }
//
//    public String login(UserDTO userDTO) {
//
//        User existingUser = userRepository.findByUsername(userDTO.getUsername());
//
//        if (existingUser == null) {
//            return "User not found";
//        }
//
//        if (encoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
//            return "Login successful";
//        }
//
//        return "Wrong password";
//    }
//}





/////    Another method by using CRUD

//package com.example.demo.service;
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.example.demo.dto.UserDTO;
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    // Signup
//    public String signup(UserDTO userDTO) {
//
//        User existing = userRepository.findByUsername(userDTO.getUsername());
//        if (existing != null) {
//            return "Username already exists";
//        }
//
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(encoder.encode(userDTO.getPassword()));
//
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }
//
//    // Login
//    public String login(UserDTO userDTO) {
//
//        User existingUser = userRepository.findByUsername(userDTO.getUsername());
//
//        if (existingUser == null) {
//            return "User not found";
//        }
//
//        if (encoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
//
 //          return "Wrong password";
//    }
//
//    // All users
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // single user
//    public User getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    // delete
//    public String deleteUser(Long id) {
//        userRepository.deleteById(id);
//        return "User deleted";
//    }
//
//    // update
//    public User updateUser(Long id, User updatedUser) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setUsername(updatedUser.getUsername());
//        user.setPassword(encoder.encode(updatedUser.getPassword()));
//
//        return userRepository.save(user);
//    }
//}
//


////    Another method using JWT


package com.example.demo.service;

import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

//   For Refresh Token
import com.example.demo.model.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.dto.LoginResponseDTO;

import com.example.demo.dto.RefreshRequestDTO;

// import com.example.demo.exception.UserNotFoundException;

////   For Pagination + Sorting APIs
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

////  Logging Implementation
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// For User Logout
import com.example.demo.dto.LogoutRequestDTO;
import org.springframework.transaction.annotation.Transactional;

/////  For Email Verification System -->  This isn't Used Bcz of OTP Verification
// import com.example.demo.model.VerificationToken;
// import com.example.demo.repository.VerificationTokenRepository;

import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSender;

//// For OTP generation
import com.example.demo.model.OtpToken;
import com.example.demo.repository.OtpTokenRepository;
import java.time.LocalDateTime;

///// to email and password  for OTP verification  rather than username
import com.example.demo.dto.LoginRequestDTO;


/////    For Forgot Password
import com.example.demo.model.PasswordResetToken;
import com.example.demo.repository.PasswordResetTokenRepository;
import org.springframework.mail.SimpleMailMessage;
import com.example.demo.dto.ForgotPasswordRequestDTO;
import com.example.demo.dto.ResetPasswordDTO;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

//    @Autowired
//    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Signup
    public String signup(UserDTO userDTO) {

        User existing = userRepository.findByUsername(userDTO.getUsername());
        if (existing != null) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        ////  For ROLE-BASED AUTHORIZATION --> Implemented role-based authorization using Spring Security and JWT.
        user.setRole("USER");
        user.setEnabled(true);   // no email verification needed now

        /////  Logger Implementation
        logger.info("New user registration attempt: {}", userDTO.getUsername());

        // userRepository.save(user);

        userRepository.save(user);

//            // GENERATE TOKEN
//        String token = UUID.randomUUID().toString();
//
//           // SAVE TOKEN
//        VerificationToken verificationToken =
//                new VerificationToken();
//
//        verificationToken.setToken(token);
//        verificationToken.setUsername(user.getUsername());
//
//        verificationTokenRepository.save(verificationToken);
//
//           // SEND EMAIL
//        emailService.sendVerificationEmail(
//                user.getUsername(),
//                token
//        );


        logger.info("User registered successfully: {}", user.getUsername());

        return "User registered successfully";
    }

    // Login (JWT return)
//    public String login(UserDTO userDTO) {
//
//        User existingUser = userRepository.findByUsername(userDTO.getUsername());
//
//        if (existingUser == null) {
//           // return "User not found";
//            throw new RuntimeException("User not found");
//        }
//
//        if (encoder.matches(userDTO.getPassword(), existingUser.getPassword())) {
//          //  return JwtUtil.generateToken(existingUser.getUsername());
//
//           //// ROLE-BASED AUTHORIZATION
//            return JwtUtil.generateToken(
//                    existingUser.getUsername(),
//                    existingUser.getRole()
//            );
//        }
//
//       //  return "Wrong password";
//        throw new RuntimeException("Wrong password");
//    }


    ////// Old with email verification

//    public LoginResponseDTO login(UserDTO userDTO) {
//
//        User existingUser =
//                userRepository.findByUsername(userDTO.getUsername());
//
////        if (existingUser == null) {
////          //  throw new RuntimeException("User not found");
////
////            if (!existingUser.isEnabled()) {
////                throw new RuntimeException(
////                        "Please verify your email first"
////                );
////            }
////
////            /////   Logger Implementation
////            logger.error("Login failed. User not found: {}", userDTO.getUsername());
////            throw new UserNotFoundException("User not found");
////        }
//
//
//        //////   For Email login verification
//
//        if (existingUser == null) {
//
//            logger.error("Login failed. User not found: {}", userDTO.getUsername());
//
//            throw new UserNotFoundException("User not found");
//        }
//
//            // EMAIL VERIFICATION CHECK
//        if (!existingUser.isEnabled()) {
//
//            logger.error("Email not verified for user: {}",
//                    userDTO.getUsername());
//
//            throw new RuntimeException(
//                    "Please verify your email first"
//            );
//        }
//
//                   //// PASSWORD CHECK
//        if (!encoder.matches(
//                userDTO.getPassword(),
//                existingUser.getPassword())) {
//
//            logger.error("Wrong password for user: {}",
//                    userDTO.getUsername());
//
//            throw new InvalidCredentialsException("Wrong password");
//        }
//     //////     Implement Logger
//        logger.info("User logged in successfully: {}",
//                existingUser.getUsername());
//
//        // ACCESS TOKEN
//        String accessToken =
//                JwtUtil.generateToken(
//                        existingUser.getUsername(),
//                        existingUser.getRole()
//                );
//
//        // REFRESH TOKEN
//        String refreshToken =
//                JwtUtil.generateRefreshToken(
//                        existingUser.getUsername()
//                );
//
//        // SAVE REFRESH TOKEN
//        RefreshToken tokenObj = new RefreshToken();
//
//        tokenObj.setUsername(existingUser.getUsername());
//        tokenObj.setRefreshToken(refreshToken);
//
//        refreshTokenRepository.save(tokenObj);
//
//        // RESPONSE DTO
//        LoginResponseDTO response = new LoginResponseDTO();
//
//        response.setAccessToken(accessToken);
//        response.setRefreshToken(refreshToken);
//
//        return response;
//    }
//


    /////  This method is Use for OTP verification Rather Than Email Verification
    /////  BUt here username, email and password 3 things are required which is not suitable

//    public String login(UserDTO userDTO) {
//
//        User existingUser =
//                userRepository.findByEmail(userDTO.getEmail());
//
//        if (existingUser == null) {
//            logger.error("Login failed. Email not found: {}", userDTO.getEmail());
//            throw new UserNotFoundException("User not found");
//        }
//
//        if (!encoder.matches(
//                userDTO.getPassword(),
//                existingUser.getPassword())) {
//
//            logger.error("Wrong password for email: {}", userDTO.getEmail());
//            throw new InvalidCredentialsException("Wrong password");
//        }
//
//        // GENERATE OTP
//        String otp = String.valueOf(
//                (int) (100000 + Math.random() * 900000)
//        );
//
//        OtpToken otpToken = new OtpToken();
//        otpToken.setEmail(existingUser.getEmail());
//        otpToken.setOtp(otp);
//        otpToken.setExpiryTime(LocalDateTime.now().plusMinutes(5));
//        otpToken.setVerified(false);
//
//        otpTokenRepository.save(otpToken);
//
//        emailService.sendOtpEmail(existingUser.getEmail(), otp);
//
//        logger.info("OTP sent to: {}", existingUser.getEmail());
//
//        return "OTP sent successfully";
//    }


    ///// This is for just Email and Password Verification

    public String login(LoginRequestDTO loginRequestDTO) {

        User existingUser =
                userRepository.findByEmail(loginRequestDTO.getEmail());

        if (existingUser == null) {
            logger.error("Login failed. Email not found: {}", loginRequestDTO.getEmail());
            throw new UserNotFoundException("User not found");
        }


        if (existingUser.isBlocked()) {
            logger.error("Login blocked for user: {}", loginRequestDTO.getEmail());
            throw new RuntimeException("Your account has been blocked. Contact admin.");
        }


        if (!encoder.matches(
                loginRequestDTO.getPassword(),
                existingUser.getPassword())) {

            logger.error("Wrong password for email: {}", loginRequestDTO.getEmail());
            throw new InvalidCredentialsException("Wrong password");
        }

        // GENERATE OTP
        String otp = String.valueOf(
                (int) (100000 + Math.random() * 900000)
        );

        OtpToken otpToken = new OtpToken();
        otpToken.setEmail(existingUser.getEmail());
        otpToken.setOtp(otp);
        otpToken.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpToken.setVerified(false);

        otpTokenRepository.save(otpToken);

        emailService.sendOtpEmail(existingUser.getEmail(), otp);

        logger.info("OTP sent to: {}", existingUser.getEmail());

        return "OTP sent successfully";
    }




    ///// For  /verify-login-otp endpoint

    public LoginResponseDTO verifyLoginOtp(String email, String otp) {

        OtpToken otpToken =
                otpTokenRepository.findTopByEmailOrderByIdDesc(email);

        if (otpToken == null) {
            throw new RuntimeException("OTP not found. Please login again.");
        }

        if (otpToken.isVerified()) {
            throw new RuntimeException("OTP already used. Please login again.");
        }

        if (otpToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired. Please login again.");
        }

        if (!otpToken.getOtp().equals(otp)) {
            throw new InvalidCredentialsException("Invalid OTP");
        }

        otpToken.setVerified(true);
        otpTokenRepository.save(otpToken);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // ACCESS TOKEN
        String accessToken =
                JwtUtil.generateToken(
                        user.getUsername(),
                        user.getRole()
                );

        // REFRESH TOKEN
        String refreshToken =
                JwtUtil.generateRefreshToken(
                        user.getUsername()
                );

        RefreshToken tokenObj = new RefreshToken();
        tokenObj.setUsername(user.getUsername());
        tokenObj.setRefreshToken(refreshToken);

        refreshTokenRepository.save(tokenObj);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }



    // Get all users (DTO)
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll().stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());
            return dto;
        }).toList();
    }

    // Get by ID (DTO)
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
              //  .orElseThrow(() -> new RuntimeException("User not found"));

                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        return dto;
    }

    // Delete
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        ////  Implement logger
        logger.warn("User deleted with id: {}", id);

        userRepository.delete(user);

        return "User deleted";
    }



    ////  Blocked Feature For ADMIN

    public String blockUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setBlocked(true);
        userRepository.save(user);

        logger.warn("User blocked with id: {}", id);

        return "User blocked successfully";
    }

    public String unblockUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setBlocked(false);
        userRepository.save(user);

        logger.info("User unblocked with id: {}", id);

        return "User unblocked successfully";
    }

    public String changeUserRole(Long id, String newRole) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setRole(newRole);
        userRepository.save(user);

        logger.info("Role changed for user id: {} to {}", id, newRole);

        return "User role updated to " + newRole;
    }




    // Update
    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
              //  .orElseThrow(() -> new RuntimeException("User not found"));

        .orElseThrow(() ->
                new UserNotFoundException("User not found"));

        user.setUsername(updatedUser.getUsername());
        user.setPassword(encoder.encode(updatedUser.getPassword()));

       // return userRepository.save(existingUser);
        logger.info("User updated with id: {}", id);
         return userRepository.save(user);
    }


    // For profile API
    public UserResponseDTO getProfile(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            // throw new RuntimeException("User not found");
            throw new UserNotFoundException("User not found");
        }

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        return dto;
    }
    public User updateProfile(User updatedUser, HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        String token = authHeader.substring(7);

        String username = JwtUtil.extractUsername(token);

        User existingUser = userRepository.findByUsername(username);

        if (existingUser == null) {
            //throw new RuntimeException("User not found");

            throw new UserNotFoundException("User not found");
        }

        existingUser.setUsername(updatedUser.getUsername());

        existingUser.setPassword(
                encoder.encode(updatedUser.getPassword())
        );

        ////   Implement Logger
        logger.info("Profile updated for user: {}",
                existingUser.getUsername());
        return userRepository.save(existingUser);
    }
    public LoginResponseDTO refreshToken(
            RefreshRequestDTO requestDTO) {

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByRefreshToken(
                                requestDTO.getRefreshToken()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Invalid Refresh Token"));

        // validate refresh token
        JwtUtil.validateToken(
                requestDTO.getRefreshToken()
        );

        String username =
                JwtUtil.extractUsername(
                        requestDTO.getRefreshToken()
                );

        User user =
                userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // new access token
        String newAccessToken =
                JwtUtil.generateToken(
                        user.getUsername(),
                        user.getRole()
                );
      /////   Implemented Logger
        logger.info("Access token refreshed for user: {}", username);

        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setAccessToken(newAccessToken);

        // same refresh token
        response.setRefreshToken(
                requestDTO.getRefreshToken()
        );

        return response;
    }

    ////   Implement For User Logout
    @Transactional
    public String logout(LogoutRequestDTO requestDTO) {

        String token = requestDTO.getRefreshToken();

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByRefreshToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid Refresh Token"));

        refreshTokenRepository.deleteByRefreshToken(token);

        return "Logout Successful";
    }

    ////    For Pagination + Sorting
    public Page<User> getUsersWithPagination(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        return userRepository.findAll(pageable);
    }

    ////    For Search APIs
    public List<UserResponseDTO> searchUsers(String username) {

        List<User> users =
                userRepository.findByUsernameContainingIgnoreCase(username);

        return users.stream().map(user -> {

            UserResponseDTO dto = new UserResponseDTO();

            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRole(user.getRole());

            return dto;

        }).toList();
    }


    /////  This is for OTP verification password Reset

    public String forgotPassword(ForgotPasswordRequestDTO requestDTO) {

        User user = userRepository.findByEmail(requestDTO.getEmail());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        String otp = String.valueOf(
                (int) (100000 + Math.random() * 900000)
        );

        PasswordResetToken resetToken =
                new PasswordResetToken(
                        otp,
                        user.getEmail(),
                        LocalDateTime.now().plusMinutes(5)
                );

        passwordResetTokenRepository.save(resetToken);

        emailService.sendOtpEmail(user.getEmail(), otp);

        logger.info("Password reset OTP sent to: {}", user.getEmail());

        return "OTP sent to email for password reset";
    }



    ///////  Just for email verification not used any OTP

//    public String resetPassword(ResetPasswordDTO requestDTO) {
//
//        PasswordResetToken resetToken =
//                passwordResetTokenRepository
//                        .findByToken(requestDTO.getToken())
//                        .orElseThrow(() ->
//                                new RuntimeException("Invalid token"));
//
//        User user = userRepository.findByUsername(resetToken.getUsername());
//
//        if(user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        user.setPassword(
//                passwordEncoder.encode(requestDTO.getNewPassword())
//        );
//
//        userRepository.save(user);
//
//        passwordResetTokenRepository.delete(resetToken);
//
//        return "Password updated successfully";
//    }


    //////   This is for the OTP based reset/forget for verification
    public String resetPassword(ResetPasswordDTO requestDTO) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByToken(requestDTO.getOtp())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid OTP"));

        if (!resetToken.getUsername().equals(requestDTO.getEmail())) {
            throw new RuntimeException("Invalid OTP for this email");
        }

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired. Please try again.");
        }

        User user = userRepository.findByEmail(resetToken.getUsername());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        user.setPassword(
                passwordEncoder.encode(requestDTO.getNewPassword())
        );

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        return "Password updated successfully";
    }

}