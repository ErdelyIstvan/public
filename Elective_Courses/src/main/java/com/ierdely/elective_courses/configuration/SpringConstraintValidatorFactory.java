package com.ierdely.elective_courses.configuration;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;

//@Component
public class SpringConstraintValidatorFactory {// implements ConstraintValidatorFactory {

//
//
//    private final ApplicationContext applicationContext;
//    private final ConstraintValidatorFactory defaultFactory = new ConstraintValidatorFactoryImpl();
//
//
//    @Autowired
//    public SpringConstraintValidatorFactory(ApplicationContext applicationContext) {
//    	
//        this.applicationContext = applicationContext;
//    }
//    
//    @Override
//    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
//    	
//        if (applicationContext.getBeanNamesForType(key).length > 0) {
//            return applicationContext.getBean(key);
//        } else {
//            return defaultFactory.getInstance(key);
//        }
//    }
//
//    @Override
//    public void releaseInstance(ConstraintValidator<?, ?> instance) {
//    	
//        // Default factory will handle releasing its own instances
//        if (!(applicationContext.getBeanNamesForType(instance.getClass()).length > 0)) {
//            defaultFactory.releaseInstance(instance);
//        }
//    }


}
