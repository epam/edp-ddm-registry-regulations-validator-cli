/*
 * Copyright 2022 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.registry.regulation.validation.cli.validator;

import com.epam.digital.data.platform.registry.regulation.validation.cli.model.RegulationFiles;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class GlobalCompositeRegulationFilesValidator implements RegulationValidator<RegulationFiles> {
  @Singular
  private List<RegulationValidator<RegulationFiles>> validators;

  @Override
  public Set<ValidationError> validate(RegulationFiles regulationFiles, ValidationContext validationContext) {
    for (var validator : validators) {
      var errors = validator.validate(regulationFiles, validationContext);
      if (errors.isEmpty()) {
        continue;
      }
      return errors;
    }
    return Collections.emptySet();
  }
}