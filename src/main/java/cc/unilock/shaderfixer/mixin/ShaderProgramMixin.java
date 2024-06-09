package cc.unilock.shaderfixer.mixin;

import Reika.DragonAPI.IO.Shaders.ShaderProgram;
import Reika.DragonAPI.Instantiable.Math.Vec4;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Mixin(value = ShaderProgram.class, remap = false)
public class ShaderProgramMixin {
    @Shadow
    private int programID;

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glDeleteProgram(I)V"))
    private void load$glDeleteProgram(int program) {
        ARBShaderObjects.glDeleteObjectARB(program);
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glUseProgram(I)V"))
    private void run$glUseProgram(int program) {
        ARBShaderObjects.glUseProgramObjectARB(program);
    }

    @Redirect(method = "applyFocus", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glGetUniformLocation(ILjava/lang/CharSequence;)I"))
    private int applyFocus$glGetUniformLocation(int program, CharSequence name) {
        return ARBShaderObjects.glGetUniformLocationARB(program, name);
    }

    @Redirect(method = "applyFocus", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glUniform3(ILjava/nio/FloatBuffer;)V"))
    private void applyFocus$glUniform3(int location, FloatBuffer values) {
        ARBShaderObjects.glUniform3ARB(location, values);
    }

    @Redirect(method = "applyMatrices", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glGetUniformLocation(ILjava/lang/CharSequence;)I"))
    private int applyMatrices$glGetUniformLocation(int program, CharSequence name) {
        return ARBShaderObjects.glGetUniformLocationARB(program, name);
    }

    @Redirect(method = "applyMatrices", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glUniformMatrix4(IZLjava/nio/FloatBuffer;)V"))
    private void applyMatrices$glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
        ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
    }

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glCreateProgram()I"))
    private int register$glCreateProgram() {
        return ARBShaderObjects.glCreateProgramObjectARB();
    }

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glAttachShader(II)V"))
    private void register$glAttachShader(int program, int shader) {
        ARBShaderObjects.glAttachObjectARB(program, shader);
    }

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glLinkProgram(I)V"))
    private void register$glLinkProgram(int program) {
        ARBShaderObjects.glLinkProgramARB(program);
    }

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glValidateProgram(I)V"))
    private void register$glValidateProgram(int program) {
        ARBShaderObjects.glValidateProgramARB(program);
    }

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glGetProgrami(II)I"))
    private int register$glGetProgrami(int program, int pname) {
        return ARBShaderObjects.glGetObjectParameteriARB(program, pname);
    }

    /**
     * @author unilock
     * @reason asdf
     */
    @Overwrite
    private void applyField(String f, Object val) {
        int loc = ARBShaderObjects.glGetUniformLocationARB(programID, f);
        if (val instanceof Integer) {
            ARBShaderObjects.glUniform1iARB(loc, (int)val);
        }
        else if (val instanceof Float) {
            ARBShaderObjects.glUniform1fARB(loc, (float)val);
        }
        else if (val instanceof Double) {
            ARBShaderObjects.glUniform1fARB(loc, ((Double)val).floatValue());
        }
        else if (val instanceof Vec3) {
            Vec3 vec = (Vec3)val;
            ARBShaderObjects.glUniform3fARB(loc, (float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
        }
        else if (val instanceof Vec4) {
            Vec4 vec = (Vec4)val;
            ARBShaderObjects.glUniform4fARB(loc, vec.a, vec.b, vec.c, vec.d);
        }
        else if (val instanceof int[]) {
            int[] data = (int[])val;
            IntBuffer buf = BufferUtils.createIntBuffer(data.length);
            buf.put(data);
            buf.rewind();
            ARBShaderObjects.glUniform1ARB(loc, buf);
        }
        else if (val instanceof float[]) {
            float[] data = (float[])val;
            FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
            buf.put(data);
            buf.rewind();
            ARBShaderObjects.glUniform1ARB(loc, buf);
        }
        else if (val instanceof Vec3[]) {
            Vec3[] data = (Vec3[])val;
            FloatBuffer buf = BufferUtils.createFloatBuffer(data.length*3);
            for (Vec3 vec : data) {
                buf.put(vec == null ? 0 : (float)vec.xCoord);
                buf.put(vec == null ? 0 : (float)vec.yCoord);
                buf.put(vec == null ? 0 : (float)vec.zCoord);
            }
            buf.rewind();
            ARBShaderObjects.glUniform3ARB(loc, buf);
        }
        else if (val instanceof Vec4[]) {
            Vec4[] data = (Vec4[])val;
            FloatBuffer buf = BufferUtils.createFloatBuffer(data.length*4);
            for (Vec4 vec : data) {
                buf.put(vec == null ? 0 : vec.a);
                buf.put(vec == null ? 0 : vec.b);
                buf.put(vec == null ? 0 : vec.c);
                buf.put(vec == null ? 0 : vec.d);
            }
            buf.rewind();
            ARBShaderObjects.glUniform4ARB(loc, buf);
        }
    }
}
