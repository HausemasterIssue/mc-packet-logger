package wtf.aesthetical.logger.asm.api;

import org.apache.logging.log4j.LogManager;
import wtf.aesthetical.logger.asm.mixins.MixinNetworkManager;

import java.util.LinkedList;
import java.util.List;

public class MixinManager {
    public static MixinManager instance;

    private final List<Mixin> mixins = new LinkedList<>();

    private boolean notch = false;

    public MixinManager() {
        addMixin(MixinNetworkManager.class);

        LogManager.getLogger("MixinManager").info("Loaded {} mixins.", mixins.size());
    }

    public void addMixin(Class<? extends Mixin> clazz) {
        try {
            Mixin instance = clazz.getConstructor().newInstance();
            if (instance.isLoadedSuccessfully()) {
                mixins.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Mixin getMixin(String name) {
        for (Mixin mixin : mixins) {
            String className = isNotch() ? mixin.getNotchClassName() : mixin.getMcpClassName();
            if (className.equals(name)) {
                return mixin;
            }
        }

        return null;
    }

    public void setNotch(boolean notch) {
        this.notch = notch;
    }

    public boolean isNotch() {
        return notch;
    }

    public static void setInstance() {
        if (instance == null) {
            instance = new MixinManager();
        }
    }
}
