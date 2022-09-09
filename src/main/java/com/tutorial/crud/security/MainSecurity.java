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
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/citas/redimirPase").permitAll()
                .antMatchers("/citas/actualizarPasesRedimidos").permitAll()
                .antMatchers("/citas/crearHorario").permitAll()
                .antMatchers("/parking/registrarEvento").permitAll()
                .antMatchers("/parking/chipsActivos/**").permitAll()
                .antMatchers("/parking/calcularAmonestaciones").permitAll()
                .antMatchers("/parking/qrParkingSportsPlaza").permitAll()
                .antMatchers("/abrir").permitAll()
                .antMatchers("/citas/obtenerClases").permitAll()
                .antMatchers("/citas/obtenerUsuariosByClaseApp").permitAll()
                .antMatchers("/citas/confirmarAsistencia").permitAll()
                .antMatchers("/citas/confirmarAsistencia").permitAll()
                .antMatchers("/citas/crearReserva").permitAll()
                .antMatchers("/citas/cancelarReserva").permitAll()
                .antMatchers("/alpha/facturacion").permitAll()
                .antMatchers("/alpha/clienteFactura/**").permitAll()
                .antMatchers("/alpha/descargarFactura").permitAll()
                .antMatchers("/rutina/datosBascula").permitAll()
                .antMatchers("/rutina/ultimoPesajeGeneral/**").permitAll()
                .antMatchers("/rutina/listaEjercicios").permitAll()
                .antMatchers("/rutina/obtenerEjercicio/**").permitAll()
                .antMatchers("/alpha/registrarAcceso").permitAll()
                .antMatchers("/alpha/aplicarPago").permitAll()
                .antMatchers("/alpha/idClienteByMembresia/**").permitAll()
                .antMatchers("/alpha/referencia").permitAll()
                .antMatchers("/alpha/guardarReferencia").permitAll()
                .antMatchers("/alpha/generarArchivoDomiciliacion").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.requiresChannel().antMatchers("/secure*").requiresSecure();
    }
}
