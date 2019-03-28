package net.demo.ignite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CachableService {
    @Autowired
    CacheManager cacheManager;

    /**
     * If cache with this name already exists in cluster or defined throw
     * the node initialization ("igniteCacheMagager" bean init) then it'll be used.
     * If there now cache with such name in a cluster, it will be created with defaults
     */
    private final static String CACHE_NAME = "demoCache";

    @CachePut(value = CACHE_NAME, key = "#root.args[0]")
    public String putValueIntoCache(Long id, String val) {
        return val;
    }

    public String getFromCache(Long id) {
        /*it could be also done with  @Cacheable annotation on putValueIntoCache method*/
        return getCache().map(cache -> (String)Optional.ofNullable(cache.get(id)).map(Cache.ValueWrapper::get).orElse(null)).orElse(null);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.args[0]")
    public void removeFromCache(Long id) {
        //do nothing
    }

    private Optional<Cache> getCache() {
        return Optional.ofNullable(cacheManager.getCache(CACHE_NAME));
    }

}
