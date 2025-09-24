package com.example.shardingjdbc.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mayn
 * @version 1.0
 * @className UserRoleComplexShardingAlgorithm
 * @description TODO 复杂分片算法
 * @date 2025/9/24 15:17
 **/
@Component
public class UserRoleComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        // userid 取余 rolecode 等值
        Map<String, Collection<Integer>> map = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        if (map.isEmpty()) {
            return collection;
        }
        Collection<String> availableTargetNames = new LinkedHashSet<>();
        Collection<Integer> userIds = map.get("user_id");
        Collection<Integer> roleCodes = map.get("role_code");
        if (userIds == null || roleCodes == null) {
            return collection;
        }
        for (Integer userId : userIds) {
            for (Integer roleCode : roleCodes) {
                availableTargetNames.add(complexKeysShardingValue.getLogicTableName() + "_" + (userId % 2) + "_" + roleCode);
            }
        }
        // 取符合条件的名称
        return availableTargetNames.stream().filter(collection::contains).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
