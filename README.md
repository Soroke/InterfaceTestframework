## InterfaceTestframework
# 使用说明
#### 1、根据excel中的提示信息添加接口和用例的信息
#### 2、excel编写完成后将其放在当前项目所在目录下
#### 3、执行maven命令 mvn clean install 进行测试
#### 4、执行完成后使用allure生成测试报告,项目集成到jenkins请参考[这里](https://docs.qameta.io/allure/2.0/#_jenkins)
# 版本支持信息
### 1、	接口执行类型</br>
支持接口类型为**post、get**(get延期)</br>
### 2、	前置条件</br>
支持填写前置case的ID；校验前置用例实行结果(延期)</br>
支持自定义前置值 以供下面的参数和校验时使用(延期)</br>
### 3、	入参</br>
支持多条参数同时传入</br>
支持参数值为前置用例的返回值(延期)</br>
支持参数值为前置条件自定义值(延期)</br>
### 4、	状态码检查</br>
支持接口返回状态码检查</br>
### 5、	接口字段检查</br>
#####   仅支持接口返回为标准json的校验</br>
-----------
如果接口返回json如下
```Json
{
  "lotto":{
    "lottoId":5,
    "winning-numbers":[2,45,34,23,7,5,3],
    "winners":[{
        "winnerId":23,
        "numbers":[2,45,34,23,3,5]
        },{
        "winnerId":54,
        "numbers":[52,3,12,11,18,22]
     }]
  },
  "id":1,
  "name":"soroke"
}
```
支持验证格式：
```
id=1&&name=soroke&&lotto.lottoId=5&&lotto.winning-numbers[3]=23
```
支持key=value检查使用key匹配接口返回获取值和value对比，可以为多个键值对；</br>
key和value都是固定值</br>
value可以是前置用例的返回值(延期)</br>
### 6、	数据库检查</br>
支持校验SQL结果是否为空</br>
支持校验SQL结果的值为某个固定值</br>
支持校验SQL结果的值为前置用例的返回值(延期)</br>
支持校验SQL结果为多条数据(延期)</br>
### 7、	一条接口可以对应多条用例，提取出接口的类型、url、参数字段 避免在用例中重复编写</br>
### 8、	一条SQL可以关联多条用例避免相同的SQL重复编写</br>
### 9、	多条SQL可以关联同一条用例</br>
### 10、数据分离，只需要维护excel中的用例来完成日常用例的增删改</br>
