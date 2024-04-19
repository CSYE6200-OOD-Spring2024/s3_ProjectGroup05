package com.example.Course.Registration.controllers;

import com.example.Course.Registration.Services.CourseService;
import com.example.Course.Registration.payload.response.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;

import com.example.Course.Registration.Services.UserService;
import com.example.Course.Registration.models.Courses;
import com.example.Course.Registration.models.User;
import com.example.Course.Registration.payload.request.AddCourseRequest;
import com.example.Course.Registration.payload.request.CourseRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CourseController {

    private String email = null;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;
    private AbstractResponseFactory MessageResponseFactory = new MessageResponseFactory();
    private AbstractResponseFactory CourseResponsseFactory = new CourseResponseFactory();

    private void setAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle the case when there is no authenticated user
            email = null;
            return;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername(); // Assuming email is stored in the username field
        } else {
            email = null;
        }
    }

    private static Date parseMongoDbTime(String mongoDbTimeString) {
        try {
            SimpleDateFormat mongoDbDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            return convertToTimeZone(mongoDbDateFormat.parse(mongoDbTimeString),
                    TimeZone.getTimeZone("America/New_York"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert to a specific timezone
    private static Date convertToTimeZone(Date date, TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        sdf.setTimeZone(timeZone);
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to extract time component
    private static Date extractTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            return timeFormat.parse(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean checkClash(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    @GetMapping("/getAllCourses")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCourses() {
        setAuthenticatedUserEmail();
        if (email == null) {
            return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: User not logged in"));
        }
        System.out.println(courseService.getCourses().toString());
        List<AbstractResponse> courseResponse = courseService.getCourses().stream()
                .sorted(Comparator.comparing(Courses::getTitle))
                .map(course -> CourseResponsseFactory.getResponse(course.toString()))
                .toList();

        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/userCourses")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCourse() {
        System.out.println("efefefe");
        setAuthenticatedUserEmail();
        if (email == null) {
            return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: User not logged in"));
        }

        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: User not found"));
        }
        List<AbstractResponse> userCourseRespone = user.getCourses().stream()
                .sorted()
                .map(course -> CourseResponsseFactory.getResponse(
                        courseService.getCourseByCRN(course.getCRN()).toString()))
                .toList();

        return ResponseEntity.ok(userCourseRespone);

    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> registerCourse(@Valid @RequestBody CourseRequest registerCourseRequest) {
        if (email == null) {
            setAuthenticatedUserEmail();
            if (email == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: User not logged in"));
            }
        }
        if (registerCourseRequest.getCourseCRNS().length == 0) {
            return ResponseEntity.badRequest()
                    .body(MessageResponseFactory.getResponse("Error: No courses to register"));
        }
        for (String CRN : registerCourseRequest.getCourseCRNS()) {
            Courses course = courseService.getCourseByCRN(Integer.parseInt(CRN));
            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " not found"));
            }
            if (course.getSeats() == 0) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " is full"));

            }
            User user = userService.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: User not found"));
            }

            if (user.getCourses().stream().anyMatch(c -> c.getCRN().equals(Integer.parseInt(CRN)))) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " already registered"));
            }
            for (Courses c : user.getCourses()) {
                if (c.getClassTiming().getDay().equalsIgnoreCase(course.getClassTiming().getDay())
                        && checkClash(parseMongoDbTime(c.getClassTiming().getStartTime()),
                                parseMongoDbTime(c.getClassTiming().getEndTime()),
                                parseMongoDbTime(course.getClassTiming().getStartTime()),
                                parseMongoDbTime(course.getClassTiming().getEndTime()))) {
                    return ResponseEntity.badRequest()
                            .body(MessageResponseFactory
                                    .getResponse("Error: Course " + CRN + " clashes with course " + c.getCRN()));
                }
            }
            Integer total_credits = user.getCourses().stream()
                    .mapToInt(Courses::getHours)
                    .sum();
            if (total_credits + course.getHours() > 12) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " exceeds credit limit"));
            }

            user.getCourses().add(course);
            userService.save(user);
            course.setEnrollment(course.getEnrollment() + 1);
            courseService.addCourse(course);
        }
        return ResponseEntity.ok().body(MessageResponseFactory.getResponse("Course registered successfully"));
    }

    @PostMapping("/drop")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> dropCourse(@Valid @RequestBody CourseRequest dropCourseRequest) {
        if (email == null) {
            setAuthenticatedUserEmail();
            if (email == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: User not logged in"));
            }
        }
        if (dropCourseRequest.getCourseCRNS().length == 0) {
            return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: No courses to drop"));
        }
        for (String CRN : dropCourseRequest.getCourseCRNS()) {
            Courses course = courseService.getCourseByCRN(Integer.parseInt(CRN));
            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " not found"));
            }
            course.setSeats(course.getSeats() + 1);
            course.setEnrollment(course.getEnrollment() - 1);
            courseService.addCourse(course);

            User user = userService.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: User not found"));
            }
            if (!user.getCourses().stream().anyMatch(c -> c.getCRN().equals(Integer.parseInt(CRN)))) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " not registered"));
            }
            user.getCourses().removeIf(c -> c.getCRN().equals(Integer.parseInt(CRN)));
            userService.save(user);
        }
        return ResponseEntity.ok().body(MessageResponseFactory.getResponse("Course dropped successfully"));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addCourse(@Valid @RequestBody AddCourseRequest courses) {
        for (Courses course : courses.getCourses()) {
            // System.out.println(course.getTitle());
            if (courseService.getCourseByCRN(course.getCRN()) != null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory
                                .getResponse("Error: Course " + course.getCRN() + " already exists"));
            }

            if (course.getSeats() == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + course.getCRN() + " has no seats"));
            }
            // try {
            // SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
            // Date startTime = inputFormat.parse(course.getClassTiming().getStartTime());
            // String startTime =
            // course.getClassTiming().setStartTime(startTime.toString());
            // Date endTime = inputFormat.parse(course.getClassTiming().getEndTime());
            // course.getClassTiming().setEndTime(endTime.toString());
            // } catch (ParseException e) {
            // return ResponseEntity.badRequest()
            // .body(MessageResponseFactory.getResponse("Error: Invalid time format"));
            // }
            courseService.addCourse(course);
        }
        return ResponseEntity.ok().body(MessageResponseFactory.getResponse("Course added successfully"));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deleteCourse(@Valid @RequestBody CourseRequest deleteCourseRequest) {
        if (deleteCourseRequest.getCourseCRNS().length == 0) {
            return ResponseEntity.badRequest().body(MessageResponseFactory.getResponse("Error: No courses to delete"));
        }
        for (String CRN : deleteCourseRequest.getCourseCRNS()) {
            Courses course = courseService.getCourseByCRN(Integer.parseInt(CRN));
            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(MessageResponseFactory.getResponse("Error: Course " + CRN + " not found"));
            }
            courseService.deleteCourse(course);
        }
        return ResponseEntity.ok().body(MessageResponseFactory.getResponse("Course deleted successfully"));
    }

}
