package sba.sms.models;

import jakarta.persistence.*;
import lombok.NonNull;

import java.util.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    @NonNull
    private String name;

    @Column(length = 50)
    @NonNull
    private String instructor;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Student> students = new ArrayList<>();

    public Course(int id, String name, String instructor) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;

    }

    public void addStudent(Student student) {
        students.add(student);
        student.getCourses().add(this);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course() {
    }

    public Course(int id, String name, String instructor, List<Student> students) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.students = students;
    }

    public Course(@NonNull String name, @NonNull String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) &&
                Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor);
    }
}
