package com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile;

import java.io.IOException;
import java.io.Writer;

public interface IExportProfile {

  String getFilename();

  String[] getHeaders();

  int write(Writer writer) throws IOException;
}
