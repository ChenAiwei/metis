package com.boot.metis.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Particle extends Model<Particle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 节点ID
     */
    @TableField("node_id")
    private Long nodeId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 生成时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 0 未删除 1已删除
     */
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
