package com.example.shardingjdbc.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author mayn
 * @version 1.0
 * @className MonthTableShardingAlgorithm
 * @description TODO 按月分表算法
 * @date 2025/9/24 14:00
 **/
@Component
public class MonthTableShardingAlgorithm implements StandardShardingAlgorithm<LocalDateTime> {

    /**
     * 日期格式化，yyyyMM
     * **/
    static final DateTimeFormatter YYYY_M_M = DateTimeFormatter.ofPattern("yyyyMM");
    static final DateTimeFormatter YYYY_M_D = DateTimeFormatter.ofPattern("yyyyMMdd");



    /**
     * @author mayn
     * @description //TODO 等值查询，确定表
     * @date 2025/9/24 14:13
     * @param availableTargetNames 可用的表名
     * @param shardingValue 当前的分片值
     * @return java.lang.String
     **/
    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<LocalDateTime> shardingValue) {
        LocalDateTime value = shardingValue.getValue();
        String afterName = value.format(YYYY_M_M);
        for (String each : availableTargetNames) {
           if (each.endsWith(afterName)) {
               return each;
           }
        }
        // 找不到表, 返回null
        return null;
    }

    /**
     * @author mayn
     * @description //TODO 范围分片-只到月。日的全部忽略
     * @date 2025/9/24 14:17
     * @param availableTargetNames 可用的表明
     * @param shardingValue 范围分片值
     * @return java.util.Collection<java.lang.String>
     **/
    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final RangeShardingValue<LocalDateTime> shardingValue) {
        var range = shardingValue.getValueRange();

        // 如果只给了下界或上界，可选择全表扫描或自定义逻辑
        if (!range.hasLowerBound() || !range.hasUpperBound()) {
            return availableTargetNames;
        }
        // 如果开始时间大于结束时间，返回null
        if (shardingValue.getValueRange().lowerEndpoint().isAfter(shardingValue.getValueRange().upperEndpoint()))  {
            return null;
        }
        return confirmTargetNames(availableTargetNames, shardingValue);
    }

    /**
     * @author mayn
     * @description //TODO 确定表
     * @date 2025/9/24 14:43
     * @param availableTargetNames 可用的表名
     * @param shardingValue 分片值
     * @return java.util.Collection<java.lang.String>
     **/
    private static Collection<String> confirmTargetNames(Collection<String> availableTargetNames, RangeShardingValue<LocalDateTime> shardingValue) {
        LocalDate startValue = shardingValue.getValueRange().lowerEndpoint().toLocalDate().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endValue = shardingValue.getValueRange().upperEndpoint().toLocalDate().with(TemporalAdjusters.lastDayOfMonth());
        // 遍历所有可能的表
        Collection<String> result = new LinkedList<>();
        // 为了避免不必要的查表，只遍历可能的月份
        for (String name : availableTargetNames) {
            // 2024-01-01
            if (name.length() < 6) {
                continue;
            }
            String date = name.substring(name.length() - 6) +  "01";
            // 截取月份
            int month = Integer.parseInt(date.substring(4, 6));

            // 月份不合法
            if (month < 1 || month > 12) {
                continue;
            }
            LocalDate value = LocalDate.parse(date, YYYY_M_D);
            if ((value.isAfter(startValue) || value.isEqual(startValue)) && (value.isBefore(endValue) || value.isEqual(endValue))) {
                result.add(name);
            }
        }
        return result;
    }
}