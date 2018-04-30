package com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer;

public interface IElementSerializer<E> {

  String getTitle();

  String serialize(E element);

}
