package pl.poznan.put.core.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.tables.daos.PdbIdSessionIdDao;

@Configuration
public class PdbIdSessionIdConfiguration {

    @Bean
    public PdbIdSessionIdDao getPdbIdSessionIdDao(org.jooq.Configuration defaultConfiguration) {
        return new PdbIdSessionIdDao(defaultConfiguration);
    }
}
