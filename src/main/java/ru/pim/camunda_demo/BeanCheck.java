package ru.pim.camunda_demo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.pim.camunda_demo.utils.BeanChecker;

@Component
@ConditionalOnProperty(name = "check.beans.enables", havingValue = "true", matchIfMissing = true)
public class BeanCheck {

    private final BeanChecker beanChecker;

    public BeanCheck(BeanChecker beanChecker) {
        this.beanChecker = beanChecker;
    }

    @PostConstruct
    public void beanCheck() {
        beanChecker.checkBean();
    }
}
