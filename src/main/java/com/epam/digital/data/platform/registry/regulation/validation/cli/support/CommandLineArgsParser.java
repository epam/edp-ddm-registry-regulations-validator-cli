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

package com.epam.digital.data.platform.registry.regulation.validation.cli.support;

import java.util.Arrays;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineArgsParser {

  private static final String JAR_NAME = "registry-regulations-validator-cli";

  private final CommandLineParser parser;
  private final HelpFormatter helpFormatter;

  public CommandLineArgsParser() {
    this.parser = new DefaultParser();
    this.helpFormatter = newHelpFormatter();
  }

  public Options parse(String... args) throws ParseException {
    var commandLine = parser.parse(commandLineOptions(), args);
    var options = new Options();
    Arrays.stream(commandLine.getOptions()).forEach(options::addOption);
    return options;
  }

  public void printHelp() {
    this.helpFormatter.printHelp(
        String.format("java -jar %s.jar", JAR_NAME),
        "Options:",
        commandLineOptions(),
        "Exit codes: 0 (success), 1 (system error), 10 (validation failure)",
        true);
  }

  private HelpFormatter newHelpFormatter() {
    var formatter = new HelpFormatter();
    formatter.setSyntaxPrefix("Usage: ");
    formatter.setLongOptSeparator("=");
    return formatter;
  }

  private Options commandLineOptions() {
    var options = new Options();

    options.addOption(Option.builder()
        .longOpt("help")
        .desc("Help on utility usage")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.BP_AUTH.getArgOptionName())
        .numberOfArgs(3)
        .valueSeparator(',')
        .desc("BP authorization regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.BP_TREMBITA.getArgOptionName())
        .hasArgs()
        .numberOfArgs(1)
        .desc("BP Trembita configuration regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.ROLES.getArgOptionName())
        .numberOfArgs(2)
        .valueSeparator(',')
        .desc("Authorization roles regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.BPMN.getArgOptionName())
        .hasArgs()
        .valueSeparator(',')
        .desc("Business processes regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.DMN.getArgOptionName())
        .hasArgs()
        .valueSeparator(',')
        .desc("Business rules regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.FORMS.getArgOptionName())
        .hasArgs()
        .valueSeparator(',')
        .desc("UI forms regulation files (accepts multiple values separated by ',')")
        .build());

    options.addOption(Option.builder()
        .longOpt(CommandLineArg.GLOBAL_VARS.getArgOptionName())
        .numberOfArgs(1)
        .desc("Global variables regulation files (accepts multiple values separated by ',')")
        .build());

    return options;
  }
}
