package core.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration(exclude = { ThymeleafAutoConfiguration.class })
@EntityScan("org.nhnnext.domain")
@EnableJpaRepositories("org.nhnnext.domain")
@EnableTransactionManagement
public class RepositoryConfiguration {

}
