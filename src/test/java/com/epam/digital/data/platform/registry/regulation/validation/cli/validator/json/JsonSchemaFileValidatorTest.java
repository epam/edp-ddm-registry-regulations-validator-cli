/*
 * Copyright 2021 EPAM Systems.
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

package com.epam.digital.data.platform.registry.regulation.validation.cli.validator.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import com.epam.digital.data.platform.registry.regulation.validation.cli.model.RegulationFileType;
import com.epam.digital.data.platform.registry.regulation.validation.cli.validator.RegulationValidator;
import com.epam.digital.data.platform.registry.regulation.validation.cli.validator.ValidationContext;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class JsonSchemaFileValidatorTest {

  private final ResourceLoader resourceLoader = new ClassRelativeResourceLoader(getClass());

  private RegulationValidator<File> validator;

  @Before
  public void setUp() {
    this.validator = new JsonSchemaFileValidator("classpath:schema/bp-auth-schema.json", resourceLoader, new YAMLMapper());
  }

  @Test
  public void shouldPassSchemaValidation() {
    var processFile = getFileFromClasspath("registry-regulation/correct/bp-auth.yml");

    var errors = validator.validate(processFile, ValidationContext.of(RegulationFileType.BP_AUTH));

    assertThat(errors, is(empty()));
  }

  @Test
  public void shouldFailSchemaValidation() {
    var processFile = getFileFromClasspath("registry-regulation/broken/bp-auth-broken.yml");

    var errors = validator.validate(processFile, ValidationContext.of(RegulationFileType.BP_AUTH));

    assertThat(errors, is(not(empty())));
  }

  private File getFileFromClasspath(String filePath) {
    var classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource(filePath).getFile());
  }
}