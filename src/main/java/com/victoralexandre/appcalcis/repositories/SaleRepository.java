package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findByMomentBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT DATE(s.moment) AS data, SUM(s.total) AS faturamento " +
            "FROM Sale s " +
            "GROUP BY DATE(s.moment) " +
            "ORDER BY faturamento DESC " +
            "LIMIT 1")
    Sale findDayWithHighestTotalSales();
}