package ra.bussinessIpm;

import ra.entity.Product;

import java.util.Comparator;
import java.util.Scanner;

import static ra.presentation.ProductPresentation.listProduct;

public class ProductIpm {
    public static void addProduct(Scanner scanner) {
        boolean isExist = true;
        int numAdd = 0;
        System.out.println("nhập số lượng sản phẩm bạn muốn thêm:");
        do {
            try {
                numAdd = Integer.parseInt(scanner.nextLine());
                isExist = false;
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExist);

        for (int i = 0; i < numAdd; i++) {
            Product product = new Product();
            product.inputData(scanner);
            listProduct.add(product);
        }
    }

    public static void updateProduct(Scanner scanner) {
        System.out.println("nhập vào mã sản phẩm cần cập nhật:");
        String updateId = scanner.nextLine();
        boolean isUpdate = false;

        for (Product product : listProduct) {
            if (product.getId().equals(updateId)) {
                product.updateData(scanner);
                isUpdate = true;
                break;
            }
        }

        if (!isUpdate) {
            System.out.println("Không tồn tại mã sản phẩm!");
        }
    }

    public static void deleteCategory(Scanner scanner) {
        System.out.println("mã sản phẩm cần xóa:");
        String deleteId = scanner.nextLine();
        boolean isDelete = false;

        for (Product product : listProduct) {
            if (product.getId().equals(deleteId)) {
                listProduct.remove(product);
                isDelete = true;
                System.out.println("xóa sản phẩm thành công!");
                break;
            }
        }

        if (!isDelete) {
            System.out.println("Không tồn tại mã sản phẩm!");
        }
    }

    public static void softProductByName() {
        listProduct.stream().sorted(Comparator.comparing(Product::getName)).forEach(System.out::println);
    }

    public static void softProductByProfit() {
        listProduct.stream().sorted(Comparator.comparing(Product::getProfit).reversed()).forEach(System.out::println);
    }

    public static void searchProduct(Scanner scanner) {
        System.out.println("tên sản phẩm tìm kiếm:");
        String searchName = scanner.nextLine();

        listProduct.stream().filter(product -> product.getName().contains(searchName)).forEach(System.out::println);
    }
}
