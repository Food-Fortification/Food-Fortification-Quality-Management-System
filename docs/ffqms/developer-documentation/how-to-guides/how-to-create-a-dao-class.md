# How to create a Dao Class

In FFQMS application we use data access object (DAO) responsible for interacting with the entities in the database. Each DAO class extends a generic `BaseDao` to inherit basic CRUD (Create, Read, Update, Delete) operations. It manages database operations related to the entity using the Java Persistence API (JPA) with an `EntityManager`.

**Example: AddressDao Class**

```java
@Component
public class AddressDao extends BaseDao<Address> {
    private final EntityManager em;

    public AddressDao(EntityManager em) {
        super(em, Address.class);
        this.em = em;
    }

    // Custom query to find an Address by the associated Manufacturer ID
    public Address findByManufacturerId(Long manufacturerId) {
        return em.createQuery("FROM Address a WHERE a.manufacturer.id = :manufacturerId", Address.class)
                .setParameter("manufacturerId", manufacturerId)
                .getSingleResult();
    }
}
```

The `AddressDao` class provides methods to interact with the `Address` entity, which is associated with manufacturers. It leverages an `EntityManager` to perform database queries and is annotated with `@Component` to allow Spring's dependency injection system to automatically manage this bean.

**Key Features**

1. **Extends BaseDao**:
   * The `AddressDao` class extends the `BaseDao` class, which is a generic DAO that provides basic CRUD operations. This allows the `AddressDao` to inherit common database operations, such as `save`, `delete`, `find`, and `update`, without having to redefine them.
   * The `BaseDao` is parameterized with the `Address` entity class, making it a type-specific DAO.
2. **EntityManager Integration**:
   * The class uses the `EntityManager` to interact with the database, which is injected via the constructor. The `EntityManager` is part of the Java Persistence API (JPA) and is responsible for managing the lifecycle of entities, executing queries, and handling transactions.
3. **Custom Query (findByManufacturerId)**:
   * The `findByManufacturerId` method defines a custom query to fetch the `Address` associated with a specific `Manufacturer` by its `manufacturerId`.
   * This method uses JPQL (Java Persistence Query Language) to select an `Address` where the `manufacturer.id` matches the provided `manufacturerId`.
   * `setParameter` binds the `manufacturerId` to the query, ensuring that it is safely passed into the query.

**Code Explanation**

**1. Class Declaration:**

```java
@Component
public class AddressDao extends BaseDao<Address> {
```

* **@Component**: This annotation registers `AddressDao` as a Spring bean, enabling it to be automatically managed and injected into other components. This makes the class eligible for dependency injection in the Spring application.
* **extends BaseDao**: This class extends a generic `BaseDao`, which provides common data access functionality for the `Address` entity.

**2. Constructor:**

```java
public AddressDao(EntityManager em) {
    super(em, Address.class);
    this.em = em;
}
```

* The constructor takes an `EntityManager` as a parameter and passes it to the `BaseDao` along with the `Address` entity class.
* This ensures that `BaseDao` can perform CRUD operations on the `Address` entity and the `AddressDao` has access to the `EntityManager` for custom queries.

**3. Custom Method: `findByManufacturerId`**

```java
public Address findByManufacturerId(Long manufacturerId) {
    return em.createQuery("FROM Address a WHERE a.manufacturer.id = :manufacturerId", Address.class)
            .setParameter("manufacturerId", manufacturerId)
            .getSingleResult();
}
```

* **Purpose**: This method retrieves the `Address` entity based on the associated `manufacturerId`.
* **JPQL Query**: `"FROM Address a WHERE a.manufacturer.id = :manufacturerId"` is a JPQL query that selects an `Address` where the `manufacturer.id` matches the provided `manufacturerId`.
* **Parameters**: The `setParameter("manufacturerId", manufacturerId)` method binds the `manufacturerId` parameter to the query to prevent SQL injection and ensure correct query execution.
* **Return Value**: The `getSingleResult()` method ensures that a single `Address` entity is returned. If no result is found, an exception will be thrown, which can be handled by the caller.

**Use Case Example**

Suppose you have a scenario where you need to fetch the `Address` of a manufacturer in your application. Instead of manually writing a query every time, you can simply call the `findByManufacturerId` method provided by `AddressDao`, passing in the `manufacturerId`. The method will return the corresponding `Address` entity, allowing you to access its properties and perform any necessary operations.

```java
@Autowired
private AddressDao addressDao;

public Address getAddressForManufacturer(Long manufacturerId) {
    return addressDao.findByManufacturerId(manufacturerId);
}
```

**Best Practices**

1. **Use DAO for Database Operations**: The `AddressDao` encapsulates all database operations for the `Address` entity, following the Data Access Object (DAO) pattern. This keeps database interaction logic separate from business logic, improving maintainability.
2. **Leverage JPQL for Custom Queries**: Use JPQL for complex queries involving relationships between entities, such as the `Manufacturer` and `Address` relationship in this case.
3. **EntityManager Injection**: The use of an `EntityManager` allows for flexible query execution and lifecycle management of entities. Spring manages the lifecycle of the `EntityManager` through dependency injection, simplifying the code.

**Conclusion**

The DAO classes simplifies the management of entities in the database by providing CRUD functionality and a custom query to find the entity with specific parameters. It leverages JPA’s `EntityManager` and Spring’s component management to streamline the process of database interaction, ensuring clean separation between data access and business logic.
