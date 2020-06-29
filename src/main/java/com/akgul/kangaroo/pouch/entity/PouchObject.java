package com.akgul.kangaroo.pouch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class PouchObject {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @JsonIgnore
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @JsonIgnore
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Version
    private Long version;

    @PrePersist
    public void onCreate(){
        createDate = Calendar.getInstance().getTime();
    }

    @PostPersist
    public void onUpdate(){
        modifiedDate = Calendar.getInstance().getTime();
    }
}
