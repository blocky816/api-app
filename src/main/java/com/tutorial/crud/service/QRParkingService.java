package com.tutorial.crud.service;

import com.tutorial.crud.dto.QRParkingDTO;
import com.tutorial.crud.entity.DailyQROut;
import com.tutorial.crud.entity.EstacionamientoExterno;
import com.tutorial.crud.entity.QRParking;
import com.tutorial.crud.repository.DailyQROutRepository;
import com.tutorial.crud.repository.QRParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
public class QRParkingService {

    @Autowired
    EstacionamientoExternoService estacionamientoExternoService;
    @Autowired
    QRParkingRepository qrParkingRepository;
    @Autowired
    DailyQROutRepository dailyQROutRepository;

    public QRParking findByClubAndIdRegistro(String club, String idRegistro) {
        return qrParkingRepository.findByClubAndIdRegistro(club, idRegistro);
    }

    public QRParking save(QRParkingDTO qrParkingDTO) {
        System.out.println("QRRequest de qrparkingusuario => " + qrParkingDTO);
        EstacionamientoExterno estacionamientoExterno = estacionamientoExternoService.getByIdRegistro(qrParkingDTO.getIdRegistro(), qrParkingDTO.getClub());
        QRParking qrParkingFound = findByClubAndIdRegistro(qrParkingDTO.getClub(), qrParkingDTO.getIdRegistro());
        if (qrParkingFound != null) {
            qrParkingFound.setDebito(qrParkingDTO.getDebito() + qrParkingFound.getDebito());
            qrParkingFound.setPagado(qrParkingDTO.getPagado());
            qrParkingFound.setCambio(qrParkingDTO.getCambio());
            //if (qrParkingDTO.getCambio() == qrParkingFound.getDebito() - qrParkingFound.getCosto()) qrParkingFound.setDevuelto(true);
            System.out.println("cambio => " + qrParkingDTO.getCambio());
            if (qrParkingDTO.getCambio() == 0) qrParkingFound.setDevuelto(true);
            qrParkingRepository.save(qrParkingFound);
            if (qrParkingFound.isPagado()) {
                estacionamientoExterno.setActivo(true);
                estacionamientoExternoService.save(estacionamientoExterno);
            }
            return qrParkingFound;
        } else {
            if (estacionamientoExterno instanceof EstacionamientoExterno) {
                System.out.println("Lo encontre! " + estacionamientoExterno);
                QRParking qrParking = new QRParking();
                qrParking.setIdRegistro(qrParkingDTO.getIdRegistro());
                qrParking.setClub(qrParkingDTO.getClub());
                qrParking.setIdUsuario(qrParkingDTO.getIdUsuario());
                qrParking.setCosto(qrParkingDTO.getCosto());
                qrParking.setDebito(qrParkingDTO.getDebito());
                qrParking.setPagado(qrParkingDTO.getPagado());
                qrParking.setObservaciones(qrParkingDTO.getObservaciones());
                qrParking.setCambio(qrParkingDTO.getCambio());
                qrParking.setCambioFinal(qrParkingDTO.getDebito() - qrParkingDTO.getCosto());
                //if (qrParkingDTO.getCambio() == qrParking.getDebito() - qrParking.getCosto()) qrParking.setDevuelto(true);
                if (qrParkingDTO.getCambio() == 0) qrParking.setDevuelto(true);
                qrParkingRepository.save(qrParking);
                if (qrParking.isPagado()) {
                    estacionamientoExterno.setActivo(true);
                    estacionamientoExternoService.save(estacionamientoExterno);
                }
                return qrParking;
            }
        }
        return null;
    }

    public DailyQROut generarCorte(String club, int idUsuario) {
        List<QRParking> qrParkingList = qrParkingRepository.findByClubAndIdUsuarioAndDailyQROutIsNull(club, idUsuario);
        System.out.println(qrParkingList);

        if (!qrParkingList.isEmpty()) {
            //System.out.println("La lista no esta vacia");
            float total = (float) qrParkingList.stream().mapToDouble(l -> l.getCosto()).sum();
            //System.out.println("Sum => " + total);

            DailyQROut dailyQROut = new DailyQROut();
            dailyQROut.setClub(club);
            dailyQROut.setCreatedBy(idUsuario);
            dailyQROut.setUpdatedBy(idUsuario);
            dailyQROut.setTotal(total);
            dailyQROut.setQrParkings(qrParkingList);
            dailyQROutRepository.save(dailyQROut);
            return dailyQROut;
        }
        //DailyQROut find = dailyQROutRepository.findById(25L).get();
        //System.out.println(find);
        return null;
    }
}
