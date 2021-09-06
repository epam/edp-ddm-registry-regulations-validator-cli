package com.epam.digital.data.platform.registry.regulation.validation.cli.validator;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.digital.data.platform.registry.regulation.validation.cli.model.RegulationFileType;
import com.epam.digital.data.platform.registry.regulation.validation.cli.model.RegulationFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Test;

public class RegulationFilesValidatorTest {

  @Test
  @SuppressWarnings("unchecked")
  public void shouldDelegateValidationForAllRegulationFileTypes() {
    RegulationValidator<File> bpAuthFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> bpTrembitaFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> rolesFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> globalVarsFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> formsFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> bpmnFileValidator = mock(RegulationValidator.class);
    RegulationValidator<File> dmnFileValidator = mock(RegulationValidator.class);

    var validators = Map.of(
        RegulationFileType.BP_AUTH, bpAuthFileValidator,
        RegulationFileType.BP_TREMBITA, bpTrembitaFileValidator,
        RegulationFileType.ROLES, rolesFileValidator,
        RegulationFileType.GLOBAL_VARS, globalVarsFileValidator,
        RegulationFileType.FORMS, formsFileValidator,
        RegulationFileType.BPMN, bpmnFileValidator,
        RegulationFileType.DMN, dmnFileValidator
    );

    var regulationFilesValidator = new RegulationFilesValidator(validators);

    var regulationFiles = RegulationFiles.builder()
        .bpAuthFiles(singleFile())
        .bpTrembitaFiles(singleFile())
        .rolesFiles(singleFile())
        .globalVarsFiles(singleFile())
        .formFiles(singleFile())
        .bpmnFiles(singleFile())
        .dmnFiles(singleFile())
        .build();

    regulationFilesValidator.validate(regulationFiles, ValidationContext.empty());

    verify(bpAuthFileValidator, times(1)).validate(any(), any());
    verify(bpTrembitaFileValidator, times(1)).validate(any(), any());
    verify(rolesFileValidator, times(1)).validate(any(), any());
    verify(globalVarsFileValidator, times(1)).validate(any(), any());
    verify(formsFileValidator, times(1)).validate(any(), any());
    verify(bpmnFileValidator, times(1)).validate(any(), any());
    verify(dmnFileValidator, times(1)).validate(any(), any());
  }

  private ArrayList<File> singleFile() {
    return newArrayList(new File("no-file"));
  }
}