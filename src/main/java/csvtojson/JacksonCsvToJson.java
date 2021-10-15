package csvtojson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

  import org.apache.commons.cli.CommandLine;
    import org.apache.commons.cli.Option;
    import org.apache.commons.cli.Options;
    import org.apache.commons.cli.Option.Builder;
    import org.apache.commons.cli.CommandLineParser;
    import org.apache.commons.cli.DefaultParser;
    import org.apache.commons.cli.ParseException;

public class JacksonCsvToJson {

    public static void main(String[] args) throws Exception {
       
	   Options options = new Options();

        Option input = new Option("input", "input", true, "input csv file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("output", "output", true, "output json file");
        output.setRequired(true);
        options.addOption(output);


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;//not a good practice, it serves it purpose 

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
         //   formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");

	
		File inputfile = new File(inputFilePath);
        File outputfile = new File(outputFilePath);
		
        List<Map<?, ?>> data = readObjectsFromCsv(inputfile);
        writeAsJson(data, outputfile);
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