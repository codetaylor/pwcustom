package com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile;

import com.sudoplay.mc.pwcustom.modules.dumpt.line.LineWriter;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.line.ILineWriter;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;

import java.util.Collections;
import java.util.List;

public abstract class ExportProfileBase<E>
    implements IExportProfile {

  private final List<IElementSerializer<E>> elementSerializerList;

  public ExportProfileBase(List<IElementSerializer<E>> elementSerializerList) {

    this.elementSerializerList = Collections.unmodifiableList(elementSerializerList);
  }

  @Override
  public String[] getHeaders() {

    String[] headers = new String[this.elementSerializerList.size()];

    for (int i = 0; i < this.elementSerializerList.size(); i++) {
      headers[i] = this.elementSerializerList.get(i).getTitle();
    }

    return headers;
  }

  /* package */ List<IElementSerializer<E>> getElementSerializerList() {

    return this.elementSerializerList;
  }

  protected ILineWriter<E> getLineWriter() {

    return new LineWriter<>(this.getElementSerializerList());
  }
}
