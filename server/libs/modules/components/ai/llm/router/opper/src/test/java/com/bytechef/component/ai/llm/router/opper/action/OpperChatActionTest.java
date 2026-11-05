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

import static com.bytechef.component.ai.llm.constant.LLMConstants.MAX_TOKENS;
import static com.bytechef.component.ai.llm.constant.LLMConstants.MODEL;
import static com.bytechef.component.ai.llm.constant.LLMConstants.REASONING;
import static com.bytechef.component.ai.llm.constant.LLMConstants.STOP;
import static com.bytechef.component.ai.llm.constant.LLMConstants.TEMPERATURE;
import static com.bytechef.component.ai.llm.constant.LLMConstants.TOP_P;
import static com.bytechef.component.ai.llm.constant.LLMConstants.USER;
import static com.bytechef.component.ai.llm.router.constant.RouterConstants.MAX_COMPLETION_TOKENS;
import static com.bytechef.component.definition.Authorization.TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bytechef.component.ai.llm.router.opper.model.OpperChatModel;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.test.definition.MockParametersFactory;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Felix Wunderlich
 */
class OpperChatActionTest {

    private final Parameters mockedParameters = MockParametersFactory.create(
        Map.ofEntries(
            Map.entry(TOKEN, "token"), Map.entry(MODEL, "model"),
            Map.entry(MAX_COMPLETION_TOKENS, 1), Map.entry(MAX_TOKENS, 1),
            Map.entry(REASONING, "reasoning"), Map.entry(STOP, List.of()),
            Map.entry(TEMPERATURE, 0.0), Map.entry(TOP_P, 0.0), Map.entry(USER, "user")));

    @Test
    void testPerform() {
        OpperChatModel opperChatModel = (OpperChatModel) OpperChatAction.CHAT_MODEL.createChatModel(
            mockedParameters, mockedParameters, false);

        assertNotNull(opperChatModel);

        assertEquals("model", opperChatModel.getModel());
        assertEquals(1, opperChatModel.getMaxCompletionTokens());
        assertEquals(1, opperChatModel.getMaxTokens());
        assertEquals("reasoning", opperChatModel.getReasoning());
        assertEquals(List.of(), opperChatModel.getStop());
        assertEquals(0.0, opperChatModel.getTemperature());
        assertEquals(0.0, opperChatModel.getTopP());
        assertEquals("user", opperChatModel.getUser());
    }
}
