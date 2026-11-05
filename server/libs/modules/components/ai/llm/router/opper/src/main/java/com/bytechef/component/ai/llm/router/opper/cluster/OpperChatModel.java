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

package com.bytechef.component.ai.llm.router.opper.cluster;

import static com.bytechef.component.ai.llm.constant.LLMConstants.USER_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_COMPLETION_TOKENS_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_TOKENS_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.STOP_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.TEMPERATURE_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.TOP_P_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.action.OpperChatAction.CHAT_MODEL;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.CHAT_MODEL_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.REASONING_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.SUPPORTED_PARAMETERS_PROPERTY;

import com.bytechef.component.definition.ClusterElementDefinition;
import com.bytechef.component.definition.ComponentDsl;
import com.bytechef.component.definition.Parameters;
import com.bytechef.platform.component.definition.ai.agent.ModelFunction;
import org.springframework.ai.chat.model.ChatModel;

/**
 * @author Felix Wunderlich
 */
public class OpperChatModel {

    public static final ClusterElementDefinition<ModelFunction> CLUSTER_ELEMENT_DEFINITION =
        ComponentDsl.<ModelFunction>clusterElement("model")
            .title("Opper Model")
            .description("Opper model.")
            .type(ModelFunction.MODEL)
            .object(() -> OpperChatModel::apply)
            .properties(
                SUPPORTED_PARAMETERS_PROPERTY,
                CHAT_MODEL_PROPERTY,
                MAX_COMPLETION_TOKENS_PROPERTY,
                MAX_TOKENS_PROPERTY,
                REASONING_PROPERTY,
                STOP_PROPERTY,
                TEMPERATURE_PROPERTY,
                TOP_P_PROPERTY,
                USER_PROPERTY);

    protected static ChatModel apply(
        Parameters inputParameters, Parameters connectionParameters, boolean responseFormatRequired) {

        return CHAT_MODEL.createChatModel(inputParameters, connectionParameters, responseFormatRequired);
    }
}
