package com.tiaramisu.schooladministration.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Data
@Table(name = "enrollments")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String enrollmentId;
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String teacherId;
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String studentId;
    private Date createdDate;
    private Date modifiedDate;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Enrollment that = (Enrollment) object;
        return teacherId.equals(that.teacherId) && studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, studentId);
    }
}
