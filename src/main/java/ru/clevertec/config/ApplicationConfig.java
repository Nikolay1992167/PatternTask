package ru.clevertec.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import ru.clevertec.dao.CarDAO;
import ru.clevertec.mapper.CarMapper;
import ru.clevertec.servicecrud.CarService;
import ru.clevertec.servicecrud.impl.CarServiceImpl;
import ru.clevertec.servicecrud.impl.ProxyCarService;

import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("ru.clevertec")
public class ApplicationConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties yamlObject = Objects.requireNonNull(yaml.getObject(), "Yaml not found.");
        configure.setProperties(yamlObject);
        return configure;
    }

    @Bean
    public CarService carService(CarMapper carMapper, CarDAO carDAO) {
        CarServiceImpl carServiceImpl = new CarServiceImpl(carMapper, carDAO);
        return new ProxyCarService(carServiceImpl);
    }
}