package org.example.logparser.parser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddonParser {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("agile-addon");
        names.add("administration-guide-en");
        names.add("dcm-addon");
        names.add("auth-module");
        names.add("bfp-addon");
        names.add("boardpreview-addon");
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/SyrineFekih/Downloads/iObeyaInfo20240301_103044.properties"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                for (String name : names) {
                    if (parts[0].startsWith("bundle_" + name)) {
                        String version = parts[1].substring(0, Math.min(parts[1].length()-1, 100));


                        JSONObject nameObject = new JSONObject();
                        nameObject.put("addon",name);
                        nameObject.put("version", version);
                        nameObject.put("status", "UP");
                        System.out.println(nameObject.toString(4));

                        names.remove(name);
                        break;

                    }
                }
            }

            for (String name : names) {
                JSONObject nameObject = new JSONObject();
                nameObject.put("version", "");
                nameObject.put("status", "DOWN");
                nameObject.put("addon",name);
                System.out.println(nameObject.toString(4));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}