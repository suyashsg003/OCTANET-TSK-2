import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Product class to represent items
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d\t%.2f\t%.2f", name, quantity, price, getTotalPrice());
    }
}

// Receipt class to handle calculations
class Receipt {
    private List<Product> products;
    private final double TAX_RATE = 0.05; // 5% tax
    private double discountRate;

    public Receipt(double discountRate) {
        this.products = new ArrayList<>();
        this.discountRate = discountRate;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateSubtotal() {
        double subtotal = 0;
        for (Product product : products) {
            subtotal += product.getTotalPrice();
        }
        return subtotal;
    }

    public double calculateTax() {
        return calculateSubtotal() * TAX_RATE;
    }

    public double calculateDiscount() {
        return calculateSubtotal() * discountRate;
    }

    public double calculateFinalTotal() {
        return calculateSubtotal() + calculateTax() - calculateDiscount();
    }

    public void printReceipt() {
        System.out.println("Item\tQty\tPrice\tTotal");
        System.out.println("-------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.printf("\nSubtotal: %.2f\n", calculateSubtotal());
        System.out.printf("Tax: %.2f\n", calculateTax());
        System.out.printf("Discount: %.2f\n", calculateDiscount());
        System.out.printf("Final Total: %.2f\n", calculateFinalTotal());
    }

    public String generateReceiptString() {
        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("Item\tQty\tPrice\tTotal\n");
        receiptBuilder.append("-------------------------------\n");
        for (Product product : products) {
            receiptBuilder.append(product).append("\n");
        }
        receiptBuilder.append(String.format("\nSubtotal: %.2f\n", calculateSubtotal()));
        receiptBuilder.append(String.format("Tax: %.2f\n", calculateTax()));
        receiptBuilder.append(String.format("Discount: %.2f\n", calculateDiscount()));
        receiptBuilder.append(String.format("Final Total: %.2f\n", calculateFinalTotal()));
        return receiptBuilder.toString();
    }
}

// Main program to accept user input and handle the receipt
public class BakeryReceiptCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating a receipt with a 5% discount
        Receipt receipt = new Receipt(0.10); //10% discount 

        // User input for products
        while (true) {
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("end")) break;

            System.out.print("Enter price: ");
            double price = scanner.nextDouble();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            // Add product to the receipt
            Product product = new Product(name, price, quantity);
            receipt.addProduct(product);
        }

        // Displaying the receipt
        receipt.printReceipt();

        // Saving receipt to a text file
        System.out.print("Would you like to save the receipt ? (yes/no): ");
        String saveReceipt = scanner.nextLine();
        if (saveReceipt.equalsIgnoreCase("yes")) {
            saveReceiptToFile(receipt);
        }

        scanner.close();
    }

    // Save the receipt as a text file
    public static void saveReceiptToFile(Receipt receipt) {
        try (FileWriter writer = new FileWriter("receipt.txt")) {
            writer.write(receipt.generateReceiptString());
            System.out.println("Receipt saved to 'receipt.txt'.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the receipt.");
        }
    }
}
