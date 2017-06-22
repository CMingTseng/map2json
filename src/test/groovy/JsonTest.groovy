import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonOutput
import org.json.JSONObject
import spock.lang.Specification
import spock.lang.Unroll

class JsonTest extends Specification {

    public static final String expectedJson = '{"hei":"sann"}'

    @Unroll
    def 'map to json using gson'() {
        expect:
        new Gson().toJson(map) == expectedJson  // TEST FAIL: toJson returns null when using the inlineMap()

        where:
        map << [ plainMap(), inlineMap(), guavaMap() ]
    }

    @Unroll
    def 'map to json using gson using wrapped map'() {
        expect:
        new Gson().toJson(new HashMap(map)) == expectedJson  // No problems

        where:
        map << [ plainMap(), inlineMap(), guavaMap() ]
    }

    @Unroll
    def 'map to json using groovy'() {
        expect:
        JsonOutput.toJson(map) == expectedJson

        where:
        map << [ plainMap(), inlineMap(), guavaMap() ]
    }

    @Unroll
    def 'map to json using jsonobject'() {
        expect:
        new JSONObject(map).toString() == expectedJson

        where:
        map << [ plainMap(), inlineMap(), guavaMap() ]
    }

    @Unroll
    def 'map to json using jackson'() {
        expect:
        new ObjectMapper().writeValueAsString(map) == expectedJson

        where:
        map << [ plainMap(), inlineMap(), guavaMap() ]
    }

    private Map<String, String> plainMap() {
        def amap = new LinkedHashMap()
        amap.put("hei", "sann")
        return amap
    }


    private Map<String, String> inlineMap() {
        new LinkedHashMap() {{
            put("hei", "sann")
        }}
    }

    private Map<String, String> guavaMap() {
        ImmutableMap.of("hei", "sann")
    }
}