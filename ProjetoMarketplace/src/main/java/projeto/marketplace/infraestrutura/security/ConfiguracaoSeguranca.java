package projeto.marketplace.infraestrutura.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Mantém CSRF ativo nas telas web, mas libera para testes na API REST e H2 Console
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/h2-console/**")
                )
                // Permite que o H2 Console abra em frames no navegador
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers("/login", "/acesso-negado", "/css/**", "/js/**", "/h2-console/**").permitAll()
                        // Apenas ADMIN pode excluir registros (útil para testar a tela de acesso-negado com o usuário 'operador')
                        .requestMatchers("/*/excluir/**").hasRole("ADMIN")
                        // Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?erro=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acesso-negado")
                );

        return http.build();
    }
}