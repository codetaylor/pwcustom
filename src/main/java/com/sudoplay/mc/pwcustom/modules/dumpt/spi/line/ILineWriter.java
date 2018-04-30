package com.sudoplay.mc.pwcustom.modules.dumpt.spi.line;

import java.io.IOException;
import java.io.Writer;

public interface ILineWriter<E> {

  void write(Writer writer, E element) throws IOException;

}
