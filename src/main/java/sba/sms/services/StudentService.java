package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.List;

public class StudentService implements StudentI {

    @Override
    public void createStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public List<Student> getAllStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Student> students = null;
        List<Student> list = null;
        try {
            transaction = session.beginTransaction();
            TypedQuery<Student> query = session.createQuery("from Student", Student.class);
            list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }


    @Override
    public Student getStudentByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            Student student = new Student();
            student = session.get(Student.class, email);
            if(student == null)
                throw new HibernateException("Did not find the student by email");
            else {
                return student;
        }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return new Student();
    }


    @Override
    public boolean validateStudent(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            Student student = getStudentByEmail(email);
            if( student.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }


    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            course.addStudent(student);
            session.merge(course);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public List<Course> getStudentCourses(String email) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction();
        List<Course> courses = getStudentByEmail(email).getCourses();
        session.close();
        return courses;
    }


}
