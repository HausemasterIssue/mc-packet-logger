package wtf.aesthetical.logger.asm.api;

public class Mixin {
    private final String mcpClassName, notchClassName;

    public Mixin(String mcpClassName, String notchClassName) {
        this.mcpClassName = mcpClassName;
        this.notchClassName = notchClassName;
    }

    public boolean isLoadedSuccessfully() {
        return mcpClassName != null && notchClassName != null;
    }

    public String getMcpClassName() {
        return mcpClassName;
    }

    public String getNotchClassName() {
        return notchClassName;
    }

    public boolean isNotch() {
        return MixinManager.instance.isNotch();
    }
}
