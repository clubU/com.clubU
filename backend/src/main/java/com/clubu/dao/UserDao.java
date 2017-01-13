package com.clubu.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.clubu.server.orm.User;
import com.clubu.server.utils.TextParsingUtils;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDao extends AbstractDAO<User> {

    private static UserDao instance = null;

    public static void initialize(SessionFactory sessionFactory) {
        instance = new UserDao(sessionFactory);
    }

    public static UserDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserDao needs to be initialized first before use");
        }
        return instance;
    }

    private UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findById(
            String id
            ) {
        List<User> users = list(namedQuery(User.QNAME_FIND_BY_ID).setLong("id", Long.parseLong(id)));
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByUsername(
            String username
            ) {
        List<User> users = list(namedQuery(User.QNAME_FIND_BY_USERNAME).setString("username", username));
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User createUser(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            String studentId,
            String dateOfBirth,
            String yearOfStudy,
            String programOfStudy
            ) {
        User user = new User();
        Date now = new Date();
        user.setUsername(username);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStudentId(studentId == null ? null : Integer.parseInt(studentId));
        user.setDateOfBirth(dateOfBirth == null ? null : TextParsingUtils.parseDate(dateOfBirth));
        user.setYearOfStudy(yearOfStudy == null ? null : Integer.parseInt(yearOfStudy));
        user.setProgramOfStudy(programOfStudy);
        user.setTimeCreated(now);
        user.setTimeUpdated(now);
        return persist(user);
    }

    public boolean validateCredentials(
            String username,
            String password
            ) {
        User user = findByUsername(username);
        return user == null ? false : BCrypt.checkpw(password, user.getPasswordHash());
    }

}

