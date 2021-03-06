package com.clubu.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.clubu.server.orm.Club;
import com.clubu.server.orm.Image;
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
        String programOfStudy,
		Image image
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
		student.setImage(image);
        student.setTimeCreated(now);
        student.setTimeUpdated(now);
        return persist(student);
    }

	public Student update(
		Long id,
		String password,
		String firstName,
		String lastName,
		String dateOfBirth,
		Integer yearOfStudy,
		String programOfStudy,
		Image image
	) {
		Student student = findById(id);
		if (student != null) {
			if (password != null)
        		student.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
			if (firstName != null)
        		student.setFirstName(firstName);
			if (lastName != null)
        		student.setLastName(lastName);
			if (dateOfBirth != null)
        		student.setDateOfBirth(TextParsingUtils.parseDate(dateOfBirth));
			if (yearOfStudy != null)
        		student.setYearOfStudy(yearOfStudy);
			if (programOfStudy != null)
        		student.setProgramOfStudy(programOfStudy);
			if (image != null)
				student.setImage(image);
            currentSession().update(student);
            return student;
		} else {
			return null;
		}
	}

    public Student addClub(Student student, Club club) {
        student.addClub(club);
        currentSession().update(student);
        return student;
    }

    public Student removeClub(Long studentId, Club club) {
        Student student = findById(studentId);
        if (student != null) {
            student.removeClub(club);
            currentSession().update(student);
            return student;
        } else {
            return null;
        }
    }

    public Student validateCredentials(String username, String password) {
        Student student = findByUsername(username);
        if (student != null) {
            return BCrypt.checkpw(password, student.getPasswordHash()) ? student : null;
        } else {
            return null;
        }
    }

}

