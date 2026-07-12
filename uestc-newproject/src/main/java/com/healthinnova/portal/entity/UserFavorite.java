package com.healthinnova.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_favorite")
public class UserFavorite implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String type;
    private Long targetId;
    private String targetTitle;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
