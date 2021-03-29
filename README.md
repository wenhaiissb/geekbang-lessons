# geekbang-lessons
极客时间课程工程


# 作业思路


## 第5周
### 第一题：修复本程序 org.geektimes.reactive.streams 打印缺失问题
在满足条件后面输出接收数据即可


### 地二题：继续完善 my-rest-client POST 方法
测试接口：
```java
@RestController
@SpringBootApplication
public class BootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootWebApplication.class, args);
    }


    @PostMapping("/user")
    public User user(@RequestBody User user) {
        System.out.println("POST 请求接收到 User 对象为:" + user);
        return user;
    }
}

```
```java
public class User {
    private Long id;
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```