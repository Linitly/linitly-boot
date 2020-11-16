## Linitly-Boot文档




### Swagger导出离线文档
执行`mvn swagger2markup:convertSwagger2markup`命令可生成adoc文件

执行`mvn generate-resources`命令可生成对应html和pdf文件
#### PDF文件导出空白、乱码问题解决
1. 找到maven仓库中`asciidoctorj-pdf-1.5.0-alpha.10.1.jar`文件
2. 使用压缩工具打开jar包，进入`gems\asciidoctor-pdf-1.5.0.alpha.10\data\`目录
3. 将`tools/asciidoctorj-pdf-replace-file`文件夹下的四个`ttf`文件复制到`fonts`文件夹下
4. 将`tools/asciidoctorj-pdf-replace-file`文件夹下的`default-theme.yml`文件复制替换到`themes`目录下
5. 重新执行上诉命令生成即可