package com.clubu.server.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "club")
@NamedQueries({
    @NamedQuery(
        name = Club.QNAME_FIND_ALL,
        query = "SELECT c FROM Club c"
    ),
    @NamedQuery(
        name = Club.QNAME_FIND_BY_ID,
        query = "SELECT c FROM Club c WHERE c.id = :id"
    ),
    @NamedQuery(
        name = Club.QNAME_FIND_BY_USERNAME,
        query = "SELECT c FROM Club c WHERE c.username = :username"
    ),
    @NamedQuery(
        name = Club.QNAME_FIND_BY_SEARCH_KEYWORD,
        query = "SELECT c FROM Club c WHERE c.name LIKE :searchKeyword"
    )
})
public class Club {

    // Start of query names
    public static final String QNAME_FIND_ALL = "com.clubu.server.orm.Club.FIND_ALL";
    public static final String QNAME_FIND_BY_ID = "com.clubu.server.orm.Club.FIND_BY_ID";
    public static final String QNAME_FIND_BY_USERNAME = "com.clubu.server.orm.Club.FIND_BY_USERNAME";
    public static final String QNAME_FIND_BY_SEARCH_KEYWORD = "com.clubu.server.orm.Club.FIND_BY_SEARCH_KEYWORD";
    // End of query names

    // Start of member fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "abbreviation", nullable = true)
    private String abbreviation;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "subscription",
        joinColumns = {@JoinColumn(name = "club_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")}
    )
    @OrderBy("id ASC")
    @JsonIgnoreProperties({"clubs"})
    private List<Student> students;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id")
    @JsonIgnoreProperties({"club"})
    private List<Event> events;

	@OneToOne(cascade = CascadeType.ALL)
	private Image image;

    @Column(name = "time_created", nullable = false)
    private Date timeCreated;

    @Column(name = "time_updated", nullable = false)
    private Date timeUpdated;
    // End of member fields

    // Start of member getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events; 
    }

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

    public Date getTimeCreated() {
        return timeCreated;
    }
    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated() {
        return timeUpdated;
    }
    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
    // End of member getters and setters

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Club other = (Club) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
