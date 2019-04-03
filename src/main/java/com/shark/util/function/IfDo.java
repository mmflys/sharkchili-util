package com.shark.util.function;

import java.util.function.Consumer;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/6 0006
 */
@FunctionalInterface
public interface IfDo<P,D> {
	public void acceptAndDo(P p, Consumer<D> consumer);
}
