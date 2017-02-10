package pl.poznan.put.repr.distancematrix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.poznan.put.core.session.SessionManager;
import pl.poznan.put.core.session.caches.GenericMatrixCacheManager;
import pl.poznan.put.core.session.caches.StructureCacheManager;

@Configuration
public class DmSessionCache {

    @Bean
    public GenericMatrixCacheManager<DistanceMatrix, ResiduesAtomsSpecification> getSessionCache(Environment env,
                                                                                                 StructureCacheManager structureCacheManager,
                                                                                                 SessionManager sessionManager) {
        return new GenericMatrixCacheManager<>(env, structureCacheManager, sessionManager);
    }
}
