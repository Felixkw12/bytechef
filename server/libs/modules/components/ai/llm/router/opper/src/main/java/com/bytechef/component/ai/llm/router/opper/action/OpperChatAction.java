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

package com.bytechef.component.ai.llm.router.opper.action;

import static com.bytechef.component.ai.llm.ChatModel.ResponseFormat.TEXT;
import static com.bytechef.component.ai.llm.constant.LLMConstants.ASK;
import static com.bytechef.component.ai.llm.constant.LLMConstants.ATTACHMENTS_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.FORMAT_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.MAX_TOKENS;
import static com.bytechef.component.ai.llm.constant.LLMConstants.MESSAGES_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.MODEL;
import static com.bytechef.component.ai.llm.constant.LLMConstants.PROMPT_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.REASONING;
import static com.bytechef.component.ai.llm.constant.LLMConstants.RESPONSE;
import static com.bytechef.component.ai.llm.constant.LLMConstants.RESPONSE_FORMAT;
import static com.bytechef.component.ai.llm.constant.LLMConstants.RESPONSE_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.STOP;
import static com.bytechef.component.ai.llm.constant.LLMConstants.SYSTEM_PROMPT_PROPERTY;
import static com.bytechef.component.ai.llm.constant.LLMConstants.TEMPERATURE;
import static com.bytechef.component.ai.llm.constant.LLMConstants.TOP_P;
import static com.bytechef.component.ai.llm.constant.LLMConstants.USER;
import static com.bytechef.component.ai.llm.constant.LLMConstants.USER_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_COMPLETION_TOKENS;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_COMPLETION_TOKENS_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_TOKENS_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.STOP_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.TEMPERATURE_PROPERTY;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.TOP_P_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.CHAT_MODEL_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.REASONING_PROPERTY;
import static com.bytechef.component.ai.llm.router.opper.constant.OpperConstants.SUPPORTED_PARAMETERS_PROPERTY;
import static com.bytechef.component.definition.Authorization.TOKEN;
import static com.bytechef.component.definition.ComponentDsl.action;

import com.bytechef.component.ai.llm.ChatModel;
import com.bytechef.component.ai.llm.ChatModel.ResponseFormat;
import com.bytechef.component.ai.llm.router.opper.model.OpperChatModel;
import com.bytechef.component.ai.llm.util.ModelUtils;
import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;

/**
 * @author Felix Wunderlich
 */
public class OpperChatAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action(ASK)
        .title("Ask")
        .description("Ask anything you want.")
        .help("", "https://docs.bytechef.io/reference/components/opper_v1#ask")
        .properties(
            SUPPORTED_PARAMETERS_PROPERTY,
            CHAT_MODEL_PROPERTY,
            PROMPT_PROPERTY,
            FORMAT_PROPERTY,
            SYSTEM_PROMPT_PROPERTY,
            ATTACHMENTS_PROPERTY,
            MESSAGES_PROPERTY,
            RESPONSE_PROPERTY,
            MAX_COMPLETION_TOKENS_PROPERTY,
            MAX_TOKENS_PROPERTY,
            REASONING_PROPERTY,
            STOP_PROPERTY,
            TEMPERATURE_PROPERTY,
            TOP_P_PROPERTY,
            USER_PROPERTY)
        .output(ModelUtils::output)
        .perform(OpperChatAction::perform);

    public static final ChatModel CHAT_MODEL = (inputParameters, connectionParameters, responseFormatRequired) -> {
        boolean jsonFormat = false;

        if (responseFormatRequired) {
            ResponseFormat responseFormat = inputParameters.getRequiredFromPath(
                RESPONSE + "." + RESPONSE_FORMAT, ResponseFormat.class);

            jsonFormat = !responseFormat.equals(TEXT);
        }

        return OpperChatModel.builder()
            .apiKey(connectionParameters.getString(TOKEN))
            .model(inputParameters.getRequiredString(MODEL))
            .maxCompletionTokens(inputParameters.getInteger(MAX_COMPLETION_TOKENS))
            .maxTokens(inputParameters.getInteger(MAX_TOKENS))
            .reasoning(inputParameters.getString(REASONING))
            .jsonResponseFormat(jsonFormat)
            .stop(inputParameters.getList(STOP, new TypeReference<>() {}))
            .temperature(inputParameters.getDouble(TEMPERATURE))
            .topP(inputParameters.getDouble(TOP_P))
            .user(inputParameters.getString(USER))
            .build();
    };

    private OpperChatAction() {
    }

    public static Object perform(Parameters inputParameters, Parameters connectionParameters, ActionContext context) {
        return CHAT_MODEL.getResponse(inputParameters, connectionParameters, context);
    }
}
