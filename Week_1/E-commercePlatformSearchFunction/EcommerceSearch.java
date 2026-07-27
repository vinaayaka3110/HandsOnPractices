public class EcommerceSearch {

    // Creating Product class
    static class Product {
        int productId;
        String productName;
        String category;

        Product(int productId, String productName, String category) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
        }

        void display() {
            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Category: " + category);
        }
    }

    // Linear Search algorithm ......
    public static Product linearSearch(Product[] products, int targetId) {
        for (Product product : products) {
            if (product.productId == targetId) {
                return product;
            }
        }
        return null;
    }

    // Binary Search algorithm .........
    public static Product binarySearch(Product[] products, int targetId) {
        int low = 0;
        int high = products.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (products[mid].productId == targetId) {
                return products[mid];
            }

            if (products[mid].productId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return null;
    }

    public static void main(String[] args) {

        // inserting the products in sorted way, bcz to implement the binary
        // search.......
        Product[] products = {
                new Product(101, "Laptop", "Electronics"),
                new Product(102, "Shoes", "Fashion"),
                new Product(103, "Mobile", "Electronics"),
                new Product(104, "Watch", "Accessories"),
                new Product(105, "Book", "Study Material")
        };

        int targetId = 103;

        // Linear Searching output ...
        System.out.println("=== Linear Search ===");
        Product result1 = linearSearch(products, targetId);

        if (result1 != null) {
            System.out.println("Product Found:");
            result1.display();
        } else {
            System.out.println("Product Not Found");
        }

        // Binary Searchig output ......
        System.out.println("\n=== Binary Search ===");
        Product result2 = binarySearch(products, targetId);

        if (result2 != null) {
            System.out.println("Product Found:");
            result2.display();
        } else {
            System.out.println("Product Not Found");
        }
    }
}