# 智能药箱管家

## 项目简介
智能药箱管家是一款 Android 应用，帮助用户管理家庭药品库存，包括药品录入、库存管理、有效期提醒等功能。

## 功能特性

### P0 - 核心功能
- ✅ 手动录入药品信息（名称、规格、分类、有效期、备注）
- ✅ 药品库存列表（按有效期排序，临近过期在最前）
- ✅ 数据持久化（使用 Room 数据库）

### P1 - 增强功能
- ✅ 分类管理（感冒、消化、慢性病、外用药、维生素、其他）
- ✅ 统计图表（药品分类占比饼图）
- ✅ 有效期状态显示（剩余天数、即将过期提示）

### P2 - 可选功能
- ⏳ 拍照识别（需集成 AI 识别服务）
- ⏳ 识别纠错
- ⏳ 有效期提醒推送

## 技术栈
- **语言**: Kotlin
- **UI**: Android XML + Material Design
- **数据库**: Room Database
- **图表**: MPAndroidChart
- **图片加载**: Picasso

## 项目结构
```
app/
├── src/main/
│   ├── java/com/example/medicinecabinet/
│   │   ├── MainActivity.kt           # 主界面
│   │   ├── AddMedicineActivity.kt    # 添加药品
│   │   ├── MedicineDetailActivity.kt # 药品详情
│   │   ├── StatisticsActivity.kt     # 统计图表
│   │   ├── MedicineAdapter.kt        # RecyclerView 适配器
│   │   └── db/
│   │       ├── Medicine.kt           # 数据实体
│   │       ├── MedicineDao.kt        # 数据访问对象
│   │       └── AppDatabase.kt        # 数据库
│   └── res/
│       ├── layout/                   # 布局文件
│       ├── values/                   # 资源文件
│       └── menu/                     # 菜单文件
├── build.gradle
└── AndroidManifest.xml
```

## 构建 APK
```bash
cd /tmp/medicine-cabinet
./gradlew assembleRelease
```

生成的 APK 位于：
`app/build/outputs/apk/release/app-release.apk`

## 使用说明
1. 启动应用后，点击右下角 "+" 按钮添加药品
2. 输入药品名称、规格、选择分类和有效期
3. 药品将显示在列表中，按有效期排序
4. 点击药品可查看详情或删除
5. 点击菜单可查看统计图表

## 注意事项
- 本应用为练习项目，未集成拍照识别功能
- 数据存储在本地 SQLite 数据库
- 需要 Android 7.0 (API 24) 及以上版本

## 交付物
- ✅ 可安装运行的 Android APK
- ✅ 完整源代码
- ✅ README 文档
# Trigger build
