package cc.unilock.shaderfixer.mixin;

import Reika.DragonAPI.IO.Shaders.ShaderRegistry;
import org.lwjgl.opengl.ARBShaderObjects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ShaderRegistry.class, remap = false)
public class ShaderRegistryMixin {
    @Redirect(method = "completeShader", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glUseProgram(I)V"))
    private static void completeShader$glUseProgram(int program) {
        ARBShaderObjects.glUseProgramObjectARB(program);
    }

    @Redirect(method = "constructShader", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glCreateShader(I)I"))
    private static int constructShader$glCreateShader(int type) {
        return ARBShaderObjects.glCreateShaderObjectARB(type);
    }

    @Redirect(method = "constructShader", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/CharSequence;)V"))
    private static void constructShader$glShaderSource(int shader, CharSequence string) {
        ARBShaderObjects.glShaderSourceARB(shader, string);
    }

    @Redirect(method = "constructShader", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glCompileShader(I)V"))
    private static void constructShader$glCompileShader(int shader) {
        ARBShaderObjects.glCompileShaderARB(shader);
    }

    @Redirect(method = "constructShader", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glGetShaderi(II)I"))
    private static int constructShader$glGetShaderi(int shader, int pname) {
        return ARBShaderObjects.glGetObjectParameteriARB(shader, pname);
    }

    @Redirect(method = "parseError", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glGetShaderInfoLog(II)Ljava/lang/String;"))
    private static String parseError$glGetShaderInfoLog(int shader, int maxLength) {
        return ARBShaderObjects.glGetInfoLogARB(shader, maxLength);
    }
}
