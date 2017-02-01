package com.clubu.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.clubu.server.orm.Club;
import com.clubu.server.orm.Student;
import com.clubu.server.utils.TextParsingUtils;

import io.dropwizard.hibernate.AbstractDAO;

public class StudentDao extends AbstractDAO<Student> {

    private static StudentDao instance = null;

    public static void initialize(SessionFactory sessionFactory) {
        instance = new StudentDao(sessionFactory);
    }

    public static StudentDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("StudentDao needs to be initialized first before use");
        }
        return instance;
    }

    private StudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Student> findAll() {
        return list(namedQuery(Student.QNAME_FIND_ALL));
    }

    public Student findById(long id) {
        List<Student> students = list(namedQuery(Student.QNAME_FIND_BY_ID).setLong("id", id));
        if (!students.isEmpty()) {
            return students.get(0);
        } else {
            return null;
        }
    }

    public Student findByUsername(String username) {
        List<Student> students = list(namedQuery(Student.QNAME_FIND_BY_USERNAME).setString("username", username));
        if (!students.isEmpty()) {
            return students.get(0);
        } else {
            return null;
        }
    }

    public Student createStudent(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            String studentNumber,
            String dateOfBirth,
            String yearOfStudy,
            String programOfStudy
            ) {
        Student student = new Student();
        Date now = new Date();
        student.setUsername(username);
        student.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setStudentNumber(studentNumber == null ? null : Integer.parseInt(studentNumber));
        student.setDateOfBirth(dateOfBirth == null ? null : TextParsingUtils.parseDate(dateOfBirth));
        student.setYearOfStudy(yearOfStudy == null ? null : Integer.parseInt(yearOfStudy));
        student.setProgramOfStudy(programOfStudy);
        student.setTimeCreated(now);
        student.setTimeUpdated(now);
        return persist(student);
    }

    public Student addClub(Student student, Club club) {
        student.addClub(club);
        currentSession().update(student);
        return student;
    }

    public boolean validateCredentials(String username, String password) {
        Student student = findByUsername(username);
        return student == null ? false : BCrypt.checkpw(password, student.getPasswordHash());
    }

}

