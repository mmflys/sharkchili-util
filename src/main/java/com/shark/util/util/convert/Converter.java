package com.shark.util.util.convert;

import java.util.List;

public interface Converter<Input, Output> {

    /**
     * Convert a instance that type is Input to a list of Output.
     *
     * @param input Input instance.
     * @param empty Output instance, only to provide type.
     * @return A list of Output
     */
    public List<Output> convert(Input input, Output empty);

    /**
     * Convert a array of Output to a object of Input
     *
     * @param outputs A array of Output
     * @return A instance of Input
     */
    public Input convert(Output... outputs);

}
