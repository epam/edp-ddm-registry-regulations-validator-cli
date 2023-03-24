/*
 * Copyright 2023 EPAM Systems.
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

package com.epam.digital.data.platform.registry.regulation.validation.cli.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BpGroupingConfiguration implements RegulationConfiguration {

  @JsonIgnore
  private File regulationFile;

  private List<Groups> groups = new ArrayList<>();
  private List<String> ungrouped = new ArrayList<>();

  @Data
  public static class Groups {

    private String name;
    private List<String> processDefinitions = new ArrayList<>();
  }
}