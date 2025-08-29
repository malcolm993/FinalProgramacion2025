/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.securityconfiguration;

import com.comunidadcineutn.cine.service.CostumerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author santi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CostumerUserDetailsService usuarioDetailService;

    @Bean

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(a -> a.disable())
                .userDetailsService(usuarioDetailService)
                .authorizeHttpRequests((aux) -> aux
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/icon/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/cineutn/inicio/todas").permitAll()
                .requestMatchers("/fragments/**").permitAll()
                .anyRequest().authenticated())
                .httpBasic(httpBasico -> {
                })
                .formLogin(form -> form
                .loginPage("/cineutn/usuario/login")
                .loginProcessingUrl("/logginprocess")
                .permitAll()
                );

        return http.build();

    }
    
    @Bean
    public PasswordEncoder encriptadorPassword (){
        return new BCryptPasswordEncoder();
    }
}
