package com.EatAway;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ProjectConfig implements WebMvcConfigurer{
    
    //Estos metodos son para la implementación de la internalización
    
    //localResolver se utiliza para crear una sesión de cambio de idioma
    @Bean
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }
    
    //localResolver se utiliza para crear un interceptor de cambio de idioma
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    //Bean para poder acecder a los messages.properties en código Java
    @Bean("messageSource")
    public MessageSource messageSourse(){
        ResourceBundleMessageSource messageSourse = new ResourceBundleMessageSource();
        messageSourse.setBasenames("messages");
        messageSourse.setDefaultEncoding("UTF-8");
        return messageSourse;
    }
    
    /* Los siguiente métodos son para implementar el tema de seguridad dentro del proyecto */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void configurerGloal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
//    /* El siguiente método  no es realmente funcional porque no usa los usuarios de la BD */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("juan")
//                .password("{noop}123")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin);
//    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                .requestMatchers("/", "/index", "/errores/**","/error","/error/***",
                        "/error", "/error/***", "/js/**", "/webjars/**"
                ,"/css/**","/imagenes/**", "/registro/**")
                .permitAll()
                .requestMatchers(
                        "/detalle/**","/eventos", "/transporte", "/usuario/**",
                        "/enviar", "/reservar/**","/resenas/resena",
                        "/guardar",  "/reservas/**", "/reservas/eliminar/**",
                        "/resenas/**","/cuenta/**","/centroAyuda"
                ).authenticated()               
                )
                .formLogin((form) -> form
                .loginPage("/").permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
    
}