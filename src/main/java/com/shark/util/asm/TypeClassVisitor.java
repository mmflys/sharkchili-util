package com.shark.util.asm;

import com.google.common.collect.Maps;
import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/13 0013
 */
public class TypeClassVisitor extends ClassVisitor implements Opcodes {

	private Map<Method, TypeMethodVisitor> enhance;
	private Map<Method,Type> methodType;
	private ClassReader classReader;
	private ClassWriter classWriter;

	public TypeClassVisitor(int api, ClassVisitor cv, ClassReader classReader, ClassWriter classWriter) {
		super(api, cv);
		this.classReader = classReader;
		this.classWriter = classWriter;
		enhance = Maps.newHashMap();
		methodType =Maps.newHashMap();
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		// 过滤方法
		boolean cvNotNull=cv!=null;
		for (Method method : methodType.keySet()) {
			boolean nameEqual=method.getName().equals(name);
			boolean descEqual=methodType.get(method).getDescriptor().equals(desc);
			if (cvNotNull&&nameEqual&&descEqual) {
				MethodVisitor mvOrigin = cv.visitMethod(access, name, desc, signature, exceptions);
				try {
					return enhance.get(method).setMv(mvOrigin);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (cvNotNull){
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
		return null;
	}

	public TypeClassVisitor putEnhance(Method method,TypeMethodVisitor typeMethodVisitor){
		this.enhance.put(method, typeMethodVisitor);
		addType(method);
		return this;
	}

	public TypeClassVisitor removeEnhance(Method method){
		this.enhance.remove(method);
		this.methodType.remove(method);
		return this;
	}

	private void parseMetho(){
		for (Method method : this.enhance.keySet()) {
			addType(method);
		}
	}

	private void addType(Method method){
		Type type=Type.getType(method);
		this.methodType.put(method, type);
	}

	public void accept(){
		this.classReader.accept(this, this.api);
	}

	public byte[] classByteData(){
		return this.classWriter.toByteArray();
	}

	public static TypeClassVisitor asm4(ClassVisitor cv,ClassReader cr,ClassWriter cw){
		return new TypeClassVisitor(Opcodes.ASM4,cv,cr,cw);
	}

	public static TypeClassVisitor asm5(ClassVisitor cv,ClassReader cr,ClassWriter cw){
		return new TypeClassVisitor(Opcodes.ASM5,cv,cr,cw);
	}

	public static TypeClassVisitor asm4(Class c) throws IOException {
		ClassReader cr = new ClassReader(c.getName());
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		return asm4(cw,cr,cw);
	}

	public static TypeClassVisitor asm5(Class c) throws IOException {
		ClassReader cr = new ClassReader(c.getName());
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		return asm5(cw,cr,cw);
	}
}
