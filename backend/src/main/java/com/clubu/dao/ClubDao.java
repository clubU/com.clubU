package com.clubu.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.clubu.server.orm.Club;
import com.clubu.server.utils.TextParsingUtils;

import io.dropwizard.hibernate.AbstractDAO;

public class ClubDao extends AbstractDAO<Club> {

    private static ClubDao instance = null;

    public static void initialize(SessionFactory sessionFactory) {
        instance = new ClubDao(sessionFactory);
    }

    public static ClubDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("ClubDao needs to be initialized first before use");
        }
        return instance;
    }

    private ClubDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Club> findAll() {
        return list(namedQuery(Club.QNAME_FIND_ALL));
    }

    public Club findById(long id) {
        List<Club> clubs = list(namedQuery(Club.QNAME_FIND_BY_ID).setLong("id", id));
        if (!clubs.isEmpty()) {
            return clubs.get(0);
        } else {
            return null;
        }
    }

    public Club findByUsername(String username) {
        List<Club> clubs = list(namedQuery(Club.QNAME_FIND_BY_USERNAME).setString("username", username));
        if (!clubs.isEmpty()) {
            return clubs.get(0);
        } else {
            return null;
        }
    }

    public Club createClub(
            String username,
            String password,
            String name,
            String email,
            String abbreviation,
            String description
            ) {
        Club club = new Club();
        Date now = new Date();
        club.setUsername(username);
        club.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        club.setName(name);
        club.setEmail(email);
        club.setAbbreviation(abbreviation);
        club.setDescription(description);
        club.setTimeCreated(now);
        club.setTimeUpdated(now);
        return persist(club);
    }

    public List<Club> findBySearchKeyword(String searchKeyword) {
        String param = "%" + searchKeyword + "%";
        return list(namedQuery(Club.QNAME_FIND_BY_SEARCH_KEYWORD).setString("searchKeyword", param));
    }

    public boolean validateCredentials(String username, String password) {
        Club club = findByUsername(username);
        return club == null ? false : BCrypt.checkpw(password, club.getPasswordHash());
    }

}

