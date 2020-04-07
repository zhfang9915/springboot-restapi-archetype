# 快速开始

## 1. API基础

### 1.1 ApiResult
* 默认全局支持接口统一返回 `ApiResult` 格式报文，全局异常捕获后统一返回`ApiResult` 格式报文
> 若需要返回接口原定义类型，可在接口方法或者类上添加注解`@NotCastApiResult`,在类上添加此注解，当前类下所有方法返回类型不进行统一转换
> ```java
> @NotCastApiResult
> public String createAccount(@RequestBody @Valid Account account/*, BindingResult bindingResult*/) {
>     // 返回String类型，不进行转换ApiResult 
> }
> ```
> 建议：返回状态码采用Http响应状态码

* 默认集成`spring-boot-starter-validation`，支持hibernate-validation校验
> 再参数绑定时使用`@Valid`注解进行参数校验，可不使用`BindingResult`进行错误信息的绑定，
> 默认支持校验通过捕获全局`MethodArgumentNotValidException`异常统一处理，并返回`ApiResult`类型
>```java
>@ExceptionHandler(MethodArgumentNotValidException.class)
>public ApiResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
>     ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
>     return new ApiResult<>().code(HttpStatus.BAD_REQUEST.value()).message(objectError.getDefaultMessage());
>}
>```

* 支持采用注解`@ApiLink`返回下一个接口地址信息，存在`@NotCastApiResult`注解时`@ApiLink`无效
> ```java
> // 接口方法上添加注解
> @ApiLink(title = "查询用户信息", method = RequestMethod.GET, url = "/api/v1/user")
> ```
> // 响应报文效果
> ```json
> {
>  	"code": 200,
>  	"message": null,
>  	"data": "create success",
>  	"link": {
>  		"title": "查询用户信息",
>  		"method": "GET",
>  		"url": "/api/v1/user",
>  		"type": "JSON"
>  	}
>  }
> ```

* 支持多版本接口，可使用`@ApiVersion`在类或方法上标注接口版本，方法优先。
>```java
>@RestController
> public class TestController implements ITestController {
>     @ApiVersion(1)
>     @Override
>     public String createAccount(@RequestBody @Valid Account account) {
>         return "v1 create success";
>     }
> }
>``` 

持续更新