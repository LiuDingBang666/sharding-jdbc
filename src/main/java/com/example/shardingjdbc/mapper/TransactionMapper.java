package com.example.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shardingjdbc.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易记录Mapper接口
 * 支持按月分表查询
 */
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

    /**
     * 根据用户ID查询交易记录
     * @param userId 用户ID
     * @return 交易记录列表
     */
    @Select("SELECT * FROM t_transaction WHERE user_id = #{userId} AND deleted = 0 ORDER BY transaction_time DESC")
    List<Transaction> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据交易流水号查询交易记录
     * @param transactionNo 交易流水号
     * @return 交易记录
     */
    @Select("SELECT * FROM t_transaction WHERE transaction_no = #{transactionNo} AND deleted = 0")
    Transaction selectByTransactionNo(@Param("transactionNo") String transactionNo);

    /**
     * 根据订单ID查询交易记录
     * @param orderId 订单ID
     * @return 交易记录列表
     */
    @Select("SELECT * FROM t_transaction WHERE order_id = #{orderId} AND deleted = 0 ORDER BY transaction_time DESC")
    List<Transaction> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据用户ID和时间范围查询交易记录
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易记录列表
     */
    @Select("SELECT * FROM t_transaction WHERE user_id = #{userId} AND transaction_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 ORDER BY transaction_time DESC")
    List<Transaction> selectByUserIdAndTimeRange(@Param("userId") Long userId,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    /**
     * 根据交易类型统计交易数量
     * @param transactionType 交易类型
     * @return 交易数量
     */
    @Select("SELECT COUNT(*) FROM t_transaction WHERE transaction_type = #{transactionType} AND deleted = 0")
    Integer countByTransactionType(@Param("transactionType") Integer transactionType);

    /**
     * 根据用户ID和交易类型查询交易记录
     * @param userId 用户ID
     * @param transactionType 交易类型
     * @return 交易记录列表
     */
    @Select("SELECT * FROM t_transaction WHERE user_id = #{userId} AND transaction_type = #{transactionType} AND deleted = 0 ORDER BY transaction_time DESC")
    List<Transaction> selectByUserIdAndTransactionType(@Param("userId") Long userId, @Param("transactionType") Integer transactionType);

    /**
     * 计算用户在指定时间范围内的交易总金额
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param transactionType 交易类型
     * @return 交易总金额
     */
    @Select("SELECT COALESCE(SUM(amount), 0) FROM t_transaction WHERE user_id = #{userId} AND transaction_time BETWEEN #{startTime} AND #{endTime} AND transaction_type = #{transactionType} AND status = 3 AND deleted = 0")
    BigDecimal sumAmountByUserIdAndTimeRangeAndType(@Param("userId") Long userId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime,
                                                  @Param("transactionType") Integer transactionType);

    /**
     * 根据支付方式统计交易金额
     * @param paymentMethod 支付方式
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易总金额
     */
    @Select("SELECT COALESCE(SUM(amount), 0) FROM t_transaction WHERE payment_method = #{paymentMethod} AND transaction_time BETWEEN #{startTime} AND #{endTime} AND status = 3 AND deleted = 0")
    BigDecimal sumAmountByPaymentMethodAndTimeRange(@Param("paymentMethod") Integer paymentMethod,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 查询用户最近的交易记录
     * @param userId 用户ID
     * @param limit 限制条数
     * @return 交易记录列表
     */
    @Select("SELECT * FROM t_transaction WHERE user_id = #{userId} AND deleted = 0 ORDER BY transaction_time DESC LIMIT #{limit}")
    List<Transaction> selectRecentTransactionsByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
}