/*
 * Copyright 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtproject.typedarrays.server;

import org.gwtproject.core.shared.GwtIncompatible;
import org.gwtproject.typedarrays.shared.ArrayBuffer;
import org.gwtproject.typedarrays.shared.Uint32Array;

/**
 * Pure Java implementation of {@link Uint32Array}.
 */
@GwtIncompatible
public class Uint32ArrayImpl extends ArrayBufferViewImpl implements Uint32Array {

  /**
   * @param buffer
   * @param byteOffset
   * @param length
   */
  public Uint32ArrayImpl(ArrayBuffer buffer, int byteOffset, int length) {
    super(buffer, byteOffset, length * BYTES_PER_ELEMENT);
  }

  @Override
  public long get(int index) {
    long val = arrayBuf.getInt32(checkRange(index, BYTES_PER_ELEMENT), USE_LITTLE_ENDIAN);
    if (val < 0) {
      val += 0x100000000L;
    }
    return val;
  }

  @Override
  public double getAsDouble(int index) {
    return get(index);
  }

  @Override
  public int length() {
    return byteLength() / BYTES_PER_ELEMENT;
  }

  @Override
  public void set(double[] array) {
    set(array, 0);
  }

  @Override
  public void set(double[] array, int offset) {
    int len = array.length;
    if (offset + len > length()) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < len; ++i) {
      set(offset++, array[i]);
    }
  }

  @Override
  public void set(int index, double value) {
    set(index, (long) value);
  }

  @Override
  public void set(int index, long value) {
    arrayBuf.setInt32(checkRange(index, BYTES_PER_ELEMENT), (int) (value & 0xFFFFFFFF),
        USE_LITTLE_ENDIAN);
  }

  @Override
  public void set(long[] array) {
    set(array, 0);
  }

  @Override
  public void set(long[] array, int offset) {
    int len = array.length;
    if (offset + len > length()) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < len; ++i) {
      set(offset++, array[i]);
    }
  }

  @Override
  public void set(Uint32Array array) {
    set(array, 0);
  }

  @Override
  public void set(Uint32Array array, int offset) {
    int len = array.length();
    if (offset + len > length()) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < len; ++i) {
      set(offset++, array.get(i));
    }
  }

  @Override
  public Uint32Array subarray(int begin) {
    int count = (byteLength() - byteOffset()) / BYTES_PER_ELEMENT;
    return subarray(begin, count);
  }

  @Override
  public Uint32Array subarray(int begin, int end) {
    int count = (byteLength() - byteOffset()) / BYTES_PER_ELEMENT;
    if (begin < 0) {
      begin += count;
      if (begin < 0) {
        begin = 0;
      }
    } else if (begin > count) {
      begin = count;
    }
    if (end < 0) {
      end += count;
      if (end < 0) {
        end = 0;
      }
    } else if (end > count) {
      end = count;
    }
    if (end < begin) {
      end = begin;
    }
    return new Uint32ArrayImpl(arrayBuf, begin * BYTES_PER_ELEMENT, end * BYTES_PER_ELEMENT);
  }
}
