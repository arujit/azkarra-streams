/*
 * Copyright 2019 StreamThoughts.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.streamthoughts.azkarra.serialization.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.kafka.streams.processor.ThreadMetadata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThreadMetadataSerializer extends JsonSerializer<ThreadMetadata> {
    @Override
    public void serialize(final ThreadMetadata metadata,
                          final JsonGenerator gen,
                          final SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeFieldName("name");
        gen.writeString(metadata.threadName());
        gen.writeFieldName("state");
        gen.writeString(metadata.threadState());
        gen.writeFieldName("active_tasks");
        gen.writeObject(metadata.activeTasks());
        gen.writeFieldName("standby_tasks");
        gen.writeObject(metadata.standbyTasks());

        Map<String, Object> clients = new HashMap<>();
        clients.put("admin_client_id", metadata.adminClientId());
        clients.put("consumer_client_id", metadata.consumerClientId());
        clients.put("producer_client_ids", metadata.producerClientIds());
        clients.put("restore_consumer_client_id", metadata.restoreConsumerClientId());
        gen.writeFieldName("clients");
        gen.writeObject(clients);
        gen.writeEndObject();
    }
}
