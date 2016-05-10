package mvp.gxj.com.myapplication;

/**
 * Created by gxj on 2016/5/10.
 * 自定义注解测实现
 *
 * 简单说一下注解：定义的关键字@interface ;
 * @Target表示该注解可以用于什么地方，可能的类型TYPE（类）,FIELD（成员变量）,可能的类型：
 * @Retention表示：表示需要在什么级别保存该注解信息；我们这里设置为运行时。
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {

    int value();
}
