/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.model.property.validator.property;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.IdentifierValueElement;

public class TextUnderlinePositionValidator implements ValueValidator {

  private static final ValueElementValidator IDENTIFIER_VALIDATOR = new IdentifierValidator("auto", "under", "left", "right");

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() == 1) {
      return IDENTIFIER_VALIDATOR.isValid(valueElements.get(0));
    }
    if (value.getNumberOfValueElements() == 2) {
      int count = 0;
      for (CssValueElement valueElement : valueElements) {
        if (!(valueElement instanceof IdentifierValueElement)) {
          return false;
        }
        if ("under".equals(((IdentifierValueElement) valueElement).getName())) {
          // Do nothing
        } else if ("right".equals(((IdentifierValueElement) valueElement).getName())) {
          count++;
        } else if ("left".equals(((IdentifierValueElement) valueElement).getName())) {
          count++;
        } else {
          return false;
        }
      }
      return count < 2;
    }
    return false;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "auto | [ under || [ left | right ] ]";
  }

}
