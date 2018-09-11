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
        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}
