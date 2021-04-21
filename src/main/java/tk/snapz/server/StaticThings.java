package tk.snapz.server;

import hu.trigary.simplenetty.serialization.DataSerializer;

import java.nio.charset.StandardCharsets;

public class StaticThings {
    public static DataSerializer<String> stringSerializer = new DataSerializer<String>() {
        @Override
        public byte[] serialize(String data) {
            return data.getBytes(StandardCharsets.US_ASCII);
        }

        @Override
        public String deserialize(byte[] bytes) {
            return new String(bytes, StandardCharsets.US_ASCII);
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    };
}
