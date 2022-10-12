/*
 * Copyright 2022 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.registry.regulation.validation.cli.validator.datasettings.rules;

import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import com.epam.digital.data.platform.registry.regulation.validation.cli.validator.ValidationError;
import com.epam.digital.data.platform.registry.regulation.validation.cli.validator.datasettings.RulesOrder;

import java.util.Set;

@RuleBean
@Rule(order = RulesOrder.SUBPACKAGE_IS_JAVA_RESERVED_WORD_RULE)
public class SubpackageIsJavaReservedWordRule extends AbstractSettingsValidationRule {

    private static final Set<String> JAVA_RESERVED_WORDS = Set
            .of("byte", "short", "int", "long", "char", "float", "double", "boolean", "if", "else",
                    "switch", "case", "default", "while", "do", "break", "continue", "for", "try", "catch",
                    "finally", "throw", "throws", "private", "protected", "public", "import", "package",
                    "class", "interface", "extends", "implements", "static", "final", "void", "abstract",
                    "native", "new", "return", "this", "super", "synchronized", "volatile", "const", "goto",
                    "instanceof", "enum", "assert", "transient", "strictfp", "_", "exports", "module",
                    "open", "opens", "provides", "requires", "to", "transitive", "uses", "with",
                    "false", "true", "null");


    @When
    public boolean isSubpackageEqJavaReservedWord() {
        String pkg = datafactorySettingsYaml.getSettings().getGeneral().getBasePackageName();
        for (String subPackage : pkg.split("\\.")) {
            if (JAVA_RESERVED_WORDS.contains(subPackage)) {
                return true;
            }
        }
        return false;
    }

    @Then
    public RuleState then() {
        errors.add(ValidationError.of(regulationFileType, regulationFile,
                "The sub-package name must not match the words reserved in Java."));
        return RuleState.NEXT;
    }
}
