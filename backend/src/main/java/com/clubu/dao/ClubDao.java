package com.clubu.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.clubu.server.orm.Club;
import com.clubu.server.orm.Image;
import com.clubu.server.search.ClubSearchEngine;
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

	private ClubSearchEngine searchEngine = null;

    private ClubDao(SessionFactory sessionFactory) {
        super(sessionFactory);
		searchEngine = ClubSearchEngine.getInstance();
    }

    public List<Club> findAll() {
        return list(namedQuery(Club.QNAME_FIND_ALL));
    }

    public Club findById(Long id) {
		if (id == null) {
			return null;
		}
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
        String description,
		Image image	
    ) {
        Club club = new Club();
        Date now = new Date();
        club.setUsername(username);
        club.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        club.setName(name);
        club.setEmail(email);
        club.setAbbreviation(abbreviation);
        club.setDescription(description);
		club.setImage(image);
        club.setTimeCreated(now);
        club.setTimeUpdated(now);
        return persist(club);
    }

	public Club update(
		Long id,
		String password,
		String abbreviation,
		String description,
		Image image
	) {
		Club club = findById(id);
		if (club != null) {
			if (password != null)
        		club.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
			if (abbreviation != null)
				club.setAbbreviation(abbreviation);
			if (description != null)
				club.setDescription(description);
			if (image != null)
				club.setImage(image);
            currentSession().update(club);
            return club;
		} else {
			return null;
		}
	}

    public List<Club> findBySearchKeyword(String keyword) {
/*
		// LEGACY CODE
        String param = "%" + searchKeyword + "%";
        return list(namedQuery(Club.QNAME_FIND_BY_SEARCH_KEYWORD).setString("searchKeyword", param));
*/
		List<Club> ret = new ArrayList<Club>();
		List<Long> ids = searchEngine.executeQuery(keyword);
		for (Long id : ids) {
			ret.add(findById(id));
		}
		return ret;
    }

    public Club validateCredentials(String username, String password) {
        Club club = findByUsername(username);
        if (club != null) {
            return BCrypt.checkpw(password, club.getPasswordHash()) ? club : null;
        } else {
            return null;
        }
    }

}

