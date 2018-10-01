package ee.cs.ut.wad2018.viinavaatlus;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable  cors() and csrf() for localhost development
        http.cors().and().csrf().disable();

        // By default allow requests to any URL
//        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests().anyRequest().authenticated().and().oauth2Login();
        http.authorizeRequests().antMatchers("/", "/auth/login/**").permitAll()
                .and()
                // Static files.
                .authorizeRequests().antMatchers("/favicon.ico", "/css/**", "/js/**", "/media/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .logout()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                    .loginPage("/auth/login")
                    .authorizationEndpoint().baseUri("/auth/login/oauth2");
    }
}
