package com.tiaramisu.schooladministration.repository.impl;

import com.tiaramisu.schooladministration.repository.ExtendedEnrollmentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ExtendedEnrollmentRepositoryImpl implements ExtendedEnrollmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findCommonStudentsIdByTeacherIds(List<String> teacherIds) {
        StringBuilder stringBuilder = new StringBuilder();
        final String SELECT_QUERY = "SELECT s.email FROM students s ";
        stringBuilder.append(SELECT_QUERY);
        for (int i = 0; i < teacherIds.size(); i++) {
            final String joinClause = String.format("INNER JOIN enrollments e%s ON s.student_id = e%s.student_id " +
                    "AND e%s.teacher_id = '%s' ", i, i, i, teacherIds.get(i));
            stringBuilder.append(joinClause);
        }
        return entityManager.createNativeQuery(stringBuilder.toString()).getResultList();
    }
}
