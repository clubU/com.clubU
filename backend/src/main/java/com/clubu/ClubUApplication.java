package com.clubu.server;

import com.clubu.server.api.ClubApi;
import com.clubu.server.api.EventApi;
import com.clubu.server.api.SessionApi;
import com.clubu.server.api.StudentApi;
import com.clubu.server.api.SubscriptionApi;
import com.clubu.server.dao.ClubDao;
import com.clubu.server.dao.EventDao;
import com.clubu.server.dao.StudentDao;
import com.clubu.server.orm.Club;
import com.clubu.server.orm.Event;
import com.clubu.server.orm.Student;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ClubUApplication extends Application<ClubUConfiguration>{

    public static void main(String args[]) throws Exception {
        new ClubUApplication().run(args);
    }

    private ClubUConfiguration config;
    private Environment env;
    private final HibernateBundle<ClubUConfiguration> hibernateBundle =
            new HibernateBundle<ClubUConfiguration>(
                    Club.class,
                    Event.class,
                    Student.class
            ) {
                public DataSourceFactory getDataSourceFactory(ClubUConfiguration config) {
                    return config.getDataSourceFactory();
                }
            };
    private final MigrationsBundle<ClubUConfiguration> migrationsBundle =
            new MigrationsBundle<ClubUConfiguration>() {
                public DataSourceFactory getDataSourceFactory(ClubUConfiguration config) {
                    return config.getDataSourceFactory();
                }
            };

    @Override
    public void run(ClubUConfiguration config, Environment env) throws Exception {
        ClubUConfiguration.setInstance(config);
        this.config = config;
        this.env = env;
        registerDao();
        registerApi();
    }

    @Override
    public void initialize(Bootstrap<ClubUConfiguration> bootstrap) {
        bootstrap.addBundle(migrationsBundle);
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public String getName() {
        return "clubu-server";
    }

    public ClubUConfiguration getConfig() {
        return config;
    }

    private void registerDao() {
        ClubDao.initialize(hibernateBundle.getSessionFactory());
        EventDao.initialize(hibernateBundle.getSessionFactory());
        StudentDao.initialize(hibernateBundle.getSessionFactory());
    }

    private void registerApi() {
        env.jersey().register(new ClubApi());
        env.jersey().register(new EventApi());
        env.jersey().register(new SessionApi());
        env.jersey().register(new StudentApi());
        env.jersey().register(new SubscriptionApi());
    }

}

