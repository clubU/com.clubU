package com.clubu.server;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ClubUConfiguration extends Configuration {

    private static ClubUConfiguration instance;
    public static void setInstance(ClubUConfiguration instance) {
        ClubUConfiguration.instance = instance;
    }
    public static ClubUConfiguration getInstance() {
        return instance;
    }

    // Start of database section
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
    // End of database section

}
