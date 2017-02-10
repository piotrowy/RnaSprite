package pl.poznan.put.core.session.pdbid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poznan.put.tables.daos.PdbIdsDao;

@Configuration
public class PdbIdsConfiguration {

    @Bean
    public PdbIdsDao pdbIdsDao(org.jooq.Configuration defaultConfiguration) {
        return new PdbIdsDao(defaultConfiguration);
    }
}
