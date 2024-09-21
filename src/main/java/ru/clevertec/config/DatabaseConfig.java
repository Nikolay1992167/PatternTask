package ru.clevertec.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.clevertec.exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan("ru.clevertec")
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class DatabaseConfig {

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.poolSize}")
    private int poolSize;

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(poolSize);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Connection getConnection() {
        try {
            return hikariDataSource().getConnection();
        } catch (SQLException e) {
            throw new JDBCConnectionException(e.getMessage());
        }
    }
}