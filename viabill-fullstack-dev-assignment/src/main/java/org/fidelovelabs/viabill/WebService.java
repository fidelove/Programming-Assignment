package org.fidelovelabs.viabill;

import static spark.Spark.*;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class WebService {
    public static void main(String[] args) {
        Config config = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config );
        Map<Integer, String> mapCompanies = instance.getMap("companies");
        get("/getCompanies", (req, res) -> "Hello World");
    }
}
