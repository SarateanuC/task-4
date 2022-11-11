package com.example.task4.mapper;

import com.example.task4.dbo.RegistrationDbo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
public interface RegistrationMapper {
    String SELECT_FROM_BOOK_WHERE_DATE = "SELECT * FROM registration " +
            "WHERE registration_date > #{startDate}" +
            "AND registration_date < #{currentDate}";

    @Select(SELECT_FROM_BOOK_WHERE_DATE)
    @Results({
            @Result(property = "id", column = "registration_id"),
            @Result(property = "registrationDate", column = "registration_date"),
            @Result(property = "studentDbo", column = "student", one = @One(select = "findByDate"))
    })
    List<RegistrationDbo> findByDate(LocalDate currentDate, LocalDate startDate);
}
