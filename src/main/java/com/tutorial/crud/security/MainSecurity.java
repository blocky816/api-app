package com.tutorial.crud.security;

import com.tutorial.crud.security.jwt.JwtEntryPoint;
import com.tutorial.crud.security.jwt.JwtTokenFilter;
import com.tutorial.crud.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/auth/**", 
                        "/citas/redimirPase",
                        "/alpha/recibosMes/**",
                        "/citas/actualizarPasesRedimidos",
                        "/citas/crearHorario",
                        "/parking/registrarEvento",
                        "/parking/chipsActivos/**",
                        "/parking/calcularAmonestaciones",
                        "/parking/qrParkingSportsPlaza",
                        "/abrir",
                        "/citas/obtenerClases",
                        "/citas/obtenerUsuariosByClaseApp",
                        "/citas/confirmarAsistencia",
                        "/citas/confirmarAsistencia",
                        "/citas/crearReserva",
                        "/citas/cancelarReserva",
                        "/alpha/facturacion",
                        "/alpha/clienteFactura/**",
                        "/alpha/descargarFactura",
                        "/rutina/datosBascula",
                        "/rutina/ultimoPesajeGeneral/**",
                        "/rutina/listaEjercicios",
                        "/rutina/obtenerEjercicio/**",
                        "/alpha/registrarAcceso",
                        "/alpha/aplicarPago",
                        "/alpha/idClienteByMembresia/**",
                        "/alpha/referencia",
                        "/alpha/referenciaDomiciliado",
                        "/alpha/guardarReferencia",
                        "/alpha/generarArchivoDomiciliacion",
                        "/alpha/updateCliente/**",
                        "/alpha/clienteByMembresia/**",
                        "/alpha/domiciliarCliente",
                        "/alpha/getPasswordHash/{customerID}",
                        "/rutinas/image/**",
                        "/conversaciones/bascula",
                        "/parking/getQRbyClub/{idClub}/{idFolio}",
                        "/parking/getStatusQRbyClub/{idClub}/{idFolio}",
                        "/parking/qrParking",
                        "/parking/generarCorte",
                        "/parking/getChipInfo/{ chipID }",
                        "/formularios/getCustomerPrompt/{customerID}",
                        "/formularios/getFoodCalories",
                        "/formularios/currentDietByCustomer/{customerID}",
                        "/formularios/assignDietForm/{customerID}",
                        "/formularios/assignDietForms/",
                        "/formularios/assignDietForms/{customerID}",
                        "/parking/getQRCostByUser/{userID}",
                        "/alpha/activateCustomer/{customerID}/{statusCobranza}",
                        "/encuesta/getByCliente/{idCliente}/{idClub}",
                        "/encuesta/getQuestionsByCliente/{idCliente}/{idSurvey}",
                        "/encuesta/sendRespuestas",
                        "/alpha/platinumUsersByClub/{clubID}",
                        //"/passes/generate-monthly-passes/{clubID}",
                        //"/passes/getByCustomer/{customerID}",
                        "/passes/getPassesByMembershipType/{membershipType}",
                        "/passes/getMonthlyPasses",
                        "/parking/licensePlatesByClub/{clubID}",
                        "/directDebit/create",
                        "/directDebit/cancel",
                        "/odoo/productos/{idCliente}",
                        "/odoo/invoice",
                        "/odoo/pases/{clienteId}/{idProd}",
                        "/odoo/torniquetes/{branchId}",
                        "/odoo//torniquete/acceso",
                        "/odoo/torniquete/abrir/{idTorniquete}",
                        "/citas/clases/aquadome/{idCliente}/{dia}",
                        "/openpay/getPedido/{idCliente}",
                        "/openpay/generar-referencia/{idCliente}/{vigenciaDias}",
                        "/openpayC/enviar-correo",
                        "/deportista/{idCliente}",
                        "/deportista/evaluaciones",
                        "/deportista/asistencias"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
       /* UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return source;*/

        /*UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // permitir todos los orígenes
        config.addAllowedHeader("*"); // permitir todos los encabezados
        config.addAllowedMethod("*"); // permitir todos los métodos (GET, POST, PUT, etc.)
        source.registerCorsConfiguration("/**", config); // aplicar la configuración a todas las rutas
        return source;*/
    }
}
