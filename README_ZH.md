# Asinx Pay官方文档

[Asinx Pay]API开发者文档([English Docs](./README.md))。
本项目是Java 版官方demo, 可以下载运行测试。
# 介绍

欢迎使用[Asinx Pay]开发者文档。 本文档提供了开户，设置用户KYC信息，卡片申请，充值，信息查询等相关API的使用方法介绍。

# API接口加密验证

## 获取 appId 和 appSecret

请联系商务开通，获取密钥信息。

测试秘钥信息：

* appId='app_447770'

* appSecret='b635dd5c87f7bf73387929203321b1e1'



## 发起请求

所有REST请求都必须包含以下参数：

* appId作为一个字符串,需以 **appId** 作为key添加到所有请求的header中。
* sign 使用一定算法得出的签名（请参阅签名信息），需以**sign** 作为key添加到所有请求的header中。
* 所有请求都应该含有 **application/json;charset=UTF-8** 类型内容，并且是有效的JSON字符串。

## 签名

sign 参数是使用对应接口**PATH** 和对 **所有参数按照字典排序之后转换为JSON字符串**拼接，进行 **BASE64**编码，然后使用 **appSecret**作为密钥进行 **AES128** 加密得到。

例如：对于如下的请求参数进行签名,密钥字符串为 **123456**

```bash
curl  "https://test.asinx.io/api-web/user/register"
```
* 注册账户，以 mobileNumber=13800138000, mobilePrefix=86 为例
```javascript
  {
        "mobileNumber": "18888888867", 
        "mobilePrefix": "1",
        "email": "188888888662@188.com"
        }
```
* 按字典排序之后，为
```javascript
  {
        "email": "188888888662@188.com",
        "mobileNumber": "18888888867",
        "mobilePrefix": "1"
        }
```
* 生成待加密的字符串
```javascript
dataJson = '{"email":"188888888662@188.com","mobileNumber":"18888888867","mobilePrefix":"1"}'
```

* 接口PATH 与 dataJson 拼接
```javascript
originString = '/user/register{"email":"188888888662@188.com","mobileNumber":"18888888867","mobilePrefix":"1"}'
```

* 接口originString 进行**base64**编码
```javascript
base64String = 'L3VzZXIvcmVnaXN0ZXJ7ImVtYWlsIjoiMTg4ODg4ODg4NjYyQDE4OC5jb20iLCJtb2JpbGVOdW1iZXIiOiIxODg4ODg4ODg2NyIsIm1vYmlsZVByZWZpeCI6IjEifQ=='
```

* 使用secret作为密钥对base64String 进行**AES128** 加密
```javascript
aes128String = 'aa71f61b66b0436856be41b40ea099029bc25e43ae01d39f7332a8a56da3214d5b054c32c702142ade9a87f98cc3fca43a7625413671dff58fc6678faa760466b012d42a2b396c6218d04973b1fba60de82e593d22ddcf9f54f7fccb2cef876fe2e96e2276f672b44ebf6207ca823b0a0229ad71fa98c286f14adede9283d160d6ec88adf38227242f491052e1bd3edc'
```

* 对aes加密后字符串进行**MD5**加密得到32位摘要字符串
```javascript
signature = 'f61ff896372b9e5836cb67a069dd810a'
```

* 将appId 与 加密后得到的签名字符串，分别放入请求的header中
``` java
request.header("appId", xxxxxx);
request.header("sign", "44712f58a9455526e37567a4f9c67137");
```

## 响应数据

所有接口返回数据均为有效JSON数据。结构如下：
```javascript
{
        "total":9, // 当result为数据列表时，该字段为数据条数
        "success": true, // 是否成功
	"result": "JkLoxhGUGR8WB3ze8X1HfRAFnv0DJ5zy+Bm//Zvk4TII9XC+n3ppjhm2OFes0Wrh", // 响应数据
	"code": 1, // 响应码
	"message": "Successful!" // 响应描述
}
```
result 字段为数据实体，需要使用密钥进行AES128 解密，然后进行base64解码。得到完整的JSON字符串。

例如用户注册接口返回
```javascript
{
    {
        "success": true,
        "result": "9LOSTUUpwaho2yDtmNVfLWgqVcH+wfWKtfLpBxFLvonk8quZi5mMCnhsbN84DT1P",
        "code": 1,
        "message": "Successful!"
    }
}
```

对 result 进行AES128解密和Base64解码后，得到数据:

```javascript
{
    "uid": "1234"
}
```
那么，用户注册成功，并且用户ID 为 '1234'.

[Asinx Pay]: https://www.asinx.io

# ASINX PAY API参考

## 用户相关接口

### 1.注册账户

**HTTP请求**

```javascript
    # Request
    
    POST /user/register
    
    example: https://test.asinx.io/api-web/user/register

    {
        "email": "188888888662@188.com",
        "mobileNumber": "18888888867",
        "mobilePrefix": "1"
    }
```
***request 字段说明***

|字段|说明|是否必须|类型|
| ---------- |:-------:|-------|---|
| email| 邮箱 | YES| String|
| mobileNumber | 手机号 |YES|String|
| mobilePrefix | 手机国家代码,比如中国是86，韩国82，日本81等  |YES|String|

```javascript
    # Response
{
    "success": true,
    "result": "bOodDEqstZ82BRjTuLRE5PBmcIixXsMxNPOVqS+iyBfk8quZi5mMCnhsbN84DT1P",
    "code": 1,
    "message": "Successful!"
}
```

result 解密后json字符串

```javascript
{
    "uid": "1234"
}
```

**Result值说明**


|返回字段 | 字段说明|
| ---------- |:-------:|
| uid      | 用户ID |

### 2. 设置用户职业及KYC信息

**HTTP请求**

```javascript
    # Request
    
    POST /user/setProfession

    example: https://test.asinx.io/api-web/user/setProfession
    
    #body
    {
        "annual_income": "100000",
        "birthday": "2000-01-01",
        "country": "CN",
        "expiry_date": "2027-01-01",
        "first_name": "ming",
        "first_name_en": "ming",
        "id_type": "passport",
        "last_name": "li",
        "last_name_en": "li",
        "number": "123456",
        "occupation": "boss",
        "position": "management",
        "frontImg":"data:image/jpg;base64,xxxxxxxx..........",
        "backImg":"data:image/jpg;base64,xxxxxxxx.........."
    }
```

***需要额外设置的请求头***

需要增加'uId'请求头，值=uid,来自[用户注册](#1.注册账户)返回的数据。


***请求字段***

|字段名 | 描述|是否必填|类型|格式|
| ---------- |:-------:|-------|---|---|
| first_name     | 用户名 | YES |String||
| last_name     | 用户姓 |YES|String
| first_name_en     | 英文用户名  | YES|String
| last_name_en     | 英文用户姓   | YES|String
| birthday     | 用户生日   | YES|String| YYYY-MM-DD
| occupation     | 用户职业   | NO|String
| position     | 用户职位  | NO|String
| annual_income     | 用户年收入    | NO|String| 需换算为港币|
| id_type     | 用户证件类型  | YES|String| 需要是 [证件类型](#证件类型)中的选项|
| country     | 用户国家代码  | YES|String| 需要是 [国家代码](#国家代码与支持的证件类型)中的选项|
| number     | 证件号   | YES|String
| expiry_date     | 证件过期时间   | YES|String| YYYY-MM-DD
| frontImg     | 证件正面照片  | YES|String| [image format](#图片数据说明)|
| backImg     | 证件背面照片   | 非必须，护照可以不传该字段，身份证必须传背面图片|String|[image format](#图片数据说明)|

**HTTP 响应**

公共响应


## 卡片相关接口

### 1. 获取所有卡片信息

**HTTP请求**

```javascript
    # Request
    
    POST /bankcard/template/list
    
    example: https://test.asinx.io/api-web/bankcard/template/list
```
```javascript
    # Response
{
    "total": 9,
    "success": true,
    "result": "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",
    "code": 1,
    "message": "Successful!"
}
```

result 解密后json字符串

```javascript
[{
	"applyDiscount": 1,
	"applyFee": 10.000000000000000000,
	"bankCardNature": "VIRTUAL",
	"bankCardSource": "VCC",
	"bankCardType": "VISA",
	"categoryId": 1,
	"ccy": "USD",
	"description1": "• VISA，MASTER CARD supported",
	"description2": "• Charge Cryptocurrency and fiat to the account in seconds",
	"enable": true,
	"hot": true,
	"id": 1,
	"img": "https://test.asinx.io/static-res/bankcard/1687533514825.png",
	"monthFee": 0.00,
	"rechargeFee": 0.013000000000000000,
	"recommend": true,
	"sortParam": 9,
	"title": "Monthly membership card"
},
    ...
]
```
**Result值说明**


|返回字段 | 字段说明|
| ---------- |:-------:|
| applyFee      | 申请费(USD) |
| applyDiscount        | 申请费折扣比例
| bankCardNature | 卡片质地:VIRTUAL(虚拟卡),PHYSICAL(实体卡)
| bankCardType     | 卡片结算方 VISA / MASTER  |
| ccy       | 卡片币种 |
| description1       | 卡片描述1 |
| description2  | 卡片描述2 |
| id   | 卡片ID(卡片唯一标识，可作为申请卡片参数) |
| img          | 卡片图片 |
| monthFee       | 月费(USD) |
| rechargeFee   | 充值费率 |
| title      | 卡片名称 |


# 枚举字段介绍

### 响应码
|code | 含义|
| ---------- |:-------:|
| 1| 成功|
|2|参数错误|
|200|业务错误，具体原因查看返回信息|
|212|账户余额不足 |
|99997|因外部原因或上游未及时返回处理结果，处理结果稍后由 webhook 通知|

### 证件类型
|类型 | 描述|
| ---------- |:-------:|
| passport| 护照|
|national-id|身份证|

### 国家代码与支持的证件类型
|代码 | 国家|是否支持护照| 是否支持身份证|
| ---------- |:-------:|---|---|
| CN| 中国大陆| YES|YES|
|HK|中国香港|YES|YES|
|KOR|韩国|YES|NO|
|JP|日本|YES|NO|
|SG|新加坡|YES|NO|
|UK|英国|YES|NO|
|US|美国|YES|NO|

### 卡片状态
|状态枚举值 | 描述|
| ---------- |:-------:|
|AUDITING|卡片审核中|
|AUDIT_PASS|卡片审核通过|
|AUDIT_NOT_PASS|卡片审核未通过，需要修改后再次提交审核|
|TBA|卡片待激活（完成首次充值，卡片自动激活）|
|ACTIVE_PROCESSING|卡片激活处理中（由于网络或上游业务原因，卡片激活可能无法及时返回结果，需要等待回调通知或主动查询卡片状态，获取处理结果）|
|ACTIVE|卡片已激活，可正常使用|
|CLOSE_PROCESSING|卡片销卡处理中（由于网络或上游业务原因，卡片激活可能无法及时返回结果，需要等待回调通知或主动查询卡片状态，获取处理结果）|
|CLOSE|已销卡|
|EXCHANGE_PROCESSING|换卡处理中（预留字段）|

### Transaction status （交易状态）
|status | description|卡类型|
| ---------- |:-------:|:-------:|
| APPROVED| 成功，确认 |虚拟卡|
|DECLINED |失败，被拒绝 |实体卡|
|pending |待处理，待结算 |实体卡|
|posted |完成结算 | 实体卡|
|declined |拒绝 |实体卡|
|vodi |取消 |实体卡|

### Auth types（授权类型）
|status | description|
| ---------- |:-------:|
| PURCHASE| 消费，支出 |
|ATM |ATM 取现 |

### Transaction types （交易类型）
|type | description|card type|
| ---------- |:-------:|:-------:|
|AUTH| 授权消费 |虚拟卡|
|AUTH_QUERY |授权查询|虚拟卡|
|REVERSAL |冲正 |虚拟卡|
|REFUND |退款 |虚拟卡|
|FEE |手续费,部分卡段跨境手续费 |虚拟卡|
|TRADE_PROCESS_FEE |交易处理手续费 |虚拟卡|
|TRADE_CROSS_BOARD_FEE |交易跨境手续费 |虚拟卡|
|TRADE_REFUND_FEE |交易退款手续费|虚拟卡|
|FEE_REVERSAL |手续费冲正 |虚拟卡|
|ORIGINAL_CREDIT | otc 退款,部分卡段退款类型|虚拟卡|
|ORIGINAL_CREDIT_REVERSAL |otc 退款冲正,部分卡段退款类型|虚拟卡|
|charge| 支出，消费 |实体卡|
|refund| 退款 | 实体卡|
|topup|  充值，加余额 |实体卡|
|withdraw|  提现 |实体卡|
|transfer| 转账 |实体卡|
|cashback| 现金返还 |实体卡|
|interest| 利息 |实体卡|
|fee| 手续费 |实体卡|
|other| 其他类型 |实体卡|

### Transaction Response Code(交易记录内响应码)
|code | 描述|卡类型|
| ---------- |:-------:|:-------:|
|000000	|交易成功|虚拟卡|
|100001	|交易失败（账户异常）|虚拟卡|
|200001	|交易失败（卡片无效）|虚拟卡|
|200002	|交易失败（余额不足）|虚拟卡|
|200003	|交易失败（CVV有误）|虚拟卡|
|200004	|交易失败（有效期有误）|虚拟卡|
|200005	|交易失败（交易币种有误）|虚拟卡|
|200006	|交易失败（非多次卡)|虚拟卡|
|200007	|交易失败（超出限额)|虚拟卡|
|200008	|交易失败（卡片异常)|虚拟卡|
|200009	|交易失败（交易次数超限)|虚拟卡|
|300001	|交易失败（无效商家）|虚拟卡|
|400001	|交易失败（高风险交易）|虚拟卡|
|400002	|交易失败（无效金额）|虚拟卡|
|400003	|交易失败（无效交易)|虚拟卡|
|400004	|交易失败（查询不到原交易）|虚拟卡|
|400005	|交易失败（原交易状态异常）|虚拟卡|
|400006	|交易失败（不支持此交易类型|虚拟卡 
|400007	|交易失败（交易超时）|虚拟卡|
|400008	|交易失败（风控拒绝）|虚拟卡|
|400009	|交易失败（渠道拦截）|虚拟卡|
|400010	|交易失败（退款超限）|虚拟卡|
|400011	|交易失败（授权频繁）|虚拟卡|
|400012	|交易失败（决策拒绝）|虚拟卡|
|400013	|交易失败（冲正后又发生请款)|虚拟卡 
|999997	|交易失败（渠道异常）|虚拟卡|
|999998	|交易失败（系统升级中）|虚拟卡|
|999999	|交易失败（未知异常）|虚拟卡|

# 图片数据说明

[java 示例](./src/main/java/com/asinx/utils/Base64ImgUtil.java) or [php 示例](./PHP-signature-Demo/image-base64-demo.php)

图片数据需遵循的格式如下 :
"data:"+ 图片mine信息 +";base64," + 图片base64处理后数据.
    