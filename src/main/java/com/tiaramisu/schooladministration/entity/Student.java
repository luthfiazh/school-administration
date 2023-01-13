package com.tiaramisu.schooladministration.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "students")
@Entity
@Builder
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String studentId;
    @Column(length = 100, nullable = false)
    private String email;
    private String name;
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;
}
