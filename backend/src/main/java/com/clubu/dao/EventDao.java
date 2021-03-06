package com.clubu.server.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;

import com.clubu.server.orm.Club;
import com.clubu.server.orm.Event;
import com.clubu.server.orm.Image;

import io.dropwizard.hibernate.AbstractDAO;

public class EventDao extends AbstractDAO<Event> {

    private static EventDao instance = null;

    public static void initialize(SessionFactory sessionFactory) {
        instance = new EventDao(sessionFactory);
    }

    public static EventDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("EventDao needs to be initialized first before use");
        }
        return instance;
    }

    private EventDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Event findById(Long id) {
        if (id == null) {
            return null;
        }
        List<Event> events = list(namedQuery(Event.QNAME_FIND_BY_ID).setLong("id", id));
        return events.isEmpty() ? null : events.get(0);
    }

    public Event create(
        Club club,
        String title,
        Long time,
        String location,
        String description,
		Image image
    ) {
        Event event = new Event();
        Date now = new Date();
        event.setClub(club);
        event.setTitle(title);
        event.setTime(new Date(time));
        event.setLocation(location);
        event.setDescription(description);
		event.setImage(image);
        event.setTimeCreated(now);
        event.setTimeUpdated(now);
        return persist(event);
    }

	public Event update(
        Long id,
        String title,
        Long time,
        String location,
        String description,
        Image image
    ) {
        Event event = findById(id);
        if (event != null) {
            if (title != null)
                event.setTitle(title);
            if (time != null)
                event.setTime(new Date(time));
            if (location != null)
                event.setLocation(location);
            if (description != null)
                event.setDescription(description);
            if (image != null)
                event.setImage(image);
            currentSession().update(event);
            return event;
        } else {
            return null;
        }
    }

}
