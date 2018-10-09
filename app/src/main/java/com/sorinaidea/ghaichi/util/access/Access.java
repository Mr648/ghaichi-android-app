package com.sorinaidea.ghaichi.util.access;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface Access {
      String validate();
}
