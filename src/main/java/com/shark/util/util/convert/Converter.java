package com.shark.util.util.convert;

import java.util.List;

public interface Converter<Input, Output> {

    public List<Output> convert(Input input, Output empty);

}
