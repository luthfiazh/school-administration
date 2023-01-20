package com.tiaramisu.schooladministration.entity;

import com.tiaramisu.schooladministration.model.TeacherStudentsMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.util.Date;

@SqlResultSetMapping(
        name = "TeacherStudentsMapping",
        classes = @ConstructorResult(
                targetClass = TeacherStudentsMapping.class,
                columns = {
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "students", type = String.class)
                }
        )
)

@Data
@Table(name = "teachers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36, nullable = false, columnDefinition = "VARCHAR(36)")
    private String teacherId;
    @Column(length = 100, nullable = false)
    private String email;
    private String name;
    private Date createdDate;
    private Date modifiedDate;
}
