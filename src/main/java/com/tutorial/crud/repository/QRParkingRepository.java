package com.tutorial.crud.repository;

import com.tutorial.crud.entity.QRParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QRParkingRepository extends JpaRepository<QRParking, Long> {

    QRParking findByClubAndIdRegistro(String club, String idRegistro);
    List<QRParking> findByClubAndIdUsuarioAndDailyQROutIsNull(String club, int idUsuario);

    List<QRParking> findByClubAndDailyQROutIsNullAndObservacionesStartingWithIgnoreCase(String club, String concepto);

    List<QRParking> findByClubAndDailyQROutIsNull(String club);
}
