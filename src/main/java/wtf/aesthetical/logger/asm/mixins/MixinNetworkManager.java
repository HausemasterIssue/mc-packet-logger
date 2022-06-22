package wtf.aesthetical.logger.asm.mixins;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import wtf.aesthetical.logger.asm.api.Injection;
import wtf.aesthetical.logger.asm.api.Mixin;
import wtf.aesthetical.logger.asm.hooks.HookNetworkManager;

import static org.objectweb.asm.Opcodes.*;

public class MixinNetworkManager extends Mixin {
    private static final Class<HookNetworkManager> HOOK_CLASS = HookNetworkManager.class;

    public MixinNetworkManager() {
        super("net.minecraft.network.NetworkManager", "gw");
    }

    // MD: gw/a (Lht;)V net/minecraft/network/NetworkManager/func_179290_a (Lnet/minecraft/network/Packet;)V
    // This above might not be right, but it was the closest to the decompiled one
    @Injection(mcpDesc = "sendPacket(Lnet/minecraft/network/Packet;)V", notchDesc = "a(Lht;)V")
    private void sendPacket(MethodNode node) {

        // create instructions list
        InsnList instructions = new InsnList();

        // load first variable of the sendPacket method into our INVOKESTATIC
        instructions.add(new VarInsnNode(ALOAD, 1));

        // specify it's a client packet
        instructions.add(new FieldInsnNode(
                GETSTATIC,
                "wtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType",
                "CLIENT",
                "Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;"));

        // invoke HookNetworkManager#logPacket
        instructions.add(new MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(HOOK_CLASS),
                "logPacket",
                isNotch() ?
                        "(Lht;Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;)V" :
                        "(Lnet/minecraft/network/Packet;Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;)V",
                false));

        // add our instructions into the method node
        node.instructions.insert(instructions);
    }

    // MD: gw/a (Lio/netty/channel/ChannelHandlerContext;Lht;)V net/minecraft/network/NetworkManager/channelRead0 (Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V
    @Injection(
            mcpDesc = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V",
            notchDesc = "a(Lio/netty/channel/ChannelHandlerContext;Lht;)V"
    )
    private void channelRead0(MethodNode node) {
        // create instructions list
        InsnList instructions = new InsnList();

        // load second variable of the channelRead0 method into our INVOKESTATIC
        instructions.add(new VarInsnNode(ALOAD, 2));

        // specify it's a server packet
        instructions.add(new FieldInsnNode(
                GETSTATIC,
                "wtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType",
                "SERVER",
                "Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;"));

        // invoke HookNetworkManager#logPacket
        instructions.add(new MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(HOOK_CLASS),
                "logPacket",
                isNotch() ?
                        "(Lht;Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;)V" :
                        "(Lnet/minecraft/network/Packet;Lwtf/aesthetical/logger/asm/hooks/HookNetworkManager$PacketType;)V",
                false));

        // add our instructions into the method node
        node.instructions.insert(instructions);
    }
}
