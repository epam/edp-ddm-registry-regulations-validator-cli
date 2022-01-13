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

package com.epam.digital.data.platform.registry.regulation.validation.cli.validator.bpmn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import com.epam.digital.data.platform.registry.regulation.validation.cli.model.RegulationFileType;
import com.epam.digital.data.platform.registry.regulation.validation.cli.validator.ValidationContext;
import java.io.File;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class BpmnFileValidatorTest {

  private BpmnFileValidator validator;

  @BeforeEach
  public void setUp() {
    this.validator = new BpmnFileValidator();
  }

  @Test
  public void shouldPassProcessValidation() {
    var processFile = getFileFromClasspath("registry-regulation/correct/process.bpmn");

    var errors = this.validator.validate(processFile, ValidationContext.of(RegulationFileType.BPMN));

    assertThat(errors, is(empty()));
  }

  @Test
  public void shouldFailProcessParsingDueToMissingProcessId() {
    var brokenProcessFile = getFileFromClasspath("registry-regulation/broken/process-broken.bpmn");

    var errors = this.validator.validate(brokenProcessFile, ValidationContext.of(RegulationFileType.BPMN));

    assertThat(errors, is(not(empty())));
  }

  @Test
  public void shouldFailDueToMissingProcessName() {
    var brokenProcessFile = getFileFromClasspath("registry-regulation/broken/process-empty-name.bpmn");

    var errors = this.validator.validate(brokenProcessFile, ValidationContext.of(RegulationFileType.BPMN));

    assertThat(errors, is(not(empty())));
  }

  @Test
  public void shouldFailDueToProcessModelValidationIssue() {
    var processFile = getFileFromClasspath("registry-regulation/correct/process.bpmn");
    var bpmnModel = Bpmn.readModelFromFile(processFile);

    try (MockedStatic<Bpmn> bpmn = Mockito.mockStatic(Bpmn.class)) {
      bpmn.when(() -> Bpmn.readModelFromFile(processFile))
          .thenReturn(bpmnModel);
      bpmn.when(() -> Bpmn.validateModel(bpmnModel))
          .thenThrow(new ModelValidationException());

      var errors = this.validator.validate(processFile, ValidationContext.of(RegulationFileType.BPMN));

      assertThat(errors, is(not(empty())));
    }
  }

  @Test
  public void shouldFailDueToUnsupportedFileExtension() {
    File missingFile = new File("missing-file.bpmn");

    var errors = this.validator.validate(missingFile, ValidationContext.of(RegulationFileType.BPMN));

    assertThat(errors, is(not(empty())));
  }

  @Test
  public void shouldFailDueToMissingFile() {
    File unsupportedFile = new File("unsupported-file.xml");

    var errors = this.validator.validate(unsupportedFile, ValidationContext.of(RegulationFileType.BPMN));

    assertThat(errors, is(not(empty())));
  }

  private File getFileFromClasspath(String filePath) {
    var classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource(filePath).getFile());
  }
}