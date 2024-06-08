package cc.unilock.shaderfixer.mixin;

import Reika.DragonAPI.IO.Shaders.ShaderProgram;
import Reika.DragonAPI.IO.Shaders.ShaderRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ShaderRegistry.class, remap = false)
public class ShaderRegistryMixin {
    @Inject(method = "createShader(LReika/DragonAPI/Base/DragonAPIMod;Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;LReika/DragonAPI/IO/Shaders/ShaderRegistry$ShaderDomain;)LReika/DragonAPI/IO/Shaders/ShaderProgram;", at = @At("HEAD"), cancellable = true)
    private static void createShader(CallbackInfoReturnable<ShaderProgram> cir) {
        cir.setReturnValue(null);
    }

    @Inject(method = "runShader(LReika/DragonAPI/IO/Shaders/ShaderProgram;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void runShader(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "completeShader()V", at = @At(value = "HEAD"), cancellable = true)
    private static void completeShader(CallbackInfo ci) {
        ci.cancel();
    }
}
