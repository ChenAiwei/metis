package com.boot.metis.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author auto
 * @since 2021-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("model_name")
    private String modelName;

    @TableField("class_name")
    private String className;

    @TableField("method_name")
    private String methodName;

    @TableField("request_url")
    private String requestUrl;

    @TableField("request_params")
    private String requestParams;

    @TableField("response_body")
    private String responseBody;

    /**
     * 操作类型 0：删除，1：查询，2：添加，3：修改
     */
    @TableField("opt_type")
    private Integer optType;

    /**
     * 操作结果 0:异常失败 ，1：正常
     */
    @TableField("opt_status")
    private Integer optStatus;

    @TableField("time_consu")
    private Long timeConsu;

    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
