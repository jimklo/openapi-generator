/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.cmd;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Command(name = "validate", description = "Validate specification")
public class Validate implements Runnable {

    @Option(name = {"-i", "--input-spec"}, title = "spec file", required = true,
            description = "location of the OpenAPI spec, as URL or file (required)")
    private String spec;

    @Override
    public void run() {
        System.out.println("Validating spec (" + spec + ")");

        SwaggerParseResult result = new OpenAPIParser().readLocation(spec, null, null);
        List<String> messageList = result.getMessages();
        Set<String> messages = new HashSet<String>(messageList);

        if (messages.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(System.lineSeparator());
            for (String message : messages) {
                sb.append(String.format("\t- %s%s", message, System.lineSeparator()));
            }
            sb.append(System.lineSeparator());
            sb.append("[error] Spec is invalid.");
            System.err.println(sb.toString());
            System.exit(1);
        } else {
            System.out.println("No validation errors detected.");
        }
    }
}
