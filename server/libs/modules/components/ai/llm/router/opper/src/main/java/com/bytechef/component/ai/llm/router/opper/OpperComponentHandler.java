/*
 * Copyright 2025 ByteChef
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

package com.bytechef.component.ai.llm.router.opper;

import static com.bytechef.component.definition.ComponentDsl.component;

import com.bytechef.component.ComponentHandler;
import com.bytechef.component.ai.llm.router.opper.action.OpperChatAction;
import com.bytechef.component.ai.llm.router.opper.cluster.OpperChatModel;
import com.bytechef.component.ai.llm.router.opper.cluster.OpperEmbedding;
import com.bytechef.component.ai.llm.router.opper.connection.OpperConnection;
import com.bytechef.component.definition.ComponentCategory;
import com.bytechef.component.definition.ComponentDefinition;
import com.google.auto.service.AutoService;

/**
 * @author Felix Wunderlich
 */
@AutoService(ComponentHandler.class)
public class OpperComponentHandler implements ComponentHandler {

    private static final ComponentDefinition COMPONENT_DEFINITION = component("opper")
        .title("Opper")
        .description(
            "Opper is a European AI gateway that gives you access to 700+ AI models from OpenAI, Anthropic, " +
                "Google and other providers through a single OpenAI compatible API and one API key.")
        .icon("path:assets/opper.svg")
        .categories(ComponentCategory.ARTIFICIAL_INTELLIGENCE)
        .connection(OpperConnection.CONNECTION_DEFINITION)
        .actions(OpperChatAction.ACTION_DEFINITION)
        .clusterElements(
            OpperChatModel.CLUSTER_ELEMENT_DEFINITION,
            OpperEmbedding.CLUSTER_ELEMENT_DEFINITION)
        .version(1);

    @Override
    public ComponentDefinition getDefinition() {
        return COMPONENT_DEFINITION;
    }
}
