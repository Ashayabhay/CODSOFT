import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Simple JSON parser for exchange rate API responses
class SimpleJSONParser {
    
    public static Map<String, Double> parseRates(String jsonString) {
        Map<String, Double> rates = new HashMap<>();
        
        // Find the rates object in the JSON
        Pattern ratesPattern = Pattern.compile("\"rates\"\\s*:\\s*\\{([^}]+)\\}");
        Matcher ratesMatcher = ratesPattern.matcher(jsonString);
        
        if (ratesMatcher.find()) {
            String ratesContent = ratesMatcher.group(1);
            
            // Parse individual currency rates
            Pattern currencyPattern = Pattern.compile("\"([A-Z]{3})\"\\s*:\\s*([0-9.]+)");
            Matcher currencyMatcher = currencyPattern.matcher(ratesContent);
            
            while (currencyMatcher.find()) {
                String currency = currencyMatcher.group(1);
                double rate = Double.parseDouble(currencyMatcher.group(2));
                rates.put(currency, rate);
            }
        }
        
        return rates;
    }
    
    public static String parseBaseCurrency(String jsonString) {
        Pattern basePattern = Pattern.compile("\"base\"\\s*:\\s*\"([A-Z]{3})\"");
        Matcher baseMatcher = basePattern.matcher(jsonString);
        
        if (baseMatcher.find()) {
            return baseMatcher.group(1);
        }
        
        return "USD"; // Default fallback
    }
}

// Class to handle currency conversion operations
class CurrencyService {
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/";
    
    // Map of currency codes to currency names
    private final Map<String, String> currencyMap;
    
    public CurrencyService() {
        currencyMap = new HashMap<>();
        initializeCurrencies();
    }
    
    // Initialize supported currencies
    private void initializeCurrencies() {
        currencyMap.put("USD", "US Dollar");
        currencyMap.put("EUR", "Euro");
        currencyMap.put("GBP", "British Pound");
        currencyMap.put("JPY", "Japanese Yen");
        currencyMap.put("AUD", "Australian Dollar");
        currencyMap.put("CAD", "Canadian Dollar");
        currencyMap.put("CHF", "Swiss Franc");
        currencyMap.put("CNY", "Chinese Yuan");
        currencyMap.put("INR", "Indian Rupee");
        currencyMap.put("KRW", "South Korean Won");
        currencyMap.put("SGD", "Singapore Dollar");
        currencyMap.put("HKD", "Hong Kong Dollar");
        currencyMap.put("NOK", "Norwegian Krone");
        currencyMap.put("SEK", "Swedish Krona");
        currencyMap.put("DKK", "Danish Krone");
        currencyMap.put("PLN", "Polish Zloty");
        currencyMap.put("CZK", "Czech Koruna");
        currencyMap.put("HUF", "Hungarian Forint");
        currencyMap.put("RUB", "Russian Ruble");
        currencyMap.put("BRL", "Brazilian Real");
        currencyMap.put("MXN", "Mexican Peso");
        currencyMap.put("ZAR", "South African Rand");
        currencyMap.put("TRY", "Turkish Lira");
        currencyMap.put("NZD", "New Zealand Dollar");
    }
    
    // Get currency name by code
    public String getCurrencyName(String code) {
        if (currencyMap.containsKey(code)) {
            return currencyMap.get(code);
        } else {
            return "Unknown Currency";
        }
    }
    
    // Get all supported currencies
    public Map<String, String> getAllCurrencies() {
        return new HashMap<>(currencyMap);
    }
    
    // Fetch exchange rates from API
    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // If same currency, return 1.0
            if (baseCurrency.equals(targetCurrency)) {
                return 1.0;
            }
            
            // Create URL for API request
            String urlString = BASE_URL + baseCurrency;
            URL url = new URL(urlString);
            
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("User-Agent", "Currency Converter App");
            
            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("API returned response code: " + responseCode);
            }
            
            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
            
            // Parse JSON response using our simple parser
            String jsonResponse = response.toString();
            Map<String, Double> rates = SimpleJSONParser.parseRates(jsonResponse);
            
            // Get exchange rate for target currency
            if (rates.containsKey(targetCurrency)) {
                return rates.get(targetCurrency);
            } else {
                throw new Exception("Target currency " + targetCurrency + " not found in API response");
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching exchange rate: " + e.getMessage());
            System.out.println("Using fallback exchange rate...");
            return getFallbackRate(baseCurrency, targetCurrency);
        }
    }
    
    // Fallback exchange rates (in case API is unavailable)
    private double getFallbackRate(String baseCurrency, String targetCurrency) {
        // Simple fallback rates relative to USD (approximate values)
        Map<String, Double> fallbackRates = new HashMap<>();
        fallbackRates.put("USD", 1.0);
        fallbackRates.put("EUR", 0.85);
        fallbackRates.put("GBP", 0.73);
        fallbackRates.put("JPY", 110.0);
        fallbackRates.put("INR", 83.0);
        fallbackRates.put("AUD", 1.35);
        fallbackRates.put("CAD", 1.25);
        fallbackRates.put("CHF", 0.92);
        fallbackRates.put("CNY", 7.2);
        fallbackRates.put("KRW", 1200.0);
        fallbackRates.put("SGD", 1.35);
        fallbackRates.put("HKD", 7.8);
        fallbackRates.put("NOK", 8.5);
        fallbackRates.put("SEK", 9.0);
        fallbackRates.put("DKK", 6.3);
        fallbackRates.put("PLN", 3.9);
        fallbackRates.put("CZK", 22.0);
        fallbackRates.put("HUF", 350.0);
        fallbackRates.put("RUB", 75.0);
        fallbackRates.put("BRL", 5.2);
        fallbackRates.put("MXN", 18.0);
        fallbackRates.put("ZAR", 15.0);
        fallbackRates.put("TRY", 8.5);
        fallbackRates.put("NZD", 1.45);
        
        double baseRate = fallbackRates.containsKey(baseCurrency) ? fallbackRates.get(baseCurrency) : 1.0;
        double targetRate = fallbackRates.containsKey(targetCurrency) ? fallbackRates.get(targetCurrency) : 1.0;
        
        return targetRate / baseRate;
    }
}

// Main Currency Converter class
class CurrencyConverter {
    private final CurrencyService currencyService;
    private final Scanner scanner;
    
    public CurrencyConverter() {
        this.currencyService = new CurrencyService();
        this.scanner = new Scanner(System.in);
    }
    
    // Start the currency converter application
    public void start() {
        try {
            System.out.println("========================================");
            System.out.println("    WELCOME TO CURRENCY CONVERTER");
            System.out.println("========================================");
            System.out.println("Convert between different currencies with");
            System.out.println("real-time exchange rates!");
            System.out.println("========================================");
            
            boolean continueConverting = true;
            
            while (continueConverting) {
                try {
                    // Get base currency
                    String baseCurrency = selectCurrency("base");
                    if (baseCurrency == null) {
                        continueConverting = false;
                        continue;
                    }
                    
                    // Get target currency
                    String targetCurrency = selectCurrency("target");
                    if (targetCurrency == null) {
                        continueConverting = false;
                        continue;
                    }
                    
                    // Get amount to convert
                    double amount = getAmountInput();
                    if (amount < 0) {
                        continueConverting = false;
                        continue;
                    }
                    
                    // Perform conversion
                    performConversion(baseCurrency, targetCurrency, amount);
                    
                    // Ask if user wants to continue
                    continueConverting = askToContinue();
                    
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    continueConverting = askToContinue();
                }
            }
            
            System.out.println("\nThank you for using Currency Converter!");
            System.out.println("Have a great day!");
        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        } finally {
            // Close scanner
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    // Display currency selection menu
    private String selectCurrency(String type) {
        System.out.println("\n========================================");
        System.out.println("    SELECT " + type.toUpperCase() + " CURRENCY");
        System.out.println("========================================");
        
        Map<String, String> currencies = currencyService.getAllCurrencies();
        String[] currencyCodes = currencies.keySet().toArray(new String[0]);
        java.util.Arrays.sort(currencyCodes); // Sort currencies alphabetically
        
        // Display currencies in a formatted way (two columns)
        int count = 1;
        for (int i = 0; i < currencyCodes.length; i += 2) {
            String leftCode = currencyCodes[i];
            String leftName = currencies.get(leftCode);
            
            if (i + 1 < currencyCodes.length) {
                String rightCode = currencyCodes[i + 1];
                String rightName = currencies.get(rightCode);
                System.out.printf("%-2d. %-3s - %-20s  %-2d. %-3s - %s\n", 
                    count, leftCode, leftName, count + 1, rightCode, rightName);
                count += 2;
            } else {
                System.out.printf("%-2d. %-3s - %s\n", count, leftCode, leftName);
                count++;
            }
        }
        
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Select " + type + " currency (1-" + currencyCodes.length + ") or 0 to exit: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            if (choice == 0) {
                return null; // Exit
            }
            
            if (choice >= 1 && choice <= currencyCodes.length) {
                String selectedCurrency = currencyCodes[choice - 1];
                System.out.println("Selected: " + selectedCurrency + " - " + currencies.get(selectedCurrency));
                return selectedCurrency;
            } else {
                System.out.println("Invalid selection! Please try again.");
                return selectCurrency(type);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return selectCurrency(type);
        }
    }
    
    // Get amount input from user
    private double getAmountInput() {
        System.out.println("\n========================================");
        System.out.println("           AMOUNT INPUT");
        System.out.println("========================================");
        System.out.print("Enter the amount to convert (or 0 to exit): ");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount == 0) {
                return -1; // Exit signal
            }
            
            if (amount < 0) {
                System.out.println("Amount cannot be negative! Please try again.");
                return getAmountInput();
            }
            
            return amount;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            return getAmountInput();
        }
    }
    
    // Perform currency conversion
    private void performConversion(String baseCurrency, String targetCurrency, double amount) {
        System.out.println("\n========================================");
        System.out.println("         CONVERSION RESULT");
        System.out.println("========================================");
        System.out.println("Fetching real-time exchange rates...");
        
        try {
            // Get exchange rate
            double exchangeRate = currencyService.getExchangeRate(baseCurrency, targetCurrency);
            
            // Calculate converted amount
            double convertedAmount = amount * exchangeRate;
            
            // Display results
            System.out.println("\n✓ Conversion successful!");
            System.out.println("----------------------------------------");
            System.out.printf("From: %.2f %s (%s)\n", amount, baseCurrency, 
                            currencyService.getCurrencyName(baseCurrency));
            System.out.printf("To:   %.2f %s (%s)\n", convertedAmount, targetCurrency, 
                            currencyService.getCurrencyName(targetCurrency));
            System.out.println("----------------------------------------");
            System.out.printf("Exchange Rate: 1 %s = %.6f %s\n", baseCurrency, exchangeRate, targetCurrency);
            System.out.println("----------------------------------------");
            
            // Display currency symbols if available
            String baseSymbol = getCurrencySymbol(baseCurrency);
            String targetSymbol = getCurrencySymbol(targetCurrency);
            
            if (!baseSymbol.equals(baseCurrency + " ")) {
                System.out.printf("Formatted: %s%.2f → %s%.2f\n", baseSymbol, amount, targetSymbol, convertedAmount);
            }
            
        } catch (Exception e) {
            System.out.println("✗ Conversion failed!");
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("========================================");
    }
    
    // Get currency symbol
    private String getCurrencySymbol(String currencyCode) {
        Map<String, String> symbols = new HashMap<>();
        symbols.put("USD", "$");
        symbols.put("EUR", "€");
        symbols.put("GBP", "£");
        symbols.put("JPY", "¥");
        symbols.put("INR", "₹");
        symbols.put("KRW", "₩");
        symbols.put("CNY", "¥");
        symbols.put("RUB", "₽");
        symbols.put("CAD", "C$");
        symbols.put("AUD", "A$");
        symbols.put("CHF", "CHF ");
        symbols.put("BRL", "R$");
        symbols.put("MXN", "$");
        symbols.put("ZAR", "R");
        
        if (symbols.containsKey(currencyCode)) {
            return symbols.get(currencyCode);
        } else {
            return currencyCode + " ";
        }
    }
    
    // Ask user if they want to continue
    private boolean askToContinue() {
        System.out.print("\nWould you like to perform another conversion? (y/n): ");
        String response = scanner.nextLine().toLowerCase().trim();
        return response.equals("y") || response.equals("yes");
    }
}

// Main class to run the Currency Converter
public class CurrencyConverterApp {
    public static void main(String[] args) {
        System.out.println("Starting Currency Converter Application...");
        System.out.println("Note: This app uses the free ExchangeRate-API service.");
        System.out.println("Internet connection required for real-time rates.\n");
        
        CurrencyConverter converter = new CurrencyConverter();
        converter.start();
    }
}