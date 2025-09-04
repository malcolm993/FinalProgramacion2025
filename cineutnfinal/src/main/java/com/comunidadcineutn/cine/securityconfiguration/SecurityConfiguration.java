/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.securityconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author santi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private CustomUserDetailsService usuarioDetailService; // ✅ Nombre corregido
    
    @Bean
    public static PasswordEncoder encriptadorPassword() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(a -> a.disable())
            // ❌ ELIMINAR esta línea: .userDetailsService(usuarioDetailService)
            .authorizeHttpRequests((aux) -> aux
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/icon/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/cineutn/inicio/todas").permitAll()
                .requestMatchers("/cineutn/usuario/signup").permitAll()
                .requestMatchers("/cineutn/usuario/login").permitAll() // ✅ AÑADIR
                .requestMatchers("/fragments/**").permitAll()
                .requestMatchers("/error").permitAll() // ✅ AÑADIR para páginas de error
                .requestMatchers("/public/**").permitAll() // ✅ OPCIONAL: para contenido público
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/cineutn/usuario/login")
                .loginProcessingUrl("/loginprocess")
                .defaultSuccessUrl("/cineutn/inicio/todas")
                .failureUrl("/cineutn/usuario/login?error=true")
                .permitAll()
            )
            .logout(deslogueo -> deslogueo
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
            );

        return http.build();
    }
    
    // ✅ CONFIGURACIÓN CORRECTA del AuthenticationManager
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailService)
            .passwordEncoder(encriptadorPassword());
    }
}