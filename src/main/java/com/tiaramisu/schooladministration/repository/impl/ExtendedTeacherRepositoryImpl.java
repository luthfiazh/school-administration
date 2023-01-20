package com.tiaramisu.schooladministration.repository.impl;

import com.tiaramisu.schooladministration.model.TeacherStudentsMapping;
import com.tiaramisu.schooladministration.repository.ExtendedTeacherRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ExtendedTeacherRepositoryImpl implements ExtendedTeacherRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TeacherStudentsMapping> findAllTeacher() {
        final String queryString = "SELECT t.email, group_concat(DISTINCT s.email) as students " +
                "FROM teachers t " +
                "LEFT JOIN enrollments e ON t.teacher_id = e.teacher_id " +
                "LEFT JOIN students s ON e.student_id = s.student_id " +
                "GROUP BY t.teacher_id;";
        final String RESULT_SET_MAPPING_NAME = "TeacherStudentsMapping";
        Query query = entityManager.createNativeQuery(queryString, RESULT_SET_MAPPING_NAME);
        return query.getResultList();
    }
}
