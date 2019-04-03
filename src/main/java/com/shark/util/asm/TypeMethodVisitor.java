package com.shark.util.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/13 0013
 */
public abstract class TypeMethodVisitor extends MethodVisitor implements Opcodes {

	public TypeMethodVisitor(int api) {
		super(api);
	}

	public TypeMethodVisitor(int api, MethodVisitor mv) {
		super(api, mv);
	}

	@Override
	public void visitCode() {
		preMethod();
		super.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode==RETURN){
			postMethod();
		}
		super.visitInsn(opcode);
	}

	public void visitMethodInsn(int opcode, Class c, String methodName, boolean itf) {
		try {
			String owner = Type.getType(c).getInternalName();
			Method method = c.getDeclaredMethod(methodName);
			String name = method.getName();
			String desc = Type.getType(method).getDescriptor();
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public abstract void preMethod();

	public abstract void postMethod();

	public MethodVisitor setMv(MethodVisitor mv) throws NoSuchFieldException, IllegalAccessException {
		this.mv=mv;
		return this;
	}
}
