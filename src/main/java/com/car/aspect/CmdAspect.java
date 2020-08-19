package com.car.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/15  17:09
 * @Description 下达指令切面类
 * 在完成了引入AOP依赖包后，不需要去做其他配置。AOP的默认配置属性中，spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，
 * 默认已经增加了@EnableAspectJAutoProxy，不需要在程序主类中增加@EnableAspectJAutoProxy来启用。
 */
@Aspect
@Component
public class CmdAspect {

    //    这样在 相应 包下，只有我们加上@SaveCmd 注解的方法切面方法才会起作用。
    @Pointcut("execution(public * com.car.service.MonitorService.*(..))&&@annotation(com.car.annontation.SaveCmd)")
    public void addCmdAdvice() {
    }

    /**
     * 前置通知（Before advice） ：在某连接点（JoinPoint）之前执行的通知，但这个通知不能阻止连接点前的执行。
     * 这里用到了JoinPoint和RequestContextHolder。通过JoinPoint可以获得通知的签名信息，
     * 如目标方法名、目标方法参数信息等。通过RequestContextHolder来获取请求信息，Session信息。
     *
     * @param joinPoint
     */
    @Before("addCmdAdvice()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("=====SysLogAspect前置通知开始=====");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        System.out.println(signature.getName());
        //AOP代理类的名字
        System.out.println("=====AOP代理类的名字" + signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        if (obj.length > 0) {
            System.out.println("=====请求的参数信息为：" + str);
        }
    }

    /**
     * 后通知（After advice） ：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
     * 对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * 原文链接：https://blog.csdn.net/zknxx/article/details/53240959
     *
     * @param joinPoint
     */
    @AfterReturning(value = "addCmdAdvice()", returning = "result")
    private void doAfter1(JoinPoint joinPoint, Object result) {
        System.out.println("=====第一个后置返回通知的返回值：" + result);
    }

    @AfterReturning(value = "addCmdAdvice()", returning = "result", argNames = "result")
    private void doAfter2(Object result) {
        System.out.println("=====第二个后置返回通知的返回值：" + result);

    }


    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     *
     * @param joinPoint
     */
    @After("addCmdAdvice()")
    public void doAfterAdvice(JoinPoint joinPoint) {
        System.out.println("=====后置最终通知执行了!!!!=====");
    }


    /**
     * 抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
     *
     * @param joinPoint
     * @param e         定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *                  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *                  对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     */
    @AfterThrowing(value = "addCmdAdvice()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Throwable e) {
        //目标方法名：
        System.out.println(joinPoint.getSignature().getName());
        if (e instanceof NullPointerException) {
            System.out.println("=====发生了空指针异常!!!!!");
        }
    }

    /**
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * @description
     * 环绕通知接受ProceedingJoinPoint作为参数，它来调用被通知的方法。
     * 通知方法中可以做任何的事情，当要将控制权交给被通知的方法时，需要调用ProceedingJoinPoint的proceed()方法。
     * 当你不调用proceed()方法时，将会阻塞被通知方法的访问。
     */
//    @Around("addCmdAdvice()")
//    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
//        try {
//            System.out.println("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());

//            System.out.println("***经纪人正在处理球星赛前事务！***");
//            pjp.proceed();
//            System.out.println("***返回通知：经纪人为球星表现疯狂鼓掌！***");
//        } catch (Throwable e) {
//            System.out.println("***异常通知：球迷要求退票！***");
//        }
//    }

}
