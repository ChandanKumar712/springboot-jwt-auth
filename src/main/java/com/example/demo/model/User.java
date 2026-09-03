//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//// import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Data
//
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String username;
//
//   // @JsonIgnore
//    private String password;
// }


// ROLE-BASED AUTHORIZATION ----->

package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    // Add email field
    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private boolean enabled = false;

    private boolean blocked = false;


}