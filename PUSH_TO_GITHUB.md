# 推送代码到 GitHub 指南

## 仓库信息
- **仓库地址**: https://github.com/lymioumm/medicine-cabinet.git
- **仓库名称**: medicine-cabinet

## 方法一：使用 GitHub CLI（推荐）

### 1. 安装 GitHub CLI
```bash
# Ubuntu/Debian
sudo apt-get install gh

# 或下载最新版本
wget https://github.com/cli/cli/releases/download/v2.30.0/gh_2.30.0_linux_amd64.tar.gz
tar -xzf gh_2.30.0_linux_amd64.tar.gz
sudo cp gh_2.30.0_linux_amd64/bin/gh /usr/local/bin/
```

### 2. 登录 GitHub
```bash
gh auth login
# 选择 https://github.com
# 选择 HTTPS
# 选择 Yes 登录
# 按提示在浏览器中授权
```

### 3. 推送代码
```bash
cd /tmp/medicine-cabinet
gh repo view lymloumm/medicine-cabinet --json name --jq '.name'
git remote add origin https://github.com/lymioumm/medicine-cabinet.git
git push -u origin master
```

## 方法二：使用 Personal Access Token

### 1. 创建 Personal Access Token
1. 访问 https://github.com/settings/tokens
2. 点击 "Generate new token"
3. 勾选权限：
   - `repo` - 完整仓库访问
   - `workflow` - GitHub Actions 访问
4. 生成并复制 token

### 2. 使用 Token 推送
```bash
cd /tmp/medicine-cabinet
git remote add origin https://YOUR_TOKEN@github.com/lymioumm/medicine-cabinet.git
git push -u origin master
```

**注意**: 将 `YOUR_TOKEN` 替换为实际的 token

## 方法三：手动上传（最简单）

### 1. 下载项目文件
```bash
# 复制 archive.zip 到你的电脑
cp /tmp/medicine-cabinet/archive.zip ~/Downloads/
```

### 2. 在 GitHub 网页上传
1. 访问 https://github.com/lymioumm/medicine-cabinet
2. 点击 "Add file" → "Upload files"
3. 选择 `archive.zip` 上传
4. 提交更改

### 3. 解压并提交
1. 在仓库页面点击 "Code" → "Download ZIP"
2. 解压到本地
3. 添加 `.github/workflows/android-build.yml`
4. 提交并推送

## 推送后的操作

### 1. 触发 GitHub Actions
推送代码后，GitHub Actions 会自动触发构建：

1. 访问 https://github.com/lymioumm/medicine-cabinet
2. 点击 "Actions" 标签页
3. 看到 "Android CI" 工作流程正在运行

### 2. 等待构建完成
- 首次构建可能需要 5-10 分钟
- 需要下载 Android SDK 和依赖

### 3. 下载 APK
构建完成后：

1. 在 Actions 页面点击最新的构建任务
2. 在页面底部找到 "Artifacts" 部分
3. 下载 `medicine-cabinet-apk`
4. 解压得到 `app-release.apk`

## 验证推送成功

### 检查仓库内容
访问 https://github.com/lymioumm/medicine-cabinet 应该看到：
- `app/` 目录
- `.github/workflows/android-build.yml`
- `README-GITHUB.md`
- `GITHUB_SETUPGuide.md`

### 检查 Actions
访问 https://github.com/lymioumm/medicine-cabinet/actions
应该看到 Android CI 工作流程。

## 常见问题

### Q: 推送时提示 "Authentication failed"
A: 确保使用正确的认证方式（Token 或 SSH key）

### Q: GitHub Actions 构建失败
A: 查看构建日志，常见原因：
- 依赖下载失败
- 代码语法错误
- SDK 配置问题

### Q: 找不到 APK 下载链接
A: 确保构建成功完成，Artifacts 只在成功时生成

## 下一步

1. **完成推送** - 选择上述任一方法
2. **等待构建** - 大约 5-10 分钟
3. **下载 APK** - 从 Actions 页面获取
4. **提交到多维表格** - 包含源代码和 APK

---
**如果推送遇到问题，请告诉我具体错误信息，我可以帮你解决！**
