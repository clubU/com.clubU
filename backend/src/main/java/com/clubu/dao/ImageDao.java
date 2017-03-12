package com.clubu.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.clubu.server.orm.Image;

import io.dropwizard.hibernate.AbstractDAO;

public class ImageDao extends AbstractDAO<Image> {

    private static ImageDao instance = null;

    public static void initialize(SessionFactory sessionFactory) {
        instance = new ImageDao(sessionFactory);
    }

    public static ImageDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("ImageDao needs to be initialized first before use");
        }
        return instance;
    }

    private ImageDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Image findById(long id) {
        List<Image> images = list(namedQuery(Image.QNAME_FIND_BY_ID).setLong("id", id));
        return images.isEmpty() ? null : images.get(0);
    }

    public Image create (byte[] content) {
        Image image = new Image();
        image.setContent(content);
        return persist(image);
    }

}

