package edu.sjtu.party.web.controller.annotation;


import edu.sjtu.party.model.metadata.Power;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPriviledge {

    Power[] type() default Power.NONE;

}
