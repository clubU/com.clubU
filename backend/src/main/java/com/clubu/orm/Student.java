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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "student")
@NamedQueries({
    @NamedQuery(
        name = Student.QNAME_FIND_ALL,
        query = "SELECT s FROM Student s"
    ),
    @NamedQuery(
        name = Student.QNAME_FIND_BY_ID,
        query = "SELECT s FROM Student s WHERE s.id = :id"
    ),
    @NamedQuery(
        name = Student.QNAME_FIND_BY_USERNAME,
        query = "SELECT s FROM Student s WHERE s.username = :username"
    )
})
public class Student {

    // Start of query names
    public static final String QNAME_FIND_ALL = "com.clubu.server.orm.Student.FIND_ALL";
    public static final String QNAME_FIND_BY_ID = "com.clubu.server.orm.Student.FIND_BY_ID";
    public static final String QNAME_FIND_BY_USERNAME = "com.clubu.server.orm.Student.QNAME_FIND_BY_USERNAME";
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

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "student_number", nullable = false)
    private Integer studentNumber;

    @Column(name = "date_of_birth", nullable = true)
    private Date dateOfBirth;

    @Column(name = "year_of_study", nullable = true)
    private Integer yearOfStudy;

    @Column(name = "program_of_study", nullable = true)
    private String programOfStudy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "subscription",
        joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "club_id", referencedColumnName = "id")}
    )
    @OrderBy("id ASC")
    @JsonIgnoreProperties({"students"})
    private List<Club> clubs;

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

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }
    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getProgramOfStudy() {
        return programOfStudy;
    }
    public void setProgramOfStudy(String programOfStudy) {
        this.programOfStudy = programOfStudy;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public List<Club> getClubs() {
        return clubs;
    }
    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
    public void addClub(Club club) {
        for (Club c : clubs) {
            if (c.getId() == club.getId()) {
                return;
            }
        }
        clubs.add(club);
    }
    public void removeClub(Club club) {
        clubs.remove(club);
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
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
