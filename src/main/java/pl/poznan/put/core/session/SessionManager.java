package pl.poznan.put.core.session;

import lombok.RequiredArgsConstructor;
import pl.poznan.put.core.session.caches.EmptyCacheException;
import pl.poznan.put.core.session.caches.StructureCacheManager;
import pl.poznan.put.core.structure.PdbStructure;
import pl.poznan.put.tables.daos.PdbIdSessionIdDao;
import pl.poznan.put.tables.pojos.PdbIdSessionId;
import pl.poznan.put.util.exceptions.InvalidArgumentException;

import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SessionManager {

    private final PdbIdSessionIdDao pdbIdSessionIdDao;
    private final StructureCacheManager structureCacheManager;

    boolean sessionExists(UUID sessionId) {
        return pdbIdSessionIdDao.fetchOneBySessionId(sessionId) != null;
    }

    UUID getSession(final String pdbId) throws InvalidArgumentException {
        UUID sessionId = UUID.randomUUID();
        structureCacheManager.createStructureRecord(pdbId);
        pdbIdSessionIdDao.insert(new PdbIdSessionId(null, pdbId, sessionId));
        return sessionId;
    }

    public PdbStructure getStructureCache(UUID sessionId) throws EmptyCacheException {
        return structureCacheManager.getStructure(pdbIdSessionIdDao.fetchOneBySessionId(sessionId).getPdbId());
    }

    public boolean sessionExistForStructure(String pdbId) {
        return pdbIdSessionIdDao.fetchByPdbId(pdbId).size() > 0;
    }
}
