package com.lucy.repository;

import com.lucy.model.Product;
import com.lucy.model.MicroProduct;
import com.lucy.model.SubMicroProduct;
import com.lucy.util.DBUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Named("productRepository")
@ApplicationScoped
public class ProductRepository {

    private final Connection connection;

    public ProductRepository() {
        this.connection = DBUtil.getConnection();
        if (this.connection == null) {
            throw new IllegalStateException("Failed to establish DB connection");
        }
    }

    // === Product CRUD ===

    public boolean saveProduct(Product product) {
        String query = "INSERT INTO products (name, description, image_url, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getImageUrl());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, description = ?, image_url = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getImageUrl());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, product.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProductById(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // === MicroProduct CRUD ===
    public boolean createMicroProduct(MicroProduct mp) {
        String query = "INSERT INTO micro_products (product_id, name, description, price, image_url, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, mp.getProductId());
            stmt.setString(2, mp.getName());
            stmt.setString(3, mp.getDescription());
            stmt.setDouble(4, mp.getPrice());
            stmt.setString(5, mp.getImageUrl());
            stmt.setInt(6, mp.getStock());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<MicroProduct> getAllMicroProducts() {
        List<MicroProduct> microProducts = new ArrayList<>();
        String query = "SELECT * FROM micro_products";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                microProducts.add(mapMicroProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return microProducts;
    }

    public List<MicroProduct> getMicroProductsByProductId(int productId) {
        List<MicroProduct> microProducts = new ArrayList<>();
        String query = "SELECT * FROM micro_products WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    microProducts.add(mapMicroProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return microProducts;
    }

    public boolean updateMicroProduct(MicroProduct mp) {
        String query = "UPDATE micro_products SET name = ?, description = ?, price = ?, image_url = ?, stock = ?, updated_at = NOW() WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mp.getName());
            stmt.setString(2, mp.getDescription());
            stmt.setDouble(3, mp.getPrice());
            stmt.setString(4, mp.getImageUrl());
            stmt.setInt(5, mp.getStock());
            stmt.setInt(6, mp.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMicroProduct(int id) {
        String query = "DELETE FROM micro_products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // === SubMicroProduct CRUD ===

    public boolean createSubMicroProduct(SubMicroProduct smp) {
        String query = "INSERT INTO sub_micro_products (micro_product_id, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, smp.getMicroProductId());
            stmt.setString(2, smp.getName());
            stmt.setString(3, smp.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<SubMicroProduct> getAllSubMicroProducts() {
        List<SubMicroProduct> subMicroProducts = new ArrayList<>();
        String query = "SELECT * FROM sub_micro_products";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                subMicroProducts.add(mapSubMicroProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subMicroProducts;
    }
    public List<MicroProduct> getMicroProductsWithSubsByProductId(Integer productId) {
        String query = "SELECT mp.id AS micro_id,  mp.name AS micro_name,  mp.description AS micro_description, mp.price, mp.image_url, mp.stock, smp.id AS sub_id, smp.name AS sub_name,  smp.description AS sub_description FROM  micro_products mp LEFT JOIN sub_micro_products smp  ON mp.id = smp.micro_product_id WHERE mp.product_id = ?";

        Map<Long, MicroProduct> microProductMap = new LinkedHashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long microId = rs.getLong("micro_id");
                MicroProduct micro = microProductMap.get(microId);

                if (micro == null) {
                    micro = new MicroProduct();
                    micro.setId((int) microId);
                    micro.setName(rs.getString("micro_name"));
                    micro.setDescription(rs.getString("micro_description"));
                    micro.setPrice(rs.getDouble("price"));
                    micro.setImageUrl(rs.getString("image_url"));
                    micro.setStock(rs.getInt("stock"));
                    micro.setSubMicroProducts(new ArrayList<>());
                    microProductMap.put(microId, micro);
                }

                long subId = rs.getLong("sub_id");
                if (!rs.wasNull()) {
                    SubMicroProduct sub = new SubMicroProduct();
                    sub.setId((int) subId);
                    sub.setName(rs.getString("sub_name"));
                    sub.setDescription(rs.getString("sub_description"));
                    micro.getSubMicroProducts().add(sub);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(microProductMap.values());
    }


    public List<SubMicroProduct> getSubMicroProductsByMicroId(int microId) {
        List<SubMicroProduct> subMicroProducts = new ArrayList<>();
        String query = "SELECT * FROM sub_micro_products WHERE micro_product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, microId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subMicroProducts.add(mapSubMicroProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subMicroProducts;
    }

    public boolean updateSubMicroProduct(SubMicroProduct smp) {
        String query = "UPDATE sub_micro_products SET name = ?, description = ?, updated_at = NOW() WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, smp.getName());
            stmt.setString(2, smp.getDescription());
            stmt.setInt(3, smp.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubMicroProduct(int id) {
        String query = "DELETE FROM sub_micro_products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Mapping Helpers ===

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setImageUrl(rs.getString("image_url"));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        p.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return p;
    }

    private MicroProduct mapMicroProduct(ResultSet rs) throws SQLException {
        MicroProduct mp = new MicroProduct();
        mp.setId(rs.getInt("id"));
        mp.setProductId(rs.getInt("product_id"));
        mp.setName(rs.getString("name"));
        mp.setDescription(rs.getString("description"));
        mp.setPrice(rs.getDouble("price"));
        mp.setImageUrl(rs.getString("image_url"));
        mp.setStock(rs.getInt("stock"));
        mp.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        mp.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return mp;
    }

    private SubMicroProduct mapSubMicroProduct(ResultSet rs) throws SQLException {
        SubMicroProduct smp = new SubMicroProduct();
        smp.setId(rs.getInt("id"));
        smp.setMicroProductId(rs.getInt("micro_product_id"));
        smp.setName(rs.getString("name"));
        smp.setDescription(rs.getString("description"));
        smp.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        smp.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return smp;
    }
}
