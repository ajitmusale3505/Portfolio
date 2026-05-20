package com.edunest.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request body for  PATCH /api/users/profile
 * All fields are optional — only non-null fields will be updated.
 */
@Data
public class UpdateProfileRequest {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, dots and underscores")
    private String username;

    @Size(max = 300, message = "Bio cannot exceed 300 characters")
    private String bio;

    @Size(max = 150, message = "University name too long")
    private String university;

    @Size(max = 100, message = "Branch name too long")
    private String branch;

    @Min(value = 1, message = "Semester must be between 1 and 8")
    @Max(value = 8, message = "Semester must be between 1 and 8")
    private Integer semester;

    @Min(value = 1, message = "Year of study must be between 1 and 4")
    @Max(value = 4, message = "Year of study must be between 1 and 4")
    private Integer yearOfStudy;

    @Size(max = 50, message = "Enrollment number too long")
    private String enrollmentNumber;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}