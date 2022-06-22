package wtf.aesthetical.logger.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.apache.logging.log4j.LogManager;
import wtf.aesthetical.logger.asm.api.MixinManager;

import javax.annotation.Nullable;
import java.util.Map;

@Name("LoadingPlugin")
@MCVersion("1.12.2")
public class LoadingPlugin implements IFMLLoadingPlugin {
    public LoadingPlugin() {
        MixinManager.setInstance();
        LogManager.getLogger("LoadingPlugin").info("Loading transformer...");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { CustomClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        boolean notch = (boolean) data.getOrDefault("runtimeDeobfuscationEnabled", true);

        MixinManager.setInstance();
        MixinManager.instance.setNotch(notch);
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
