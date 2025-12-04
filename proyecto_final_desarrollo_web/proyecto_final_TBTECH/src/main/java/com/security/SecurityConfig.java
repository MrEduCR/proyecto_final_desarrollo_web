package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService customUserDetailsService;

    public SecurityConfig(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .userDetailsService(customUserDetailsService)

                .authorizeHttpRequests(auth -> auth // Lo que realmente protege son rutas de controlador NO LOS HTML
                                            
                    .requestMatchers("/index").hasAnyRole("ADMIN", "TECNICO") 
                    // Usuarios
                    .requestMatchers("/usuario").hasRole("ADMIN")
                    .requestMatchers("/usuario/nuevo").hasRole("ADMIN")
                    .requestMatchers("/usuario/editar/**").hasRole("ADMIN")
                    .requestMatchers("/usuario/guardar").hasRole("ADMIN")
                    .requestMatchers("/usuario/configuracion", "/usuario/configuracion/guardar").hasAnyRole("ADMIN", "TECNICO")

                    // Tipos de servicio
                    .requestMatchers("/tiposervicio").hasRole("ADMIN")
                    .requestMatchers("/tiposervicio/nuevo").hasRole("ADMIN")
                    .requestMatchers("/tiposervicio/editar/**").hasRole("ADMIN")
                    .requestMatchers("/tiposervicio/guardar").hasRole("ADMIN")
                    .requestMatchers("/tiposervicio/eliminar/**").hasRole("ADMIN")

                    // Tipos de equipo
                    .requestMatchers("/tipoequipo").hasRole("ADMIN")
                    .requestMatchers("/tipoequipo/nuevo").hasRole("ADMIN")
                    .requestMatchers("/tipoequipo/editar/**").hasRole("ADMIN")
                    .requestMatchers("/tipoequipo/guardar").hasRole("ADMIN")
                    .requestMatchers("/tipoequipo/eliminar/**").hasRole("ADMIN")

                    // ServicioPieza
                    .requestMatchers("/serviciopieza").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/serviciopieza/agregar").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/serviciopieza/eliminar").hasAnyRole("ADMIN", "TECNICO")

                    // Servicios
                    .requestMatchers("/servicio").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/nuevo").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/editar/**").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/guardar").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/eliminar/**").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/buscar").hasAnyRole("ADMIN", "TECNICO")
                    .requestMatchers("/servicio/servicios-sin-finalizar").hasAnyRole("ADMIN", "TECNICO")

                    // Roles
                    .requestMatchers("/rol").hasRole("ADMIN")
                    .requestMatchers("/rol/nuevo").hasRole("ADMIN")
                    .requestMatchers("/rol/editar/**").hasRole("ADMIN")
                    .requestMatchers("/rol/guardar").hasRole("ADMIN")
                    .requestMatchers("/rol/eliminar/**").hasRole("ADMIN")

                    // Piezas
                    .requestMatchers("/pieza").hasRole("ADMIN")
                    .requestMatchers("/pieza/nuevo").hasRole("ADMIN")
                    .requestMatchers("/pieza/editar/**").hasRole("ADMIN")
                    .requestMatchers("/pieza/guardar").hasRole("ADMIN")
                    .requestMatchers("/pieza/eliminar/**").hasRole("ADMIN")

                    // Facturas
                    .requestMatchers("/factura/lista").hasRole("ADMIN")
                    .requestMatchers("/factura/form-generar/**").hasRole("ADMIN")
                    .requestMatchers("/factura/generar").hasRole("ADMIN")
                    .requestMatchers("/factura/ver/**").hasRole("ADMIN")

                    // Estados de servicio
                    .requestMatchers("/estadoservicio").hasRole("ADMIN")
                    .requestMatchers("/estadoservicio/nuevo").hasRole("ADMIN")
                    .requestMatchers("/estadoservicio/editar/**").hasRole("ADMIN")
                    .requestMatchers("/estadoservicio/guardar").hasRole("ADMIN")
                    .requestMatchers("/estadoservicio/eliminar/**").hasRole("ADMIN")

                    // Clientes
                    .requestMatchers("/clientes").hasRole("ADMIN")
                    .requestMatchers("/clientes/guardar").hasRole("ADMIN")
                    .requestMatchers("/clientes/buscar").hasRole("ADMIN")
                        // aqui no se puede permitall a rutas delicadas xq podria entrar un usuario random con un rol ahi

                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/blog/**", "/marketing/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
