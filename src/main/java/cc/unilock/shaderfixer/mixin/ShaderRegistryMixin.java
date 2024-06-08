package cc.unilock.shaderfixer.mixin;

import Reika.DragonAPI.IO.Shaders.ShaderRegistry;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ShaderRegistry.class, remap = false)
public class ShaderRegistryMixin {
    @Redirect(method = "createShader(LReika/DragonAPI/Base/DragonAPIMod;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;LReika/DragonAPI/IO/Shaders/ShaderRegistry$ShaderDomain;)LReika/DragonAPI/IO/Shaders/ShaderProgram;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/OpenGlHelper;shadersSupported:Z", opcode = Opcodes.GETSTATIC))
    private static boolean createShader$getShadersSupported() {
        return false;
    }

    @Redirect(method = "runShader(LReika/DragonAPI/IO/Shaders/ShaderProgram;)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/OpenGlHelper;shadersSupported:Z", opcode = Opcodes.GETSTATIC))
    private static boolean runShader$getShadersSupported() {
        return false;
    }

    @Redirect(method = "completeShader()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/OpenGlHelper;shadersSupported:Z", opcode = Opcodes.GETSTATIC))
    private static boolean completeShader$getShadersSupported() {
        return false;
    }
}
