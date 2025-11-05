package com.fose.datatypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests demonstrating different data formats and their conversions.
 * Each test shows how the same Customer data can be represented in different formats.
 */
class DataFormatConverterTest {
    private DataFormatConverter converter;
    private Customer customer;

    @BeforeEach
    void setUp() {
        converter = new DataFormatConverter();
        customer = DataFormatConverter.createSampleCustomer();
    }

    @Test
    void shouldConvertToJson() {
        // JSON is the most common format for web APIs
        String json = converter.toJson(customer);

        System.out.println("JSON Format:");
        System.out.println(json);
        System.out.println();

        // Verify key elements are present
        assertTrue(json.contains("\"id\" : 1001"));
        assertTrue(json.contains("\"firstName\" : \"Jane\""));
        assertTrue(json.contains("\"email\" : \"jane.smith@example.com\""));
        assertTrue(json.contains("\"birthDate\" : \"1985-03-15\""));
    }

    @Test
    void shouldConvertFromJson() {
        // Convert to JSON and back
        String json = converter.toJson(customer);
        Customer restored = converter.fromJson(json);

        // Verify round-trip conversion works
        assertEquals(customer.getId(), restored.getId());
        assertEquals(customer.getFirstName(), restored.getFirstName());
        assertEquals(customer.getEmail(), restored.getEmail());
        assertEquals(customer.getBirthDate(), restored.getBirthDate());
    }

    @Test
    void shouldConvertToXml() {
        // XML is more verbose but offers schema validation
        String xml = converter.toXml(customer);

        System.out.println("XML Format:");
        System.out.println(xml);
        System.out.println();

        // Verify XML structure
        assertTrue(xml.startsWith("<?xml version=\"1.0\""));
        assertTrue(xml.contains("<customer>"));
        assertTrue(xml.contains("<firstName>Jane</firstName>"));
        assertTrue(xml.contains("<address>"));
        assertTrue(xml.contains("</customer>"));
    }

    @Test
    void shouldConvertToCsv() {
        // CSV is simple for tabular data
        String header = converter.getCsvHeader();
        String csv = converter.toCsv(customer);

        System.out.println("CSV Format:");
        System.out.println(header);
        System.out.println(csv);
        System.out.println();

        // Verify CSV structure
        String[] values = csv.split(",");
        assertEquals("1001", values[0]);
        assertEquals("Jane", values[1]);
        assertEquals("Smith", values[2]);
    }

    @Test
    void shouldConvertToYaml() {
        // YAML is human-friendly and supports comments
        String yaml = converter.toYaml(customer);

        System.out.println("YAML Format:");
        System.out.println(yaml);
        System.out.println();

        // Verify YAML structure
        assertTrue(yaml.contains("customer:"));
        assertTrue(yaml.contains("id: 1001"));
        assertTrue(yaml.contains("firstName: Jane"));
        assertTrue(yaml.contains("address:"));
    }

    @Test
    void shouldHandleSpecialCharactersInCsv() {
        // CSV needs special handling for commas and quotes
        Address addressWithComma = new Address(
            "123 Main Street, Apt 4B",  // Contains comma
            "Boston",
            "MA",
            "02108",
            "USA"
        );
        customer.setAddress(addressWithComma);

        String csv = converter.toCsv(customer);

        System.out.println("CSV with Special Characters:");
        System.out.println(csv);
        System.out.println();

        // The street address should be quoted because it contains a comma
        assertTrue(csv.contains("\"123 Main Street, Apt 4B\""));
    }
}
