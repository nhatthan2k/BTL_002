package ra.entity;

import ra.bussiness.IProduct;

import java.io.Serializable;
import java.util.Scanner;

import static ra.entity.Category.headerDisplayCategory;
import static ra.presentation.CategoryPresentation.listCategory;
import static ra.presentation.ProductPresentation.listProduct;

public class Product implements IProduct, Serializable {
    private String id;
    private String name;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
    private int categoryId;

    public Product() {
    }

    public Product(String id, String name, double importPrice, double exportPrice, double profit, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.id = inputIdProduct(scanner);
        this.name = inputNameProduct(scanner);
        this.importPrice = inputImportPrice(scanner);
        this.exportPrice = inputExportPrice(scanner);
        this.description = inputDescription(scanner);
        this.status = inputStatus(scanner);
        this.categoryId = inputCategoryId(scanner);
        calProfit();
    }

    public String inputIdProduct(Scanner scanner) {
        do {
            System.out.println("mã sản phẩm (4 kí tự, bắt đầu bằng “P”):");
            String id = scanner.nextLine();

            if (id.length() == 4) {
                if (id.startsWith("P")) {
                    boolean isIdProduct = false;
                    for (Product product : listProduct) {
                        if (product.getId().equals(id)) {
                            isIdProduct = true;
                            break;
                        }
                    }

                    if (isIdProduct) {
                        System.err.println("mã sản phẩm đã tồn tại! vui lòng nhập lại");
                    } else {
                        return id;
                    }
                } else {
                    System.err.println("mã sản phẩm bắt đầu là P! vui lòng nhập lại");
                }
            } else {
                System.err.println("mã sản phẩm có 4 kí tự! vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputNameProduct(Scanner scanner) {
        do {
            System.out.println("Tên sản phẩm (6-30 kí tự):");
            String nameProduct = scanner.nextLine();

            if (nameProduct.length() >= 6 && nameProduct.length() <= 30) {
                boolean isNameProduct = false;
                for (Product product : listProduct) {
                    if (product.getName().equals(nameProduct)) {
                        isNameProduct = true;
                        break;
                    }
                }

                if (isNameProduct) {
                    System.err.println("tên sản phẩm đã tồn tại! vui lòng nhập lại");
                } else {
                    return nameProduct;
                }
            } else {
                System.err.println("tên sản phẩm có từ 6-30 kí tự! vui lòng nhập lại");
            }
        } while (true);
    }

    public double inputImportPrice(Scanner scanner) {
        do {
            System.out.println("giá nhập sản phẩm:");
            try {
                double importPrice = Double.parseDouble(scanner.nextLine());
                if (importPrice > 0) {
                    return importPrice;
                } else {
                    System.err.println("giá nhập có giá trị lớn hơn 0! vui lòng nhâp lại");
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhập số thực!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    public double inputExportPrice(Scanner scanner) {
        do {
            System.out.println("giá bán sản phẩm:");
            try {
                double exportPrice = Double.parseDouble(scanner.nextLine());
                if (exportPrice > this.importPrice * (1 + MIN_INTEREST_RATE)) {
                    return exportPrice;
                } else {
                    System.err.printf("giá bản có giá trị lớn hơn giá nhập %f lần! vui lòng nhâp lại\n", MIN_INTEREST_RATE);
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhập số thực!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        do {
            System.out.println("mô tả sản phẩm:");
            String description = scanner.nextLine();

            if (description.trim().isEmpty()) {
                System.err.println("không được bỏ trống mô tả! vui lòng nhâp lại");
            } else {
                return description;
            }
        } while (true);
    }

    public boolean inputStatus(Scanner scanner) {
        do {
            System.out.println("trạng thái sản phẩm:");
            String status = scanner.nextLine();

            if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(status);
            } else {
                System.err.println("trạng thái sản phẩm chỉ nhận true/false!");
            }
        } while (true);
    }

    public int inputCategoryId(Scanner scanner) {
        do {
            System.out.println("mã danh mục đã lưu:");
            headerDisplayCategory();
            for (Category category : listCategory) {
                category.displayData();
            }

            System.out.println("mã danh mục sản phẩm");
            try {
                int categoryId = Integer.parseInt(scanner.nextLine());
                for (Category category : listCategory) {
                    if (category.getId() == categoryId) {
                        return categoryId;
                    }
                }

                System.err.println("không tồn tại mã danh mục trên");
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (true);
    }

    public void updateData(Scanner scanner) {
        boolean isExitUpdate = true;
        do {
            System.out.println("************Câp nhât thông tin************");
            System.out.println("1. cập nhât id");
            System.out.println("2. cập nhât name");
            System.out.println("3. cập nhât importPrice");
            System.out.println("4. cập nhât exportPrice");
            System.out.println("5. cập nhật description");
            System.out.println("6. cập nhật status");
            System.out.println("7. cập nhật categoryId");
            System.out.println("8. thoát");
            System.out.println("lựa chon của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.id = inputIdProduct(scanner);
                        break;
                    case 2:
                        this.name = inputNameProduct(scanner);
                        break;
                    case 3:
                        this.importPrice = inputImportPrice(scanner);
                        break;
                    case 4:
                        this.exportPrice = inputExportPrice(scanner);
                        break;
                    case 5:
                        this.description = inputDescription(scanner);
                        break;
                    case 6:
                        this.status = inputStatus(scanner);
                        break;
                    case 7:
                        this.categoryId = inputCategoryId(scanner);
                        break;
                    case 8:
                        isExitUpdate = false;
                        break;
                    default:
                        System.out.println("nhập lựa chọn từ 1-8");
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhâp số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (isExitUpdate);
    }

    public String displayCategoryName(int categoryId) {
        for (Category category : listCategory) {
            if (category.getId() == categoryId) {
                return category.getName();
            }
        }
        return "";
    }

    @Override
    public void displayData() {
        System.out.printf("| %-10s | %-15s | %-15f | %-15f | %-15f | %-15s | %-15s | %-15s |\n",
                this.id, this.name, this.importPrice, this.exportPrice, this.profit, this.description, this.status, displayCategoryName(this.categoryId));
        System.out.println("+------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
    }

    public static void headerDisplayProduct() {
        String separator = "+------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+";
        String header = "|     ID     |       Name      |   importPrice   |  exportPrice    |     Profit      |   Description   |     status      |  Category name  |";
        System.out.printf(separator + "\n"
                + header + "\n"
                + separator + "\n"
        );
    }

    @Override
    public void calProfit() {
        this.profit = this.exportPrice - this.importPrice;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-15s | %-15f | %-15f | %-15f | %-15s | %-15s | %-15s |",
                this.id, this.name, this.importPrice, this.exportPrice, this.profit, this.description, this.status, displayCategoryName(this.categoryId)) + "\n" +
                "+------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+";
    }
}
