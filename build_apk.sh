#!/bin/bash
echo "开始构建智能药箱管家 APK..."
echo "项目目录: $(pwd)"

# 检查 Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "错误: ANDROID_HOME 环境变量未设置"
    if [ -f local.properties ]; then
        echo "使用 local.properties 中的配置"
    fi
fi

# 设置权限
chmod +x gradlew 2>/dev/null || echo "gradlew 不存在，跳过"

echo "项目文件结构:"
find . -type f -name "*.kt" -o -name "*.xml" | wc -l
echo "个文件"

echo "尝试构建..."
# 这里由于环境限制，实际构建需要在有 Android SDK 的环境中进行
echo "注意: 完整构建需要在安装 Android SDK 的环境中进行"
echo "项目已准备好，可复制到 Android Studio 中构建"

# 打包源代码
echo "打包源代码..."
git archive -o archive.zip HEAD 2>/dev/null || zip -r archive.zip . -x "*.git*" -x "build/*" -x ".gradle/*"
echo "源代码包已创建: archive.zip"
ls -lh archive.zip
