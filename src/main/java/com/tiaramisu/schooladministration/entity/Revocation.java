package com.tiaramisu.schooladministration.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "revocations")
@Entity
@Builder
@AllArgsConstructor
public class Revocation {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String revocationId;
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String enrollmentId;
    private String reason;
    private Date createdDate;
    private Date modifiedDate;
}
