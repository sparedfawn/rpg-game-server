package com.example.demo.model.quest.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${aspect.enable:true}")
public class QuestAdvice {

    @Around("@annotation(QuestProgressable)")
    public Object execTest(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = joinPoint.proceed();

        //fixMe processing rzeczy do joinPointa z annotacja

        return object;
    }
}
