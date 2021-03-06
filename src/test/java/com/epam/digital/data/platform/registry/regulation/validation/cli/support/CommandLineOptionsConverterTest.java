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

package com.epam.digital.data.platform.registry.regulation.validation.cli.support;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import com.google.common.base.Joiner;
import lombok.SneakyThrows;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandLineOptionsConverterTest {

  private CommandLineOptionsConverter commandLineOptionsConverter;

  @BeforeEach
  public void setUp() {
    this.commandLineOptionsConverter = new CommandLineOptionsConverter();
  }

  @Test
  public void shouldConvertAllOptionsToFiles() {
    var regulationFiles = commandLineOptionsConverter.convert(allOptionsPrefilled());

    assertThat(regulationFiles.getBpTrembitaFiles(), is(not(empty())));
    assertThat(regulationFiles.getGlobalVarsFiles(), is(not(empty())));
    assertThat(regulationFiles.getBpAuthFiles(), is(not(empty())));
    assertThat(regulationFiles.getRolesFiles(), is(not(empty())));
    assertThat(regulationFiles.getBpmnFiles(), is(not(empty())));
    assertThat(regulationFiles.getDmnFiles(), is(not(empty())));
    assertThat(regulationFiles.getFormFiles(), is(not(empty())));
    assertThat(regulationFiles.getSettingsFiles(), is(not(empty())));
    assertThat(regulationFiles.getLiquibaseFiles(), is(not(empty())));
  }

  @SneakyThrows
  private Options allOptionsPrefilled() {
    return new CommandLineArgsParser().parse(regulationFiles());
  }

  private String[] regulationFiles() {
    return new String[]{
        argOf(CommandLineArg.GLOBAL_VARS, "global-vars.yml"),
        argOf(CommandLineArg.BP_AUTH, "bp-auth.yml"),
        argOf(CommandLineArg.BP_TREMBITA, "bp-trembita.yml"),
        argOf(CommandLineArg.ROLES, "roles.yml"),
        argOf(CommandLineArg.BPMN, "process.bpmn"),
        argOf(CommandLineArg.DMN, "rule.dmn"),
        argOf(CommandLineArg.FORMS, "ui-form.json"),
        argOf(CommandLineArg.LIQUIBASE, "test-main-liquibase.xml"),
        argOf(CommandLineArg.SETTINGS, "settings.yaml")
    };
  }

  private String argOf(CommandLineArg arg, String... resourcePath) {
    return String.format("--%s=%s", arg.getArgOptionName(), Joiner.on(',').join(resourcePath));
  }
}