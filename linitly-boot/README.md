## linitly-boot文档

当前最新版本： `1.0.1`

### 系统架构

后端不采用父子项目组合方式，只构建一个项目，使用不同包来区分系统基础功能和业务功能；

系统采用`system_code`和不同的路径前缀分离可能遇到的多端、多系统情况；

### 过滤器

项目提供两种过滤器：

1. 防XSS攻击`XSSFilter`
2. 参数去空格`TrimFilter`

### web拦截器

目前项目中存在`SystemInterceptor`和`AdminTokenInterceptor`两个拦截器：

1. `SystemInterceptor`拦截器拦截所有请求，校验`system_code`请求头的有无和有效性；
2. `AdminTokenInterceptor`拦截器拦截`/admin`后台管理系统请求，校验`token`和`refresh_token`请求头的有无和有效性；

### 系统基础

框架定义了基本类信息和其对应的基本数据库规则：

#### 基础实体类

```java
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建人id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long createdUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "最后修改人id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long lastModifiedUserId;

    @ApiModelProperty(value = "最后一次更新时间")
    private Date lastModifiedTime;

    @Dict
    @JsonSerialize(using = DictSerialize.class)
    @ApiModelProperty(value = "启用禁用状态", notes = "1:启用(默认);0:禁用")
    private Integer enabled;
}
```

#### 数据库基础表字段

```sql
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `index_dict_id_value` (`sys_data_dict_id`, `value`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'test' ROW_FORMAT = Dynamic;
```

#### `MyBatis`基础`ResultMap`

```xml
<resultMap id="BaseEntityMap" type="org.linitly.boot.base.helper.entity.BaseEntity">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="enabled" jdbcType="INTEGER" property="enabled" />
    <result column="created_user_id" jdbcType="BIGINT" property="createdUserId" />
    <result column="created_time" jdbcType="TIMESTAMP" property="createdTime" />
    <result column="last_modified_user_id" jdbcType="BIGINT" property="lastModifiedUserId" />
    <result column="last_modified_time" jdbcType="TIMESTAMP" property="lastModifiedTime" />
</resultMap>
```

这些基础字段基本是工作中常用，且项目中用不到也对项目无不良影响；统一了这些，可以简化工作，方便开发，减少代码冗余；

#### 框架使用

1. 生成的实体类继承`BaseEntity`（强烈建议：部分功能在此基础之上）
2. `MyBatis`的`Mapper`文件中`ResultMap`使用继承基础`BaseEntityMap`（可选：此功能可简化代码，业务复杂需要定义多个`ResultMap`时，效果明显）
3. 使用`DTO`、`Entity`、`VO`规范，可视项目需要增加或减少使用，其本身会增加开发成本，但复杂项目其解耦会带来维护便利，规避越维护越麻烦的情况，前后端分离不同人开发也会带来便利（建议）
4. `dao`层传参时，入参对象均传入与数据库表映射的实体类，不传入`DTO`等对象（强烈建议：部分功能在此基础之上）
5. 使用自动生成代码时，数据库表名和实体类名须符合驼峰转换规则（强烈建议：部分功能在此基础之上）；

#### 自动注入

框架利用`MyBatis`拦截器对于上述部分字段可自动注入，具体功能源码可查看`org.linitly.boot.base.interceptor.mybatis.DataInjectInterceptor`文件

注意：**自动注入的前提是对应`sql`存在该字段**

### 数据校验

项目推荐使用`Spring Valid`对数据进行校验，框架使用AOP对数据校验拦截，校验不通过直接返回错误信息。

校验AOP按路径切面，对`controller`控制层进行切面；目前对两种情况进行校验：

1. 方法参数中含有数据校验结果`BindingResult`；开发者可不用去写重复的判断和返回代码；

```java
public void insert(@RequestBody @Validated({InsertValidGroup.class}) AaDTO dto, BindingResult bindingResult) {
}
```

项目中已提供部分校验组，开发人员可在校验时按需使用

```java
DeleteValidGroup.class		// 删除校验组
InsertValidGroup.class		// 新增校验组
SearchValidGroup.class		// 查询校验组
UpdateValidGroup.class		// 修改校验组
```

2. 使用Restful风格Path路径时，对Path参数进行校验；支持`String`、`Long`、`Integer`三种路径参数数据进行校验；

```java
public void delete(@PathVariable Long id) {
}
```

### 数据返回

#### 统一返回对象

```java
@Data
public class ResponseResult<T> {
	
	@ApiModelProperty(value = "状态码")
	private Integer code;
	
	@ApiModelProperty(value = "提示信息")
	private String message;

	@ApiModelProperty(value = "返回数据")
	private T data;

	@ApiModelProperty(value = "特殊数据")
	private Object specialData;

	@ApiModelProperty(value = "时间戳")
	private long timestamp = System.currentTimeMillis();
}
```

#### 分页返回对象

```java
@Data
public class PageResponseResult<T> {
	
	// 分页信息
	private PageEntity pagination;
	
	// 分页查询时当前页查询出的结果集
	private T result;
}
```

```java
@Data
public class PageEntity {

	// 分页查询时数据总数
	public long totalCount;
	
	// 总页数
	public Integer totalPages;
	
	// 当前页
	public Integer pageNumber;
	
	// 当前页的数量
	public Integer pageSize;
	
	//是否为第一页
    public boolean isFirstPage = false;
    
    //是否为最后一页
    public boolean isLastPage = false;
    
    //是否有前一页
    public boolean hasPreviousPage = false;
    
    //是否有下一页
    public boolean hasNextPage = false;
}
```

#### 返回数据处理

1. `@Result`注解：`controller`层类上或方法上添加该注解，会对该接口返回数据进行封装，返回统一返回数据类型

```java
@Result
@RestController
public class AaController {}
```

```java
@RestController
public class AaController {
    @Result
    public void Bb(){};
}
```

2. `@Pagination`注解：在`controller`层方法上添加此注解，会对该接口返回数据进行分页封装，**前提需要在该接口上使用`PageHelper`启动分页并且返回数据类型为`List`**

```java
@Result
@RestController
public class AaController {
    @Pagination
    public List<A> findAll(){
        PageHelper.startPage(pageNumber, pageSize);
    }
}
```

3. 特殊情况：若出现特殊情况，也可在接口上返回统一数据类型，接口内自己封装返回；这样可以跳过统一返回处理

```java
@Result
@RestController
public class AaController {
    public ResponseResult Bb(){}
}
```

### 文件上传

项目集成了阿里OSS文件上传功能，文件位于`org.linitly.boot.base.controller.FileController`

开发人员只需要在配置文件中完成配置即可使用，配置示例如下：

```yaml
oss:
  access-key-id: XXXXXXXXXXX
  access-key-secret: XXXXXXXXXXX
  endpoint: http://oss-cn-hangzhou.aliyuncs.com
  bucket-name: test
  store-path: linitly-test/
```

**文件上传接口未使用权限控制，开发人员可根据自身项目需求添加**

### 权限机制

项目使用`JWT+Redis`进行作为前后端的交互token，并进行权限控制；后台管理系统使用RBAC模型，进行功能权限的控制；具体JWT相关权限代码可查看`org.linitly.boot.base.utils.auth`包和`org.linitly.boot.base.utils.jwt`包。

若项目中有多端、多系统的情况，对于不同系统可以有不同的机制，权限使用工厂模式，开发人员可对应多端扩展个自的权限生成和校验机制。

#### 后台管理功能权限

用户登陆之后返回`token`和`refresh_token`并将用户的各种权限信息存储在缓存`Redis`中，前端请求时需要在请求头中携带`token`和`refresh_token`请求头，`token`的过期时间较短，而`refresh_token`的过期时间较长，若`token`过期，后端可生成新`token`返回给前端，达到用户无感切换，避免用户频繁登陆，影响用户体验。

市面上大多数的JWT使用方式教程和博客都是仿Session的实现方式，开发人员如果需要可自行修改相关逻辑。

功能权限的校验是模仿`Shiro`框架完成，逻辑较为简单：接口请求时，拦截器对于请求头中的`token`和`refresh_token`进行校验，若某接口不需要`token`的校验(登陆)，可在拦截器中添加放行接口路径，示例如下：

```java
private static final List<String> UN_CHECK_URIS = new ArrayList<>();
static {
    UN_CHECK_URIS.add("/login");		// 登录接口
}
```

校验通过之后，使用AOP注解切面，对于具体权限拦截校验；在`controller`控制层可使用权限注解对于接口进行具体的权限控制，使用方法如下：

```java
@RequirePermission("aa:bb")								// 需要单个权限
@RequirePermission({"aa:bb", "cc:dd"})					// 需要多个权限
@RequirePermission(value = {"aa:bb", "cc:dd"}, logical = Logical.OR)		// 需要多个权限中的一个
@RequireRole("aa")										// 需要单个角色
@RequireRole({"aa", "bb"})								// 需要多个角色
@RequirePermission(value = {"aa", "cc"}, logical = Logical.OR)				// 需要多个角色中的一个
```

### 定时任务

在`SpringBoot`中使用定时任务，常见的一般分三种情况：

1. 使用`Spring`的`Scheduled`：集成和使用较简单，但无法动态管理且有线程问题；开发人员需要使用可在框架中集成即可；
2. 使用`quartz`：框架已集成了`quartz`框架，使用`JDBC`持久化方式，并实现了可后台管理的方式，相关功能源码可查看`org.linitly.boot.base.controller.SysQuartzJobController`，并且框架提供了quartz工具类，源码可查看`org.linitly.boot.base.utils.QuartzUtil`，数据库表信息可查看`resource/sql/linitly_boot.sql`；
3. 使用`quartz`，不动态管理：可通过`SpringBoot`的启动后执行方法来启动任务；也可将quartz的相关信息(触发器等)注入Spring，推荐使用第一种；

创建quartz定时任务示例：

```java
@Slf4j
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("运行时间：" + format.format(date));
    }
}
```

使用`SpringBoot`启动后执行触发任务示例：

```java
@Component
@Order				// 多个CommandLineRunner接口实现类，可使用@Order设定执行顺序
public class StartJobs implements CommandLineRunner {

    @Autowired
    private QuartzUtil quartzUtil;

    @Override
    public void run(String... args) throws Exception {
        quartzUtil.addCronJob("* * * * * ? *", TestJob.class);
    }
}
```

### 数据字典

框架实现了数据字典功能，详细接口源码可查看`org.linitly.boot.base.controller`包下的`SysDataDictController`和`SysDataDictItemController`；数据库表信息可查看`sql`文件；

如果需要给前端返回时进行翻译，可使用注解方式对字段翻译，使用示例如下：

```java
@Dict
@JsonSerialize(using = DictSerialize.class)
@ApiModelProperty(value = "启用禁用状态", notes = "1:启用(默认);0:禁用")
private Integer enabled;
```

使用说明：

1. 字典翻译功能是通过`SpringBoot`默认的序列化工具`jackson`对外提供的注解，从而实现自定义序列化方法，以此来翻译字典；

2. 在需要翻译的字典上添加`@Dict`和`@JsonSerialize(using = DictSerialize.class)`注解；

   `@Dict`注解中有一个`code`属性，根据此`code`来查找数据库中对应的字典中文翻译，如果不填写此属性，会自动使用该字段的转驼峰名作为`code`来匹配；

通过json序列化来实现翻译，使用上增加了麻烦，需要使用两个注解，但这种方式对框架侵入相对较小，与其它功能耦合度低。

### Excel导入导出

### 系统工具

另外框架中除了项目本身使用的一些工具类，还提供了一些其它常用工具类，在`org.linitly.boot.base.utils`包下：

```
- utils
	- algorithm			加密算法：RSA、AES、MD5、sha256、sha1
	- bean				bean相关工具类：获取Request、Response、Spring中注入的bean
	- http				httpclient工具类(github开源项目：)
    - valid				校验工具类
    IDateUtil			时间工具类
    ImageUtil			图片工具类
    RedisOperator		StringRedisTemplate工具类
    SnowFlake			雪花算法
```

`MyBatis`可以使用别名，本框架已集成，配置文件在`resource/mybatis/mybatis-config.xml`，使用习惯后可提高开发效率；

### 日志记录

本框架采用数据库记录日志数据方式（记录数据库数据变化日志），通过`MyBatis拦截器+AOP`实现，可通过配置控制功能的使用；

```java
@ConfigurationProperties(prefix = "linitly.generator")
public class GeneratorConfig {

    // 控制日志和删除的所有功能(表生成和记录)
    private boolean enabled = true;

    // 控制日志的所有功能(表生成和记录)
    private boolean logEnabled = true;

    // 控制日志的记录功能
    private boolean logRecord = true;
    
    // 控制日志的表生成功能
    private boolean logTableGenerator = true;
}
```

示例配置：

```yaml
linitly:
  generator:
    enabled: true
    log-enabled: true
    log-record: true
    log-table-generator: false
```

#### 日志表生成

项目启动后可根据数据库中表自动生成对应日志表，表格式固定；

表结构：

```sql
create table `aa_log` (
	`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
	``ip` varchar(63) NOT NULL DEFAULT '' COMMENT '用户ip',
	`operation` varchar(15) NOT NULL DEFAULT '' COMMENT '操作',
	`entity_id` bigint DEFAULT 0 COMMENT '操作实体id',
	`log` TEXT NOT NULL COMMENT '日志信息',
	`change_json` TEXT NOT NULL COMMENT '此次变化的json字符串',
	`operator_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '操作人id',
	`system_code` int(11) NOT NULL DEFAULT 0 COMMENT '系统码',
	`created_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
```

使用条件：

1. 开启配置，允许表生成
2. 需要生成日志表的表结构需符合基础表规则：具有数据库基础表字段

#### 日志记录

使用约束：

1. 配置开启，允许日志记录

2. 对于`INSERT`、`UPDATE`语句，`dao`层传参需为`BaseEntity`类型或`List<BaseEntity>`类型

3. 对于`DELETE`语句，`dao`层传参需为`BaseEntity`类型、`List<BaseEntity>`类型、`Long`类型、`List<Long>`类型其中之一
4. 记录日志是通过`dao`层文件名获取实体类名，再计算得到数据库业务表名，从而得到日志表表名，因此需符合规定；举例：

```
DAO层文件名：AaBbMapper  ------>  获得实体名：AaBb ------>  获得表名：aa_bb ------>  获得日志表名：aa_bb_log
```

使用自动生成工具时只需注意数据库表名和实体类名须符合驼峰转换规则即可，而这也符合大多数开发者的习惯，所以开发者不用担心。

#### 其它功能

在记录日志时，会将此次的数据转`json`存储，但可能遇见特别长的字段，有可能造成超出数据库职限制，此时可以在此字段上添加`@LogIgnore`注解，在转换时就不会记录此字段。

日志`aop`是根据路径切面的，所以默认是开启的，若有些操作不需要记录（例如：关联表新增或删除），开发者可通过违背上述约束，也可在`dao`层方法上添加`@LogIgnore`注解。

### 删除备份

数据库记录删除通过**注解AOP**功能实现，可通过配置控制功能的使用；

```java
@ConfigurationProperties(prefix = "linitly.generator")
public class GeneratorConfig {

    // 控制日志和删除的所有功能(表生成和记录)
    private boolean enabled = true;

    // 控制删除的所有功能(表生成和记录)
    private boolean deleteEnabled = true;

    // 控制删除的表生成功能
    private boolean deleteTableGenerator = true;

    // 控制删除的记录功能
    private boolean deleteBackupRecord = true;
}
```

示例配置：

```yaml
linitly:
  generator:
    enabled: true
    delete-enabled: true
    delete-table-generator: false
    delete-backup-record: true
```

#### 表生成

项目启动后可根据数据库中表自动生成对应删除备份表，表格式不固定，表格式为在业务表基础上增加三个字段；

表格式：

```sql
-- 额外增加字段
`delete_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '删除时间',
`deleted_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '删除用户id',
`system_code` int(11) NOT NULL DEFAULT 0 COMMENT '系统码',
```

使用条件：

1. 开启配置，允许表生成
2. 需要生成删除备份表的表结构需符合基础表规则：具有数据库基础表字段

#### 备份

使用约束：

1. 配置开启，允许备份删除记录
2. 有`@DeleteBackup`注解（需在`dao`层方法上使用）
3. 删除备份是通过`dao`层文件名获取实体类名，再计算得到数据库业务表名，从而得到备份表名，因此需符合规定；（同日志）
4. `dao`层传参需为`BaseEntity`类型、`List<BaseEntity>`类型、`Long`类型、`List<Long>`类型其中之一


### Swagger导出离线文档
1. 执行`mvn swagger2markup:convertSwagger2markup`命令生成`adoc`文件

2. 执行`mvn generate-resources`命令可生成对应`html`和`pdf`文件

#### PDF文件导出空白、乱码问题解决
1. 找到maven仓库中`asciidoctorj-pdf-1.5.0-alpha.10.1.jar`文件
2. 使用压缩工具打开jar包，进入`gems\asciidoctor-pdf-1.5.0.alpha.10\data\`目录
3. 将`tools/asciidoctorj-pdf-replace-file`文件夹下的四个`ttf`文件复制到`fonts`文件夹下
4. 将`tools/asciidoctorj-pdf-replace-file`文件夹下的`default-theme.yml`文件复制替换到`themes`目录下
5. 重新执行上诉命令生成即可

### 代码生成

框架提供适用于本项目的代码生成器，基于`mybatis-generator`，可生成一套基本的后端开发文件，使用方法基本和`mybatis-generator`的使用方法一致；

上述中有提到部分功能对此有约束：数据库表名和实体类名须符合驼峰转换规则，若需要使用此部分功能，开发者需要遵循

举例：

```xml
<table tableName="aa_bb" domainObjectName="AaBb"/>
```

在`tools/linitly-generator`目录下，有完整的代码生成工具，配置好`xml`之后，运行`run.bat`文件即可；

详细代码、运行效果的文档可以查看该项目：

GitHub地址：https://gitee.com/linitly/linitly-generator.git

Gitee地址：https://gitee.com/linitly/linitly-generator.git