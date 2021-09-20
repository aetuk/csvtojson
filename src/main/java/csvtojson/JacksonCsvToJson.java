package csvtojson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class JacksonCsvToJson {

    public static void main(String[] args) throws Exception {
       
		String errorMessage = "You need to specify a file and path, for example : java -jar csvtojson-1.0-SNAPSHOT-jar-with-dependencies.jar /x/data.csv /x/data.json";
		
		
		 if (args.length < 1)
		 {
			System.out.println(errorMessage);
			return;
		 }
			 
		if ((args[0] == null || args[0].trim().isEmpty()) 
			 &&
			(args[1] == null || args[1].trim().isEmpty()))
		 {
			System.out.println(errorMessage);
			return;
		 }

		File input = new File(args[0]);
        File output = new File(args[1]);
		
        List<Map<?, ?>> data = readObjectsFromCsv(input);
        writeAsJson(data, output);
    }

    public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);

        return mappingIterator.readAll();
    }

    public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, data);
    }
}