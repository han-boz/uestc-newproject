package com.healthinnova.portal.common;

/**
 * 系统常量
 */
public interface Constants {

    /** 默认密码 */
    String DEFAULT_PASSWORD = "admin123";

    /** JWT Token 请求头 */
    String TOKEN_HEADER = "Authorization";

    /** JWT Token 前缀 */
    String TOKEN_PREFIX = "Bearer ";

    /** Redis Token 前缀 */
    String REDIS_TOKEN_PREFIX = "token:";

    /** 文件上传最大大小（图片） */
    long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB

    /** 文件上传最大大小（文档） */
    long MAX_DOC_SIZE = 20 * 1024 * 1024; // 20MB

    /** 允许上传的图片类型 */
    String[] ALLOWED_IMAGE_TYPES = {"jpg", "jpeg", "png", "gif"};

    /** 允许上传的文档类型 */
    String[] ALLOWED_DOC_TYPES = {"pdf", "doc", "docx", "xlsx"};

    /** 用户角色 */
    String ROLE_ADMIN = "admin";
    String ROLE_EDITOR = "editor";
    String ROLE_USER = "user";
}
