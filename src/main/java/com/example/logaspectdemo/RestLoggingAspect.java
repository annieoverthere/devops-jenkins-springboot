package com.example.logaspectdemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class RestLoggingAspect {

    @Around("(@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public Object logRestCall(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = pjp.getArgs();

        String httpMethod = "";
        String uri = "";

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            httpMethod = request.getMethod();
            uri = request.getRequestURI();
        }

        System.out.printf(
                "[REST LOG] ENTER: %s.%s (HTTP=%s, URI=%s, args=%s)%n",
                className, methodName, httpMethod, uri, Arrays.toString(args));

        Object result = pjp.proceed();

        if (!"/error".equals(uri)) {
            System.out.printf(
                    "[REST LOG] RESPONSE: %s%n",
                    result);
        }

        long duration = System.currentTimeMillis() - start;

        System.out.printf(
                "[REST LOG] EXIT: %s.%s (duration=%d ms)%n",
                className, methodName, duration);

        return result;
    }
}
