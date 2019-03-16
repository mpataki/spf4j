/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.io.csv;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Char separated value file Reader.
 * @author zoly
 */
public interface CsvReader {

  enum TokenType {
     ELEMENT, END_ROW, END_DOCUMENT
  }

  /**
   * read next CSV element, and return its type.
   *
   * @return return CSV element type.
   * @throws IOException exception is something goes wrong.
   */
  @Nonnull
  TokenType next() throws IOException, CsvParseException;

  /**
   * @return the currently parsed token type,  null if no current token is available (next has never been called)
   * @throws IOException
   * @throws CsvParseException
   */
  @Nullable
  TokenType current();

  /**
   * the CSV element string. the underlying instance is reused, so you will need to make a copy of this if planning to
   * use it.
   *
   * @return CharSequence representing a csv cell.
   */
  CharSequence getElement();

  default int skipRow() throws IOException, CsvParseException {
    int skipped = 0;
    TokenType current = current();
    if (current == null) { // beginning of file.
      next();
    }
    while ((current = current()) != CsvReader.TokenType.END_ROW
            && current != CsvReader.TokenType.END_DOCUMENT) {
      next();
      skipped++;
    }
    if (current != CsvReader.TokenType.END_DOCUMENT) {
      next();
    }
    return skipped;
  }

  default void readRow(final Consumer<CharSequence> consumer) throws IOException, CsvParseException {
    TokenType current = current();
    if (current == null) { // beginning of file.
      next();
    }
    while ((current = current()) != CsvReader.TokenType.END_ROW
            && current != CsvReader.TokenType.END_DOCUMENT) {
      consumer.accept(getElement());
      next();
    }
    if (current != CsvReader.TokenType.END_DOCUMENT) {
      next();
    }
  }


  default CsvReader toReader(final Iterator<? extends CharSequence> it) {
    return new IterableCsvReader(it);
  }
}
