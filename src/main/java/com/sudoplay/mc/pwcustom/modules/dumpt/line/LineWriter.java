package com.sudoplay.mc.pwcustom.modules.dumpt.line;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.line.ILineWriter;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class LineWriter<E>
    implements ILineWriter<E> {

  private final List<IElementSerializer<E>> elementSerializerList;

  public LineWriter(List<IElementSerializer<E>> elementSerializerList) {

    this.elementSerializerList = elementSerializerList;
  }

  @Override
  public void write(Writer writer, E element) throws IOException {

    String[] elements = new String[this.elementSerializerList.size()];

    for (int i = 0; i < this.elementSerializerList.size(); i++) {
      elements[i] = this.elementSerializerList.get(i).serialize(element);
    }

    writer.write(String.join(",", elements));
    writer.write(System.lineSeparator());
  }
}
