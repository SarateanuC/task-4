package com.example.task4.mapper;

import com.example.task4.dbo.StudentDbo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentMapper {
    String SELECT_FROM_STUDENT_WHERE_UNIVERSITY = "SELECT * FROM student WHERE universities like '%#{university}%'";
    String SELECT_FROM_STUDENT = "select * from student";

    @Select(SELECT_FROM_STUDENT)
    @Results({
            @Result(property = "id", column = "student_id"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "eligibleForErasmusScholarship", column = "eligibleForErasmusScholarship"),
            @Result(property = "universities", column = "universities", typeHandler = com.example.task4.typeHandler.StringSplitTypeHandler.class, jdbcType = JdbcType.VARCHAR)
    })
    List<StudentDbo> findAll();

    @Select(SELECT_FROM_STUDENT_WHERE_UNIVERSITY)
    @Results({
            @Result(property = "id", column = "student_id"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "eligibleForErasmusScholarship", column = "eligibleForErasmusScholarship"),
            @Result(property = "universities", column = "universities")
    })
    List<StudentDbo> findByUniversity(String university);
}
