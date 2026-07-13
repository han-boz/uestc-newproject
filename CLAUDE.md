# 健康大数据门户系统 — 项目概要

## 项目结构

```
D:/projects/
├── uestc-newproject/              # 后端 Spring Boot 3.5 + MyBatis-Plus
│   └── uestc-newproject/          # Maven 模块
│       ├── src/main/java/.../portal/
│       │   ├── controller/        # REST 控制器
│       │   ├── service/impl/      # 业务逻辑
│       │   ├── mapper/            # MyBatis-Plus Mapper
│       │   ├── entity/            # 数据实体
│       │   ├── dto/               # 请求/响应 DTO
│       │   ├── security/          # JWT 认证
│       │   ├── config/            # 配置类（含 AOP 日志）
│       │   └── common/            # 通用工具 + 异常 + 枚举
│       └── src/main/resources/
│           ├── application.yml    # 主配置（MySQL 默认）
│           ├── application-mysql.yml  # MySQL 模式
│           └── application-local.yml  # H2 零配置模式
│
├── health/uestc-newproject-frontend/  # 前端 Vue 3 + Vite
│   └── src/
│       ├── views/                 # 9 个页面组件
│       ├── components/            # 6 个公共组件
│       ├── router/index.js        # 路由 + 鉴权守卫
│       └── utils/api.js           # API 请求工具
│
└── health/uestc-newproject-frontend/项目修改记录.md  # 完整改动记录
```

## 配置模式

| 模式 | 命令 | 依赖 |
|------|------|------|
| MySQL 默认 | `./mvnw spring-boot:run` | MySQL + Redis |
| H2 备用 | 加 `--spring-boot.run.profiles=local` | 无 |

MySQL 密码通过环境变量覆盖：`MYSQL_PASSWORD`, `REDIS_PASSWORD`

## 已完成的优化（2026-07-13）

### 性能
- JWT 解析 3次→1次
- 新闻浏览量增量更新
- 知识库分类 N+1 查询消除（1+2N → 1 次 SQL）
- FastJSON → Jackson

### Bug 修复
- 管理员改他人密码不再需要旧密码
- 首页公告不跳转新闻详情
- 知识库"阅读全文"可展开正文
- CORS 配置修复

### 功能新增
- 退出登录 `/auth/logout` 接口
- AOP 自动记录操作日志
- 前端路由鉴权守卫
- 公告/知识库文章详情接口

### 代码质量
- ServiceException message 遮蔽修复
- Redis 注入优化（ObjectProvider）
- @Transactional 补全
- CORS 过滤器优先级修正
- 密码后端长度校验 @Size(min=6)

## 测试账号
- 管理员：admin / admin123
- 普通用户：testuser / test1234

## 关键配置
- 后端端口：8888
- 前端端口：3000（Vite 代理→8888）
- JWT 过期：24 小时
- Redis key 前缀：`token:`
- 文件上传路径：`uploads/`
