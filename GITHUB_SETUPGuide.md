# GitHub Actions 自动构建 APK 指南

## 概述
本指南说明如何使用 GitHub Actions 自动构建 Android APK，无需本地安装 Android Studio。

## 步骤 1：创建 GitHub 仓库

### 方法 A：通过网页创建
1. 访问 https://github.com/new
2. 输入仓库名称：`medicine-cabinet`
3. 选择公开或私有
4. 点击 "Create repository"

### 方法 B：通过命令行（如果已有 GitHub 账号）
```bash
# 需要先安装 GitHub CLI (gh)
gh auth login
gh repo create medicine-cabinet --public --source=. --remote=origin
```

## 步骤 2：推送代码到 GitHub

```bash
# 进入项目目录
cd /tmp/medicine-cabinet

# 添加 GitHub 远程仓库（替换 YOUR_USERNAME）
git remote add origin https://github.com/YOUR_USERNAME/medicine-cabinet.git

# 推送代码
git branch -M master
git push -u origin master
```

## 步骤 3：触发自动构建

推送代码后，GitHub Actions 会自动触发构建：

1. 访问你的仓库页面
2. 点击 "Actions" 标签页
3. 看到 "Android CI" 工作流程正在运行

## 步骤 4：获取 APK 文件

构建完成后（通常需要 5-10 分钟）：

1. 在 Actions 页面点击最新的构建任务
2. 在页面底部找到 "Artifacts" 部分
3. 下载 `medicine-cabinet-apk` 文件
4. 解压后得到 `app-release.apk`

## 构建状态说明

### ✅ 构建成功
- 绿色对勾图标
- APK 可下载

### ❌ 构建失败
- 红色叉号图标
- 点击查看详细错误信息
- 常见原因：
  - 依赖下载失败
  - 代码错误
  - 配置问题

## 手动触发构建

如果需要重新构建：

1. 进入 Actions 标签页
2. 点击 "Android CI" workflow
3. 点击 "Run workflow" 按钮
4. 选择分支（通常是 master）
5. 点击 "Run workflow"

## 获取构建的 APK

### 方法 1：通过 Artifacts（推荐）
- 构建完成后直接下载 APK
- 保留 90 天

### 方法 2：创建 Release
- 打标签时自动创建 Release
- APK 附加在 Release 中
- 永久保存

```bash
# 创建标签并推送
git tag v1.0.0
git push origin v1.0.0
```

## 项目文件说明

### 必要文件
- `.github/workflows/android-build.yml` - GitHub Actions 配置
- `app/build.gradle` - 应用构建配置
- `gradlew` - Gradle 包装器

### 构建产物
- `app/build/outputs/apk/release/app-release.apk` - Release APK

## 故障排除

### 构建失败常见原因

1. **依赖下载失败**
   - 检查网络连接
   - 重试构建

2. **代码错误**
   - 检查 Kotlin 语法
   - 查看构建日志

3. **SDK 配置问题**
   - 检查 build.gradle 中的 SDK 版本
   - 确保与 Android API 级别兼容

### 查看构建日志
1. 进入 Actions 页面
2. 点击失败的构建任务
3. 查看详细日志

## 免费额度说明

GitHub Actions 每月提供：
- **公开仓库**: 2000 分钟免费构建时间
- **私有仓库**: 2000 分钟（免费账号）

对于 Android 项目，单次构建通常需要 5-10 分钟。

## 下一步

1. **测试 APK**: 将 APK 传输到 Android 设备安装测试
2. **持续集成**: 配置更多测试和质量检查
3. **发布到应用商店**: 准备发布材料

## 获取帮助

如果遇到问题：
1. 查看 GitHub Actions 日志
2. 检查 Android 项目配置
3. 参考 GitHub Actions 文档

---
**项目完成时间**: 2026-04-09
