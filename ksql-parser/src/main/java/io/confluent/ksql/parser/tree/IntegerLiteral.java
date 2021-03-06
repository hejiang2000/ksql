/**
 * Copyright 2017 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.confluent.ksql.parser.tree;

import java.util.Objects;
import java.util.Optional;

import io.confluent.ksql.parser.ParsingException;

import static java.util.Objects.requireNonNull;

public class IntegerLiteral
    extends Literal {

  private final int value;

  public IntegerLiteral(NodeLocation location, String value) {
    this(Optional.of(location), value);
  }

  private IntegerLiteral(Optional<NodeLocation> location, String value) {
    super(location);
    requireNonNull(value, "value is null");
    try {
      this.value = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new ParsingException("Invalid numeric literal: " + value);
    }
  }

  public int getValue() {
    return value;
  }

  @Override
  public <R, C> R accept(AstVisitor<R, C> visitor, C context) {
    return visitor.visitIntegerLiteral(this, context);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final IntegerLiteral that = (IntegerLiteral) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
