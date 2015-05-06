package org.infinispan.demo.carmart.session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.infinispan.demo.carmart.session.CarManager;
import org.infinispan.demo.carmart.session.StatisticsProvider;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.stats.Stats;


@Named("stats")
@RequestScoped
public class LocalStatisticsProvider implements StatisticsProvider {

    @Inject
    private CacheContainerProvider provider;

    private Stats stats;

    @PostConstruct
    public void getStatsObject() {
        stats = ((DefaultCacheManager) provider.getCacheContainer()).getCache(CarManager.CACHE_NAME).getAdvancedCache()
                .getStats();
    }

    public String getRetrievals() {
        return String.valueOf(stats.getRetrievals());
    }

    public String getStores() {
        return String.valueOf(stats.getStores());
    }

    public String getCurrentEntries() {
        return String.valueOf(stats.getCurrentNumberOfEntries());
    }

    public String getHits() {
        return String.valueOf(stats.getHits());
    }

    public String getMisses() {
        return String.valueOf(stats.getMisses());
    }

    public String getRemoveHits() {
        return String.valueOf(stats.getRemoveMisses());
    }
}
