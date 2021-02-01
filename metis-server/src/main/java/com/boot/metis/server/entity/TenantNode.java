package com.boot.metis.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author auto
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tenant_node")
public class TenantNode extends Model<TenantNode> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 节点名称
     */
    @TableField("node_name")
    private String nodeName;

    /**
     * 父节点ID
     */
    private Long pid;

    /**
     * 租户ID
     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 0 未删除 1已删除
     */
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
