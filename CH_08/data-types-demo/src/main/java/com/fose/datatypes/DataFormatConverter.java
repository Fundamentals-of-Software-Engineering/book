package com.fose.datatypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.yaml.snakeyaml.Yaml;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Demonstrates conversion between different data formats:
 * - JSON (JavaScript Object Notation)
 * - XML (eXtensible Markup Language)
 * - CSV (Comma-Separated Values)
 * - YAML (YAML Ain't Markup Language)
 */
public class DataFormatConverter {
    private final ObjectMapper objectMapper;
    private final Yaml yaml;

    public DataFormatConverter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.yaml = new Yaml();
    }

    /**
     * Convert Customer to JSON format
     * JSON is the de facto standard for data exchange in modern applications
     */
    public String toJson(Customer customer) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                              .writeValueAsString(customer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    /**
     * Convert JSON string to Customer object
     */
    public Customer fromJson(String json) {
        try {
            return objectMapper.readValue(json, Customer.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    /**
     * Convert Customer to XML format
     * XML is more verbose but offers robust validation and namespaces
     */
    public String toXml(Customer customer) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<customer>\n");
        xml.append("    <id>").append(customer.getId()).append("</id>\n");
        xml.append("    <firstName>").append(customer.getFirstName()).append("</firstName>\n");
        xml.append("    <lastName>").append(customer.getLastName()).append("</lastName>\n");
        xml.append("    <email>").append(customer.getEmail()).append("</email>\n");
        xml.append("    <birthDate>").append(customer.getBirthDate()).append("</birthDate>\n");

        if (customer.getAddress() != null) {
            xml.append("    <address>\n");
            xml.append("        <street>").append(customer.getAddress().getStreet()).append("</street>\n");
            xml.append("        <city>").append(customer.getAddress().getCity()).append("</city>\n");
            xml.append("        <state>").append(customer.getAddress().getState()).append("</state>\n");
            xml.append("        <zipCode>").append(customer.getAddress().getZipCode()).append("</zipCode>\n");
            xml.append("        <country>").append(customer.getAddress().getCountry()).append("</country>\n");
            xml.append("    </address>\n");
        }

        xml.append("</customer>");
        return xml.toString();
    }

    /**
     * Convert Customer to CSV format
     * CSV is simple but lacks built-in data type definitions
     */
    public String toCsv(Customer customer) {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s",
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getBirthDate(),
            escapeForCsv(customer.getAddress().getStreet()),
            customer.getAddress().getCity(),
            customer.getAddress().getState(),
            customer.getAddress().getZipCode(),
            customer.getAddress().getCountry()
        );
    }

    /**
     * Get CSV header
     */
    public String getCsvHeader() {
        return "id,firstName,lastName,email,birthDate,street,city,state,zipCode,country";
    }

    /**
     * Convert Customer to YAML format
     * YAML offers human-friendly syntax with support for comments
     */
    public String toYaml(Customer customer) {
        Map<String, Object> data = new LinkedHashMap<>();

        Map<String, Object> customerMap = new LinkedHashMap<>();
        customerMap.put("id", customer.getId());
        customerMap.put("firstName", customer.getFirstName());
        customerMap.put("lastName", customer.getLastName());
        customerMap.put("email", customer.getEmail());
        customerMap.put("birthDate", customer.getBirthDate().toString());

        if (customer.getAddress() != null) {
            Map<String, String> addressMap = new LinkedHashMap<>();
            addressMap.put("street", customer.getAddress().getStreet());
            addressMap.put("city", customer.getAddress().getCity());
            addressMap.put("state", customer.getAddress().getState());
            addressMap.put("zipCode", customer.getAddress().getZipCode());
            addressMap.put("country", customer.getAddress().getCountry());
            customerMap.put("address", addressMap);
        }

        data.put("customer", customerMap);
        return yaml.dump(data);
    }

    /**
     * Escape special characters for CSV
     */
    private String escapeForCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Create a sample customer for demonstrations
     */
    public static Customer createSampleCustomer() {
        Address address = new Address(
            "123 Main Street",
            "Boston",
            "MA",
            "02108",
            "USA"
        );

        return new Customer(
            1001L,
            "Jane",
            "Smith",
            "jane.smith@example.com",
            LocalDate.of(1985, 3, 15),
            address
        );
    }
}
