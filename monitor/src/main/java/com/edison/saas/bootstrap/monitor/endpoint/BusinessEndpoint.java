package com.edison.saas.bootstrap.monitor.endpoint;

import com.edison.saas.bootstrap.monitor.statistics.BusinessSeq;
import com.edison.saas.bootstrap.monitor.statistics.BusinessStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;

/**
 * 业务量统计
 */
@ConfigurationProperties(prefix = "endpoints.business")
public class BusinessEndpoint extends AbstractEndpoint<Collection<BusinessSeq>> {

    @Autowired
    private BusinessStatisticsService businessStatisticsService;

    public BusinessEndpoint() {
        super("business", false);
    }

    @Override
    public Collection<BusinessSeq> invoke() {
        return businessStatisticsService.get();
    }
}
