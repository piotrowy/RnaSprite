package pl.poznan.put.torsionanglesmatrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.poznan.put.session.SessionManager;
import pl.poznan.put.session.caches.GenericMatrixCacheManager;
import pl.poznan.put.session.caches.StructureCacheManager;

import java.util.Set;

@Configuration
public class TamSessionCache {

    @Bean
    public GenericMatrixCacheManager<TorsionAnglesMatrix, Set<String>> torsionAnglesMatrixSessionCache(Environment env,
                                                                                                       StructureCacheManager structureCacheManager,
                                                                                                       SessionManager sessionManager) {
        return new GenericMatrixCacheManager<>(env, structureCacheManager, sessionManager);
    }
}
