
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterJSON {
    private final static String baseFile = "pet.json";

    public static void toJSON(HashMap<String, Pet> pet) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(baseFile), pet);
    }

    public static Pet toJavaObject() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(baseFile), Pet.class);
    }
}
