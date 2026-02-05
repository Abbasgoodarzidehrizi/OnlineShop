package net.abbas.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"net.abbas.*"})
@EntityScan(basePackages = {"net.abbas.dataaccess.entity"})
@EnableJpaRepositories(basePackages = {"net.abbas.dataaccess.repository"})
@EnableAspectJAutoProxy
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
