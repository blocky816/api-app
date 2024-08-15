package com.tutorial.crud.repository;

import com.tutorial.crud.entity.MonthlyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyClassRepository extends JpaRepository<MonthlyClass, Long> {
    List<MonthlyClass> findAllByMonthAndYearAndMembershipAndActiveTrue(int month, int year, String membershipType);

    @Query("SELECT DISTINCT m.membership FROM MonthlyClass m WHERE m.active = true AND m.month = :month AND m.year = :year")
    List<String> findDistinctActiveMembershipsByMonthAndYear(@Param("month") int month, @Param("year") int year);

}
