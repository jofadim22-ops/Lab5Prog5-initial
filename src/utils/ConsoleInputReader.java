package utils;

import model.Coordinates;
import model.Location;
import model.Route;
import exceptions.InvalidDataException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class ConsoleInputReader implements AutoCloseable {

    private final BufferedReader reader;

    public ConsoleInputReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public ConsoleInputReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }


    public Route readRoute(boolean forUpdate) throws InvalidDataException {
        try {
            System.out.println("=== Enter Route Details ===");

            String name = readString("Enter name", false, false);
            if (name == null || name.trim().isEmpty()) {
                throw new InvalidDataException("name", "non-empty string", "empty");
            }

            Coordinates coordinates = readCoordinates();

            System.out.println("\n--- Optional: From Location ---");
            Location from = readOptionalLocation();

            System.out.println("\n--- Optional: To Location ---");
            Location to = readOptionalLocation();

            Float distance = readFloat("Enter distance (>1)", false, 1f, null);
            if (distance == null || distance <= 1) {
                throw new InvalidDataException("distance", "float > 1", String.valueOf(distance));
            }

            return new Route(name, coordinates, from, to, distance);

        } catch (IOException e) {
            throw new InvalidDataException("input", "readable data", "IO error: " + e.getMessage());
        }
    }

    public Coordinates readCoordinates() throws InvalidDataException {
        try {
            System.out.println("--- Enter Coordinates ---");
            Integer x = readInteger("Enter x (max 849)", false, null, 849);
            Integer y = readInteger("Enter y (must be > -880)", false, -879, null);
            return new Coordinates(x, y);
        } catch (IOException e) {
            throw new InvalidDataException("coordinates", "valid x/y pair", "IO error");
        }
    }

    public Location readOptionalLocation() throws InvalidDataException {
        try {
            System.out.println("Enter location name (or press Enter to skip): ");
            String nameInput = reader.readLine();

            if (nameInput == null || nameInput.trim().isEmpty()) {
                return null;
            }

            System.out.println("--- Enter Location Details ---");
            Double x = readDouble("Enter x (Double, required)", false, null, null);
            Float yFloat = readFloat("Enter y (float)", false, null, null);
            Integer zInt = readInteger("Enter z (int)", false, null, null);

            float y = yFloat != null ? yFloat : 0f;
            int z = zInt != null ? zInt : 0;
            String name = nameInput.trim();

            if (name.length() > Location.MAX_NAME_LENGTH) {
                throw new InvalidDataException("name", "≤" + Location.MAX_NAME_LENGTH + " chars",
                        name.length() + " chars");
            }

            return new Location(x, y, z, name);

        } catch (IOException e) {
            throw new InvalidDataException("location", "valid location data", "IO error: " + e.getMessage());
        }
    }

    public String readString(String prompt, boolean notNull, boolean canBeEmpty)
            throws IOException {
        while (true) {
            System.out.println(prompt + ": ");
            String input = reader.readLine();

            if (input == null) {
                throw new IOException("Input stream closed");
            }

            input = input.trim();

            if (input.isEmpty()) {
                if (notNull && !canBeEmpty) {
                    System.err.println(" This field cannot  be empty. Please try again.");
                    continue;
                }
                return notNull ? null : "";
            }
            return input;
        }
    }

    public Integer readInteger(String prompt, boolean allowNull, Integer min, Integer max)
            throws IOException {
        while (true) {
            String suffix = allowNull ? "(empty for null)" : "";
            System.out.println(prompt + suffix + ": ");
            String input = reader.readLine();

            if (input == null) throw new IOException("Input stream closed");
            input = input.trim();

            if (input.isEmpty()) {
                if (allowNull) return null;
                System.err.println(" Input cannot be empty. Please enter an integer.");
                continue;
            }
            try {
                int value = Integer.parseInt(input);
                if (min != null && value < min) {
                    System.err.println(" Value must be ≥ " + min);
                    continue;
                }
                if (max != null && value > max) {
                    System.err.println(" Value must be ≤ " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.err.println(" Invalid integer format. Please try again.");
            }
        }
    }

    public Float readFloat(String prompt, boolean allowNull, Float min, Float max)
            throws IOException {
        while (true) {
            String suffix = allowNull ? " (empty for null)" : "";
            System.out.println(prompt + suffix + ": ");
            String input = reader.readLine();

            if (input == null) throw new IOException("Input stream closed");
            input = input.trim();

            if (input.isEmpty()) {
                if (allowNull) return null;
                System.err.println(" Input cannot be empty. Please enter a number");
                continue;
            }

            try {
                float value = Float.parseFloat(input);
                if (min != null && value < min) {
                    System.err.println(" Value must be ≥ " + min);
                    continue;
                }
                if (max != null && value > max) {
                    System.err.println(" Value must be ≤ " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.err.println(" Invalid number format. Please try again.");
            }
        }
    }

    public Double readDouble(String prompt, boolean allowNull, Double minGreaterThan, Double max)
            throws IOException {
        while (true) {
            String suffix = allowNull ? " (empty for null)" : "";
            System.out.print(prompt + suffix + ": ");
            String input = reader.readLine();

            if (input == null) throw new IOException("Input stream closed");

            if (input.isEmpty()) {
                if (allowNull) return null;
                System.err.println(" Input cannot be empty. Please enter a number.");
                continue;
            }

            try {
                double value = Double.parseDouble(input.trim());
                if (minGreaterThan != null && value <= minGreaterThan) {
                    System.err.println(" Value must be > " + minGreaterThan);
                    continue;
                }
                if (max != null && value > max) {
                    System.err.println(" Value must be ≤ " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.err.println(" Invalid number format. PLease try again.");
            }
        }
    }

    @Override
    public void close() {
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            System.err.println("Warning: Could not Close input reader: " + e.getMessage());
        }
    }
}






