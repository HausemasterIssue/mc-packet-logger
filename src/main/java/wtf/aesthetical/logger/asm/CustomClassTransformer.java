package wtf.aesthetical.logger.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import wtf.aesthetical.logger.asm.api.Injection;
import wtf.aesthetical.logger.asm.api.Mixin;
import wtf.aesthetical.logger.asm.api.MixinManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        Mixin mixin = MixinManager.instance.getMixin(name);
        if (mixin != null) {
            ClassNode classNode = toClassNode(basicClass);

            for (Method method : mixin.getClass().getDeclaredMethods()) {
                System.out.println(method.getName());
                if (!method.isAnnotationPresent(Injection.class)) {
                    continue;
                }

                Injection injection = method.getDeclaredAnnotation(Injection.class);

                String full = mixin.isNotch() ? injection.notchDesc() : injection.mcpDesc();
                int descStart = full.indexOf("(");

                String methodName = full.substring(0, descStart);
                String desc = full.substring(descStart);

                MethodNode methodNode = getMethod(classNode, methodName, desc);
                if (methodNode != null) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    try {
                        method.invoke(mixin, methodNode);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.printf("Successfully transformed class %s\n", name);
            return toTransformed(classNode);
        }

        return basicClass;
    }

    private ClassNode toClassNode(byte[] clazz) {
        ClassReader reader = new ClassReader(clazz);
        ClassNode classNode = new ClassNode();

        reader.accept(classNode, 0);
        return classNode;
    }

    private MethodNode getMethod(ClassNode classNode, String name, String desc) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(name) && methodNode.desc.equals(desc)) {
                return methodNode;
            }
        }

        return null;
    }

    private byte[] toTransformed(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
