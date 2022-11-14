package com.example.task4.mapper;

import com.example.task4.dbo.StudentDbo;
import com.example.task4.dbo.StudentUniversityDbo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface StudentMapper {
    String SELECT_FROM_STUDENT_BY_UNIVERSITY = "SELECT * FROM student " +
            "LEFT JOIN student_university on student.student_id = student_university.student_id " +
            "WHERE university = #{university}";

    String SELECT_STUDENT_AND_UNIVERSITY = "SELECT s.firstname,s.age, s.lastname, s.gender,s.eligibleforerasmusscholarship, s.student_id " +
            "AS Student_id, su.student_id AS StudentUniversity_id, su.university " +
            "FROM student s " +
            "INNER JOIN student_university su " +
            "ON s.student_id = su.student_id ";

    String SELECT_FROM_STUDENT_BY_DATE = "SELECT * FROM student " +
            "LEFT JOIN registration " +
            "ON student.student_id = registration.student_id " +
            "WHERE registration_date > #{startDate} " +
            "AND registration_date < #{currentDate}";


    @Select(SELECT_FROM_STUDENT_BY_UNIVERSITY)
    List<StudentDbo> findByUniversity(String university);

    @Select(SELECT_STUDENT_AND_UNIVERSITY)
    List<StudentUniversityDbo> groupByUniversity();

    @Select(SELECT_FROM_STUDENT_BY_DATE)
    List<StudentDbo> findByDate(LocalDate currentDate, LocalDate startDate);
}
