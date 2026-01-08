package ru.pim.camunda_demo.utils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * Проверяет beans в контексте приложения.
 * А также пример того, как можно создавать этот бин, проверяя наличие свойств в контексте.
 */
@Component
public class BeanChecker implements Condition {

    private final GenericApplicationContext context;

    public BeanChecker(GenericApplicationContext context) {
        this.context = context;
    }

    /**
     * Создаем бин только тогда, когда свойство есть в контексте и matches вернет true
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var env = context.getEnvironment();
        var checkBeansEnabled = env.getProperty("check.beans.enables");
//        var mainBalancingGroup = env.getProperty("main.balancing.group");

//        return ("true".equals(checkBeansEnabled) && "DUPLICATE".equals(mainBalancingGroup)) ||
//                (checkBeansEnabled == null && "DUPLICATE".equals(mainBalancingGroup));
        return "true".equals(checkBeansEnabled);
    }

    /**
     * Проверка beans в контексте приложения
     */
    public void checkBean() {
        System.out.println("BeanChecker is working. Check beans:");
        System.out.println("Total Beans: " + context.getBeanDefinitionNames().length);
        System.out.println("Total Beans: " + context.getBeanDefinitionCount());

        for (String beanName : context.getBeanDefinitionNames()) {
            Class<?> beanClass = context.getType(beanName);
            if (beanClass != null) {
                if (!Modifier.isAbstract(beanClass.getModifiers()) && !Modifier.isInterface(beanClass.getModifiers())) {
                    System.out.println("Found Bean Name: " + beanName + " with class: " + beanClass.getName());
                }

                if (!beanName.isEmpty()) {
                    System.out.println("Simple Bean Name: " + beanName);
                }
            } else {
                System.out.println("No Bean found for name: " + beanName);
            }
        }
    }
}
