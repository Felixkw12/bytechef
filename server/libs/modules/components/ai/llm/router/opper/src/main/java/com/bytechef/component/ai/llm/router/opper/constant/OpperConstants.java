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

package com.bytechef.component.ai.llm.router.opper.constant;

import static com.bytechef.component.ai.llm.constant.LLMConstants.MODEL;
import static com.bytechef.component.ai.llm.constant.LLMConstants.REASONING;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.SUPPORTED_PARAMETERS;
import static com.bytechef.component.ai.llm.router.opper.util.OpperUtils.getOpperChatModels;
import static com.bytechef.component.definition.ComponentDsl.array;
import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.ComponentDsl.string;

import com.bytechef.component.ai.llm.ChatModel;
import com.bytechef.component.definition.ComponentDsl.ModifiableArrayProperty;
import com.bytechef.component.definition.ComponentDsl.ModifiableStringProperty;
import com.bytechef.component.definition.Option;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Felix Wunderlich
 */
public class OpperConstants {

    public static final String BASE_URL = "https://api.opper.ai/v3/compat";

    public static final ModifiableStringProperty CHAT_MODEL_PROPERTY = string(MODEL)
        .label("Model")
        .description("ID of the model to use, for example claude-sonnet-4-5.")
        .options(getOpperChatModels())
        .required(true);

    public static final ModifiableStringProperty EMBEDDING_MODEL_PROPERTY = string(MODEL)
        .label("Model")
        .description("ID of the model to use.")
        .options(
            option("text-embedding-3-small", "text-embedding-3-small"),
            option("text-embedding-3-large", "text-embedding-3-large"))
        .required(true);

    public static final ModifiableStringProperty REASONING_PROPERTY = string(REASONING)
        .label("Reasoning effort")
        .description(
            "Constrains effort on reasoning. Reducing reasoning effort can result in faster responses and fewer " +
                "tokens used on reasoning in a response.")
        .options(
            option("none", "none"),
            option("low", "low"),
            option("medium", "medium"),
            option("high", "high"),
            option("maximal", "xhigh"))
        .displayCondition("response.responseFormat == '%s'".formatted(ChatModel.ResponseFormat.TEXT.name()))
        .advancedOption(true)
        .displayCondition("contains(%s, 'reasoning')".formatted(SUPPORTED_PARAMETERS));

    public static final ModifiableArrayProperty SUPPORTED_PARAMETERS_PROPERTY = array(SUPPORTED_PARAMETERS)
        .label("Supported parameters")
        .description("Request parameters to expose for the selected model.")
        .items(string())
        .options(getSupportedParametersOptions())
        .defaultValue("response_format")
        .required(true);

    private OpperConstants() {
    }

    private static String[] getSupportedParametersString() {
        return new String[] {
            "max_completion_tokens", "max_tokens",
            "reasoning", "response_format",
            "stop",
            "temperature", "top_p"
        };
    }

    private static List<Option<String>> getSupportedParametersOptions() {
        return Arrays.stream(getSupportedParametersString())
            .map(param -> option(param, param))
            .collect(Collectors.toList());
    }
}
