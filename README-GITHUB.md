# 智能药箱管家 - Android 应用

## 项目简介
这是一个智能药箱管理 Android 应用，支持药品管理、有效期提醒、统计图表等功能。

## 技术栈
- **开发语言**: Kotlin
- **UI 框架**: Android XML + Material Design
- **数据库**: Room (SQLite)
- **图表库**: MPAndroidChart

## 功能特性
### P0 - 核心功能 ✅
1. **手动录入**: 支持输入药品名称、规格、主治分类、有效期、备注
2. **药品库存列表**: 按有效期排序，显示剩余天数并用颜色标识
3. **数据持久化**: 使用 Room 数据库存储，应用关闭后数据不丢失

### P1 - 增强功能 ✅
1. **分类管理**: 支持感冒、消化、慢性病等分类
2. **统计图表**: 饼图展示药品分类占比
3. **有效期状态显示**: 实时计算剩余天数，红/橙/绿颜色标识

## GitHub Actions 构建说明

本项目配置了 GitHub Actions 自动构建流程：

### 触发条件
- 推送到 master 分支
- 创建 Pull Request
- 创建标签 (tag)

### 构建流程
1. 检出代码
2. 设置 JDK 17
3. 配置 Android SDK
4. 执行 `./gradlew assembleRelease`
5. 上传 APK 作为构建产物

### 获取 APK
构建完成后，可以在 GitHub Actions 页面下载：
1. 进入仓库的 Actions 标签页
2. 选择最新的构建任务
3. 在 Artifacts 部分下载 `medicine-cabinet-apk`

## 本地构建（需要 Android 环境）

### 前提条件
- Android Studio 或 Android Command Line Tools
- JDK 17

### 构建步骤
```bash
# 1. 进入项目目录
cd medicine-cabinet

# 2. 赋予 gradlew 执行权限
chmod +x gradlew

# 3. 构建 Release APK
./gradlew assembleRelease

# 4. 获取 APK
# APK 位置: app/build/outputs/apk/release/app-release.apk
```

## 项目结构
```
medicine-cabinet/
├── .github/workflows/          # GitHub Actions 配置
├── app/
│   ├── src/main/
│   │   ├── java/com/example/medicinecabinet/
│   │   │   ├── MainActivity.kt
│   │   │   ├── AddMedicineActivity.kt
│   │   │   ├── MedicineDetailActivity.kt
│   │   │   ├── StatisticsActivity.kt
│   │   │   ├── MedicineAdapter.kt
│   │   │   └── db/
│   │   └── res/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 提交到多维表格
1. **源代码**: `archive.zip` (已生成)
2. **APK**: 通过 GitHub Actions 自动生成
3. **说明**: 本 README 文档

## 注意事项
- 需要 Android 7.0 (API 24) 及以上版本
- 首次构建可能需要下载依赖，耗时较长
- GitHub Actions 每月提供 2000 分钟免费构建时间

---
**项目完成时间**: 2026-04-09
**技术栈**: Android (Kotlin + Room + MPAndroidChart)
