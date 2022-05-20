package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));
        //assertThat(studentService.getAllStudents()).isEqualTo(expected);
        assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);
    }

    @Test
    void getStudentByEmail() {
        Student expected = new Student("reema@gmail.com", "reema brown", "password");
        Assertions.assertEquals(studentService.getStudentByEmail(expected.getEmail()), expected);
        //assertThat(studentService.getStudentByEmail(expected.getEmail())).isEqualTo(expected);
    }

    @Test
    void validateStudent() {
        Student expected = new Student("reema@gmail.com", "password");
        Assertions.assertTrue(studentService.validateStudent(expected.getEmail(), expected.getPassword()));
    }

    @Test
    void registerStudentToCourse() {
        Student semail = new Student("reema@gmail.com", "reema brown", "password");
        studentService.registerStudentToCourse(semail.getEmail(), 1);
        List<Course> scourse = studentService.getStudentByEmail(semail.getEmail()).getCourses();
        Assertions.assertTrue(scourse.get(0).getId() == 1);

    }

    @Test
    void getStudentCourses() {
        List<Course> courses = new ArrayList<>();
        Student semail = new Student("reema@gmail.com", "reema brown", "password");
        studentService.registerStudentToCourse(semail.getEmail(), 1);
        List<Course> scourse = studentService.getStudentCourses(semail.getEmail());
        System.out.println("checking: " + scourse.get(0).getName());
        Assertions.assertTrue(scourse.get(0).getName().equals("Java"));
    }


}
