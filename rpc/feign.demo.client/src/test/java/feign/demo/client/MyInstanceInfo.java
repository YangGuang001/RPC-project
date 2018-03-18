package feign.demo.client;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;

import java.util.HashMap;

/**
 * Created by yz on 2018/3/18.
 */
public class MyInstanceInfo extends InstanceInfo {
    public MyInstanceInfo(DataCenterInfo dataCenterInfo, LeaseInfo leaseInfo) {
        this("microservice-provider-user", "microservice-provider-user", "default", "127.0.0.1:8030",
                null, null, null, null, null, null,null, null, null,0, dataCenterInfo, null,null, null, leaseInfo,null, null, null,null, null, null);
    }

    public MyInstanceInfo(String instanceId, String appName, String appGroupName, String ipAddr, String sid, PortWrapper port, PortWrapper securePort, String homePageUrl, String statusPageUrl, String healthCheckUrl, String secureHealthCheckUrl, String vipAddress, String secureVipAddress, int countryId, DataCenterInfo dataCenterInfo, String hostName, InstanceStatus status, InstanceStatus overriddenstatus, LeaseInfo leaseInfo, Boolean isCoordinatingDiscoveryServer, HashMap<String, String> metadata, Long lastUpdatedTimestamp, Long lastDirtyTimestamp, ActionType actionType, String asgName) {
        super(instanceId, appName, appGroupName, ipAddr, sid, port, securePort, homePageUrl, statusPageUrl, healthCheckUrl, secureHealthCheckUrl, vipAddress, secureVipAddress, countryId, dataCenterInfo, hostName, status, overriddenstatus, leaseInfo, isCoordinatingDiscoveryServer, metadata, lastUpdatedTimestamp, lastDirtyTimestamp, actionType, asgName);
    }
}
