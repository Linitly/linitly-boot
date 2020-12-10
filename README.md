# linitly-boot



## 前端对接说明

### 数据

统一返回数据格式(一级)如下：

```json
{
    "code": 200,							// 状态码，需先判断
    "message": "成功",					   // 提示信息，状态码非正常情况，除特殊情况，可直接提取该信息
    "data": null,							// 返回业务数据
    "specialData": null,					// 特殊数据，一般用不到，特殊情况用到特别说明
    "timestamp": 1607485350802				// 时间戳，仅供参考，不用处理
}
```

前端需首先判断`code`状态码，`200`为业务处理正常，其它为异常情况；

目前`412`和`417`状态码需提示后，跳转到登陆页面；其它情况可直接提取提示信息。

#### 返回数据说明

返回数据一般分以下几种情况：

1. **无业务数据**，格式如下：

```json
{
    "code": 200,
    "message": "成功",
    "data": null,
    "specialData": null,
    "timestamp": 1607485350802
}
```

2. **单业务数据**，格式如下：`data`内的内容为直接数据

```json
{
    "code": 200,
    "message": "成功",
    "data": {
        "xxx": "xxx",
        "xxx": "xxx"
    },
    "specialData": null,
    "timestamp": 1607485350802
}
```

3. **多业务数据，无分页信息**，格式如下：`data`内为业务数据数组

```json
{
    "code": 200,
    "message": "成功",
    "data": [{
        "xxx": "xxx",
        "xxx": "xxx"
    },{
        "xxx": "xxx",
        "xxx": "xxx"
    }],
    "specialData": null,
    "timestamp": 1607485350802
}
```

4. **多业务数据，有分页信息**，格式如下：`data`内分两部分，`pagination`为分页信息，`result`为具体业务数据数组

```json
{
    "code": 200,
    "message": "成功",
    "data": {
        "pagination": {
            "totalCount" : 0,				// 数据总数
            "totalPages" : 0,				// 总页数
            "pageNumber" : 0,				// 当前页
            "pageSize" : 0,					// 当前页的数量
            "isFirstPage" : false,			// 是否为第一页
            "isLastPage" : false,			// 是否为最后一页
            "hasPreviousPage" : false,		// 是否有前一页
            "hasNextPage" : false			// 是否有下一页
        },
        "result" : [{
            "xxx" : "xxx",
            "xxx" : "xxx"
        },{
            "xxx" : "xxx",
            "xxx" : "xxx"
        }]
    },
    "specialData": null,
    "timestamp": 1607485350802
}
```

#### Swagger

后端采用**Swagger**作为前后端文档技术。

1. 通用数据：文档中，接口返回数据中的**通用数据(一级)**和**分布信息**不在文档中体现，文档中接口返回数据只显示具体需要返回的业务数据，无业务数据返回则文档中无接口返回数据；
2. 业务数据一般包含几个通用字段，不会在文档中展示，但部分仍会在接口请求后返回，无相关数据的逻辑需要处理可忽略；

```json
{
    "id" : 0,									// 业务数据id,唯一标识
    "createdUserId" : 0,						// 创建人id
    "createdTime" : 1607485350802,				// 创建时间
    "lastModifiedUserId" : 0,					// 最后修改人id
    "lastModifiedTime" : 1607485350802,			// 最后一次更新时间
    "enabled" : 0								// 启用禁用状态
}
```

3. 新增数据时，通常在文档请求参数中也会现`id`唯一标识，可忽略，新增不需要传入该字段；

### 请求

#### 安全机制

1. 请求时须**全局**携带请求头`system_code`，具体值会在对接时给出；
2. 用户登陆之后，会返回`token`和`refresh_token`，前端需保存本地，并在获取之后的请求中携带两个参数，放入请求头；

> 以上三个参数在请求头中的属性名分别为：`system_code`、`token`、`refresh_token`

3. 前端在请求之后，须全局监听响应信息，若响应头中返回了`token`，须替换本地的`token`，并在之后的请求中携带新token；

#### 请求格式

1. 后端采用非标准Restful格式，无特殊说明，一般请求为`POST`请求；

2. 请求类型无特殊情况，为`json`格式请求，即：`Content-Type: application/json`;

3. 分页请求时，分页请求参数在请求参数中，即在url后拼接；

   页码参数名固定为==pageNumber==，单页数据量参数名固定为==pageSize==；其它参数仍在json请求体中；

   > 例：`http://xxx.com/xxx?pageNumber=1&pageSize=10`

4. 关于时间数据的处理，前后端统一采用**13位时间戳**，传值和返回均为时间戳，各自处理转换；

### 数据字典

后端提供数据字典功能，前端在需要的地方可以调用字典，不建议前端自己枚举写死；

且后端可以在返回时自动翻译字典，需要时可以和后端沟通。

举例：

**启用/禁用**状态，参数名为`enabled`，对应值为`1/0`，前端需要做下拉选择，可调用接口获取。

业务数据中返回包含了`enabled`该参数，但具体值为`1/0`，若需要翻译字义展示，可与后端沟通，在返回时直接返回中文转义**启用/禁用**。









