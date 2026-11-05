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

package com.bytechef.component.ai.llm.router.opper.util;

import static com.bytechef.component.definition.ComponentDsl.option;

import com.bytechef.component.definition.ActionDefinition.OptionsFunction;
import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Option;
import com.bytechef.component.definition.TypeReference;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Felix Wunderlich
 */
public class OpperUtils {

    private OpperUtils() {
    }

    public static OptionsFunction<String> getOpperChatModels() {
        return (inputParameters, connectionParameters, lookupDependsOnPaths, searchText, context) -> {
            ModelsResponse response = context.http(http -> http.get("/models"))
                .queryParameter("view", "simplified")
                .configuration(Context.Http.responseType(Context.Http.ResponseType.JSON))
                .execute()
                .getBody(new TypeReference<>() {});

            return response.data()
                .stream()
                .filter(OpperModel::isChatModel)
                .map(OpperUtils::toOption)
                .collect(Collectors.toList());
        };
    }

    private static Option<String> toOption(OpperModel model) {
        OpperMetadata metadata = model.opper();
        String description = metadata == null ? null : metadata.description();

        return option(model.id(), model.id(), description);
    }

    private record OpperModel(String id, OpperMetadata opper) {

        boolean isChatModel() {
            return opper == null || !"embedding".equals(opper.type());
        }
    }

    private record OpperMetadata(String type, String description) {
    }

    private record ModelsResponse(List<OpperModel> data) {
    }
}
