package com.tutorial.crud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.entity.Employee;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.repository.EmployeeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static com.fasterxml.jackson.databind.node.InternalNodeMapper.JSON_MAPPER;
import org.hibernate.Session;

import javax.persistence.EntityManager;

@Service
@Transactional
public class EmployeeService {
    private static String EMPLOYEES_URL = "http://192.168.20.107:8000/rh/get_empleados";

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PaseUsuarioService paseUsuarioService;

    @Autowired
    EntityManager entityManager;

    public String getList(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response =
                client.send(request, BodyHandlers.ofString());

        return response.body();
    }

    public void saveEmployees() throws Exception {
        String employeesList = getList(EMPLOYEES_URL);
        JSONArray jsonArray = new JSONArray(employeesList);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            //System.out.println(object);
            Employee employee = new Employee();
            employee.setId(object.getInt("id"));
            employee.setIdEmpleado(object.getInt("id_empleado"));
            employee.setEmpleado(object.getString("empleado"));
            employee.setIniciales(object.getString("iniciales"));
            employee.setActivo(object.getString("activo"));
            employee.setClub(object.getString("club"));
            employee.setDepartamento(object.getString("departamento"));
            employee.setPuesto(object.getString("puesto"));
            employee.setClaveExterna(object.getString("clave_externa"));
            employee.setRfc(object.getString("rfc"));
            employee.setCurp(object.getString("curp"));
            employee.setImss(object.getString("imss"));
            employee.setEmpleadoTipo(object.getString("empleado_tipo"));
            employee.setFechaAlta(LocalDate.parse(object.getString("fecha_alta").isBlank()
                ? "2000-01-01" : object.getString("fecha_alta")));
            employee.setFechaNacimiento(LocalDate.parse(object.getString("fecha_nacimiento").isBlank()
                    ? "2000-01-01" : object.getString("fecha_nacimiento")));
            employeeRepository.save(employee);
        }
    }

    public Employee findById(int employeeID) {
        return employeeRepository.getOne(employeeID);
    }

    public Employee findByClaveExterna(String claveExterna) { return employeeRepository.findByClaveExterna(claveExterna); }

    public List<PaseUsuario> getParkingQR(String employeeID) {
        /*System.out.println("ID Employee: " + employeeID);
        System.out.println(findByClaveExterna(employeeID));*/
        if (findByClaveExterna(employeeID.trim()) != null) {
            PaseUsuario paseUsuario=new PaseUsuario();
            paseUsuario.setActivo(true);
            paseUsuario.setCantidad(0);
            paseUsuario.setConcepto(employeeID);
            paseUsuario.setConsumido(0);
            paseUsuario.setCreated(new Date());
            paseUsuario.setCreatedBy(employeeID);
            paseUsuario.setDisponibles(0);
            paseUsuario.setF_compra(new Date());
            paseUsuario.setIdProd(1808);
            paseUsuario.setUpdated(new Date());
            paseUsuario.setUpdatedBy(employeeID);
            Session currentSession = entityManager.unwrap(Session.class);
            javax.persistence.Query query = currentSession.createNativeQuery("Select coalesce(min(id_venta_detalle),-1) from pase_usuario WHERE id_venta_detalle<0;");
            int max = ((Number) query.getSingleResult()).intValue();
            paseUsuario.setIdVentaDetalle(max-1);
            paseUsuarioService.save(paseUsuario);
            List<PaseUsuario> paseAux = new ArrayList<PaseUsuario>();
            paseAux.add(paseUsuario);
            return paseAux;
        }
        return null;
    }
}
