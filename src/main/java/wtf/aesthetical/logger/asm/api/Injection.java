package wtf.aesthetical.logger.asm.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Injection {
    /**
     * The MCP descriptor
     *
     * For example, if we wanted Minecraft#isSinglePlayer it would be in MCP:
     * isSinglePlayer()Z
     */
    String mcpDesc() default "";

    /**
     * The Notch descriptor
     *
     * I don't know how to do this yet, but I'll figure it out
     *
     * @return
     */
    String notchDesc() default "";
}
