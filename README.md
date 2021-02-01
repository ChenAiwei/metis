# metis
##### 文件上传下载服务器
**使用方法：**

1. 启动metsi-server
2. 第三方项目依赖metis-common
3. 配置yml属性
4. 调用相应的API接口

**==注==：**
    项目中必须配置的yml属性示例如下

```
metis:
  host: http://127.0.0.1:8890
  accessKey: QdtxCxaz0M6uWZ48s-UGq_hKbhUuXIEKV0c4b_db
  secretKey: 2ZQtoKP1soQ9MWnAxwTdxm-3mv-EqgzqDcYuxBOC
  bucket: metis
  downloadUrl: /file/download
  uploadUrl: /file/upload
```

在项目中注入

```
    @Bean
	public MetisHelper metisHelper(){
		return new MetisHelper();
	}
```

目前开放的API接口(++==参数缺省展示,可配置路径和过期时间==++)：


```
//文件上传
metisHelper.uploadImage()

//生成下载URL
metisHelper.getDownloadToken()
```

###### API接口约束规范：
***code：*** -1 失败 0 成功  
***content：*** 响应内容  
***timeStamp：*** 当前时间戳


---

### 项目后续规划
- [ ] API接口更多开放
- [ ] common依赖去除spring容器
- [ ] metis管控台开发
- [ ] 持久层接入
- [ ] 多租户接入场景动态分配存储空间
- [ ] 日志平台接入
- [ ] ...