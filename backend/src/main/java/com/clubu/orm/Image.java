package com.clubu.server.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "image")
@NamedQueries({
    @NamedQuery(
        name = Image.QNAME_FIND_BY_ID,
        query = "SELECT i FROM Image i WHERE i.id = :id"
    )
})
public class Image {

    // Start of query names
    public static final String QNAME_FIND_BY_ID = "com.clubu.server.orm.Image.FIND_BY_ID";
    // End of query names

    // Start of member fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private byte[] content;
    // End of member fields

    // Start of member getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    // End of member getters and setters

}

