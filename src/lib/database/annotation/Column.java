package lib.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	String value() default "";

	boolean NULL() default false;

	String DEFAULT() default "";

	String DATA_TYPE() default "";

}
