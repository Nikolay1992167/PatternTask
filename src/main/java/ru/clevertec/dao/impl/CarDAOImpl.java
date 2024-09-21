package ru.clevertec.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.clevertec.dao.CarDAO;
import ru.clevertec.entity.Car;
import ru.clevertec.exception.CarDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CarDAOImpl implements CarDAO {

    private final Connection connection;

    @Override
    public Optional<Car> findById(UUID id) {

        String sql = "SELECT * FROM cars WHERE id = ?";
        Optional<Car> car = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, (id));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    car = Optional.of(getCarFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new CarDAOException("Error fetching car by ID", e);
        }
        return car;
    }

    @Override
    public List<Car> getAll(Integer pageNumber, Integer pageSize) {

        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY id LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, pageNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Car car = getCarFromResultSet(resultSet);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            throw new CarDAOException("Error fetching all cars", e);
        }
        return cars;
    }

    @Override
    public Car save(Car car) {

        String sql = "INSERT INTO cars (name, description, price) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setCarValuesInStatement(statement, car);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString(1));
                car.setId(id);
            }
        } catch (SQLException e) {
            throw new CarDAOException("Error saving car", e);
        }
        return car;
    }

    @Override
    public Car update(Car car) {

        String sql = "UPDATE cars SET name = ?, description = ?, price = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setCarValuesInStatement(statement, car);
            statement.setObject(4, car.getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString(1));
                car.setId(id);
            }
        } catch (SQLException e) {
            throw new CarDAOException("Error updating car", e);
        }
        return car;
    }

    @Override
    public void delete(UUID id) {

        String sql = "DELETE FROM cars WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new CarDAOException("Error deleting car", e);
        }
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        return Car.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }

    private void setCarValuesInStatement(PreparedStatement statement, Car car) throws SQLException {
        statement.setString(1, car.getName());
        statement.setString(2, car.getDescription());
        statement.setBigDecimal(3, car.getPrice());
    }
}