class: inverse, center, middle

# Haladó JPA

---

class: inverse, center, middle

# Tematika

---

# Tematika 1.

- Áttekintés
- JPA verziók
- Bonyolult kapcsolatok, lazy, eager fetch
- Entity graph
- Cascade műveletek
- Detach, merge
- Tranzakciókezelés, persistence context
- Flush

---

# Tematika 2.

- Bulk műveletek
- Query hints
- Refresh
- Lock
- First level és shared cache
- Natív lekérdezések
- Best practices

---

# Források

- [Mike Keith, Merrick Schincariol: Pro JPA 2 (Expert's Voice in Java) 2nd ed. Edition](https://www.amazon.com/Pro-JPA-Experts-Voice-Java/dp/1430249269)
- [Vlad Mihalcea: High-Performance Java Persistence](https://vladmihalcea.com/books/high-performance-java-persistence/)
- [Hibernate Documentation](https://hibernate.org/orm/documentation)
- [Thorben Janssen](https://thorben-janssen.com/)

---

class: inverse, center, middle

# Bevezetés

---

# Alapfogalmak 1.

- Perzisztens technológiák
  - JDBC (szinkron, blokkoló)
  - JPA
  - jOOQ
  - MyBatis
  - Spring JdbcTemplate
  - Spring Data
    - Spring Data JDBC
    - Spring Data JPA
  - Jakarta EE 11 / Jakarta Data 1.0
- Reaktív
  - R2DBC
    - Spring Data 2DBC
  - Hibernate Reactive
- DataSource
  - Legacy: C3PO, Apache DBCP
  - Tomcat JDBC Connection Pool
  - Hikari CP - iparági standard
  - [vibur-dbcp](http://www.vibur.org/) - kísérleti
- JPA
  - Hibernate
  - EclipseLink
- Entity
  - State field
  - Relationship field

---

# Alapfogalmak 2.

- Persistence unit
- Persistence context
- Entity manager
  - Application managed
  - Container managed
- JPQL
- Criteria API

---

# Verziók

- [JSR 220](https://jcp.org/en/jsr/detail?id=220) Enterprise JavaBeans 3.0
- [JSR 317](https://jcp.org/en/jsr/detail?id=317) Java Persistence 2.0
- [JSR 338](https://jcp.org/en/jsr/detail?id=317) Java Persistence 2.1
- [Jakarta Persistence 2.2](https://jakarta.ee/specifications/persistence/2.2/)
- [Jakarta Persistence 3.0](https://jakarta.ee/specifications/persistence/3.0/)
- [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/)
- [Jakarta Persistence 3.2](https://jakarta.ee/specifications/persistence/3.2/)

---

# JPA 2.0

- 2009\. december
- Kivált az EJB szabványból

---

# JPA 2.0 - Kiterjesztett ORM funkcionalitás

- Beágyazott objektumokat tartalmazó kollekciók (element collections)
- Map támogatása entitások és beágyazott objektumok esetén is
- One-to-many és one-to-one esetén is lehet join table
- Unidirectional one-to-many join column
- Orphan removal
- Oszlop a sorrend megtartására (`@OrderColumn` annotáció)
- Hozzáférés típusok kombinálása (`@Access`)
- Derived id: összetett kulcs, mely tartalmaz egy külső kulcsot is (pl. `project_id` = `department_id` + `project_short_name`)
- Több szinten beágyazott objektumok (Complex Embedded Objects)

---

# JPA 2.0 - Lekérdező nyelv

- Date, time, timestamp literálok
- Szűrés leszármazott típusra (`TYPE`)
- Map support
- Collection input paraméter (`IN`)
- `CASE`
- `NULLIF`, `COALESCE`
- Scalar operation (`SELECT LENGTH(e.name) FROM Employee e`)
- `INDEX` kollekcióban az elem indexe
- Projection query-ben változó (`SELECT new CustInfo(c.name, a) FROM Customer c JOIN c.address a`)

---

# JPA 2.0 további újdonságok

- `TypedQuery<T>`
- Criteria query API
- Shared cache
- Validálás támogatása
- Pessimistic lock
- `getMetamodel()`

---

# JPA 2.1 újdonságok 1.

- 2013\. május
- Attribute Converter, `@Converter`
- Criteria Update/Delete
- Tárolt eljárások
- Programmatic named query
- `@ConstructorResult`

---

# JPA 2.1 újdonságok 2.

- Séma generálás
- Entity graph
- JPQL: allekérdezések, függvények, JOIN ON, TREAT (downcast)
- Unsynchronized Persistence Context
- CDI in entity listeners

---

# JPA 2.2

- 2017\. június
- Lekérdezések streameket is tudnak visszaadni, háttérben optimalizálhat `ScrollableResult`-tal
- Repeatable annotációk
- Java 8 Date and Time API támogatása
- `AttributeConverter`-ben CDI injection
- JPA annotációk lehetnek metaannotációk
- JDK 9 module system Persistence Provider Discovery Mechanism

---

# Jakarta Persistence 2.2

- 2019\. augusztus
- Jakarta átvétel

---

# Jakarta Persistence 3.0

- 2020\. október
- Csomag átnevezés: `jakarta.persistence`
- `persistence.xml` névtér és property-k átnevezése

---

# Jakarta Persistence 3.1

- 2022. április
- `java.util.UUID` basic type lett, lehet id és persistent field is
  - Id esetén `@GeneratedValue(strategy=GenerationType.UUID)`
- Új függvények:
  - Numberikus: `CEILING`, `EXP`, `FLOOR`, `LN`, `POWER`, `ROUND`, `SIGN`
  - Dátum: `LOCAL DATE`, `LOCAL DATETIME`, `LOCAL TIME`, `EXTRACT`
- `EntityManagerFactory` és `EntityManager` implementálja az `AutoCloseable` interface-t
- `TableGenerator` és `SequenceGenerator` csomag szinten
- `Calendar`, `Date`, `Time`, `Timestamp`, `@Temporal`, `@MapKeyTemporal` és `TemporalType` deprecation

[What’s New in Jakarta Persistence 3.1](https://newsroom.eclipse.org/eclipse-newsletter/2022/march/what%E2%80%99s-new-jakarta-persistence-31)

---

# Jakarta Persistence 3.2

- 2024. május
- Record as embeddable classes
- Persistence QL bővítések
- Criteria API bővítések
- `@EnumeratedValue`
- A `@Table` és `@Column` annotációknak `comment` és `check` paraméterek

---

# Hibernate

- 6.4: Jakarta Persistence 3.1
- 6.6: Jakarta Persistence 3.1
- 7.0: Jakarta Persistence 3.2

---

# JPA Buddy

- Entity és DTO összekapcsolása

`EmployeeResource`

```java
/**
DTO for {@link Employee}
*/
```

---

# Példa Java SE alkalmazás

- H2
- Hibernate
- Lombok

Teszteléshez:

- JUnit 5, AssertJ, `hibernate-testing`

---

# Példa Spring Boot alkalmazás

- Spring Boot
- Lombok
- Spring Data JPA
- PostgreSQL

---

# Séma generálás

- Java SE példákban

```xml
<property name="javax.persistence.schema-generation.database.action" value="drop-and-create" />
```

- Spring Boot példákban

```conf
spring.jpa.generate-ddl=true
```

Valójában:

- Flyway vagy Liquibase

---

# Open EntityManager in view

```conf
spring.jpa.open-in-view=false
```

---

# Teszteset Java SE esetén

```java
class LocationServiceTest {

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
    }

    @Test
    void testSaveAndList() {
        doInJPA(() -> entityManagerFactory, (em) -> {
            LocationsDao locationsDao = new LocationsDao(em);
            locationsDao.saveLocation(new Location( "a", 1, 2));
            List<Location> locations = locationsDao.listLocations();

            assertThat(locations)
                    .extracting(Location::getName)
                    .containsExactly("a");
        });
    }
```

---

# Teszteset Spring Boot esetén

```java
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "delete from employees")
class EmployeesRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Test
    @Commit
    void saveThenFindAll() {
        repository.save(new Employee("John"));
        var employees = repository.findAll();
        assertThat(employees)
                .extracting(Employee::getName)
                .containsExactly("John");
    }

}
```

- Alapból tranzakcióban fut, és rollbackel
- Ha azt akarom, hogy commit legyen, akkor `@Commit` annotáció
- Ha tranzakción kívül akarom futtatni: `@Transactional(propagation = Propagation.NOT_SUPPORTED)`

---

class: inverse, center, middle

# Naplózás

---

# Cél

- A performancia problémák nagy része az adatbáziskezelésnél keresendő
- Definition of Done: tartalmazza az adatbázis műveletek ellenőrzését is

---

# Naplózás

- Ne használjuk a `hibernate.show_sql` property-t, hiszen konzolra naplóz
- Helyette pl. Logback, `logback.xml`

```xml
<logger name="org.hibernate.SQL" level="debug" />
```

- `persistence.xml`

```xml
<property name="hibernate.format_sql" value="true"/>
<property name="hibernate.use_sql_comments" value="true"/>
```

# Naplózás Spring alkalmazásban

```conf
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

logging.level.org.hibernate.SQL=trace
logging.level.org.hibernate.orm.jdbc.bind=trace
```

---

# Naplózás Spring tesztesetben

`@DataJpaTest` automatikusan bekapcsolja a konzolra írást

Helyette:

`@DataJpaTest(showSql = false)` és naplózás bekapcsolása

---

# DataSource proxy-k

- [P6Spy](https://github.com/p6spy/p6spy), last commit 2022
- [datasource-proxy](https://github.com/jdbc-observations/datasource-proxy)
  - Logging
  - Slow Query Detector
  - Metrics
  - Custom logic injection (`QueryExecutionListener`)
  - Query and parameter replacement (`QueryTransformer` és `ParameterTransformer`)
- [Flexy Pool](https://github.com/vladmihalcea/flexy-pool), [Vlad]
  - Metrics, histograms
  - Failover statistics

---

# P6Spy

- `spy.properties`

---

# datasource-proxy, datasource-assert

```xml
<dependency>
    <groupId>net.ttddyy</groupId>
    <artifactId>datasource-proxy</artifactId>
    <version>1.7</version>
</dependency>
```

---

# datasource-proxy

```java
PrettyQueryEntryCreator creator = new PrettyQueryEntryCreator();
creator.setMultiline(true);
SystemOutQueryLoggingListener listener = new SystemOutQueryLoggingListener();
listener.setQueryLogEntryCreator(creator);

DataSource dataSource = ProxyDataSourceBuilder.create(targetDataSource)
  .name("ProxyDataSource")
  .countQuery()
  .multiline()
  .listener(listener)
  .logSlowQueryToSysOut(1, TimeUnit.MINUTES)
  .build();
```

---

# datasource-proxy Spring Boot esetén

[Spring Boot DataSource Decorator](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)

```xml
<dependency>
  <groupId>com.github.gavlyukovskiy</groupId>
  <artifactId>datasource-proxy-spring-boot-starter</artifactId>
  <version>1.9.2</version>
</dependency>
```

---

# datasource-assert

- Utolsó commit 2017 októberében, 2023 decemberében archiválva

```java
JdbcDataSource targetDataSource = new JdbcDataSource();
targetDataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

dataSource = new ProxyTestDataSource(targetDataSource);

Map<String, Object> properties = new HashMap<>();
properties.put("javax.persistence.nonJtaDataSource", dataSource);
properties.put("javax.persistence.transactionType", "RESOURCE_LOCAL");

entityManagerFactory = Persistence.createEntityManagerFactory("pu", properties);
```

---

# Assert

```java
assertTrue(dataSource.getQueryExecutions().size() < 10);
```

```java
@AfterEach
void denit() {
    dataSource.reset();
}
```

---

## Spring Boot esetén assert datasource-proxy használatával

> Sajnos az alábbi kód nem nullázza a végrehajtásokat. Sokáig
> debuggoltam, de valahogy beragad egy referencia.

```conf
decorator.datasource.datasource-proxy.count-query=true
```

```java
QueryCountHolder.clear();

// ...

assertEquals(1, QueryCountHolder.get("dataSource").getInsert());
```

## Spring Boot esetén assert Hibernate Statistics használatával

```conf
spring.jpa.properties.hibernate.generate_statistics=true
```

```java
Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
statistics.clear();

// ...
assertEquals(1, statistics.getQueryExecutionCount());
```

---

class: inverse, center, middle

# Id generálás

---

# Milyen a jó id?

- Egyedi, nem tartalmazhat null értéket
- Ne legyen beágyazott jelentése
  - Biztonsági szempontból is aggályos lehet
- Időben ne változzon
- Legyen rövid (indexben és külső kulcsokban is szerepel)
- Ne legyen összetett
- Elosztott rendszerben
  - UUID
- Egész adatbázisban egyedi?

---

# Id típusa

- Jelentése alapján
  - Surrogate key (mesterséges kulcs)
  - Natural id
- Típus alapján
  - Egyszerű
    - Egész szám
    - UUID
  - Összetett

---

# Id generálás típusai

- Sequence
- Identity
- Table
- UUID
- Auto (provider választhat)

---

# Id generálás tulajdonságai

- Sequence és identity esetén hatékony lock
- Table esetén lassabb lock
- Identity csak insert után kapható vissza, sequence előbb kikérhető
- Ezért Identity esetén a batch műveleteknél lassulás
- UUID
  - Mérete és az indexelése miatt nem olyan hatékony [Vlad]
  - Manuálisan nehezen kezelhető (SQL console-ból)
- Sequence és identity nem tranzakcionális, lehet lyuk (így hatékonyabb, mintha rollbackelni lehetne)
- Ezért leghatékonyabb a sequence alapú generálás [Vlad]

---

# Hibernate Sequence ID generálás

Vizsgált verzió: Hibernate 6.6

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
```

- Sequence esetén a `org.hibernate.id.enhanced.SequenceStyleGenerator` generátort használja
- Sequence neve: `[táblanév]_seq`
- `increment_size` értéke 50
- Optimizer: nem kell minden azonosító kiosztásakor adatbázishoz fordulni
- `increment_size` > 1 esetén `pooled` optimizer

`pooled` optimizer működése:

- sequence-nek is be van állítva az `increment_size`-ja, pl. 50-re
- create sequence után kiadott első érték `1` - ez azt jelenti, hogy `1` és `50` között adhat ki értéket
- 50 kiadása után új értéket kér, `51`-et kapja meg

```sql
create sequence employees_seq start with 1 increment by 100;
select nextval('employees_seq');
-- 1
SELECT last_value from employees_seq;
-- 1
select nextval('employees_seq');
-- 51
SELECT last_value from employees_seq;
-- 51
```

Beállítások felülírása standard annotációval

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_generator")
@SequenceGenerator(name = "employees_generator", sequenceName = "employees_seq", allocationSize = 100)
```

- Ha az `allocationSize` értéke `1`, akkor az optimizer `none`, azaz mindig adatbázishoz fordul.
- Ha > 1, akkor a default `pooled` optimizer felülírható a `hibernate.id.optimizer.pooled.preferred` konfiggal

Ez az `application.properties`-ben pl.:

```conf
spring.jpa.properties.hibernate.id.optimizer.pooled.preferred=pooled-lo
```

> Összességében építsünk a JPA standard annotációkra! Ne használjunk más optimizert, mint a default!

> A `@GenericGenerator` már deprecated!

---

# Hibernate AUTO érték esetén

- Ha a típus UUID, akkor UUD generator
- Ha egész szám, és az adatbázis támogatja a sequence használatát, akkor `SequenceStyleGenerator`
- Egyéb esetben table

---

# Id generálás személyre szabása

- Alapértelmezett `increment_size` értéke `50`

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
@GenericGenerator(
        name = "location_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "location_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "5"),
                @Parameter(name = "optimizer", value = "pooled-lo")
        })
private Long id;
```

```sql
create sequence location_sequence start with 1 increment by 5
```

---

# Natural id

- Hibernate kiegészítés

```java
@NaturalId
@Column(name = "card_number")
private String cardNumber;
```

```java
var employee = entityManager.unwrap(Session.class)
                .byNaturalId(Employee.class)
                .using("cardNumber", "abc123")
                .load();
```

- Unique constraint rákerül tábla DDL generáláskor

---

class: inverse, center, middle

# Equals és hashCode

---

# Equals és hashCode

- Cél: egyik művelet esetén se változzon

```java
@Override
public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee other)) return false;
    return id != null &&id.equals(other.getId));
}

@Override
public final int hashCode() {
    return getClass().hashCode();
}
```

A hash csak `Map` és `HashSet` esetén érdekes. Nem szabad annyi entitást betölteni, hogy ez számítson.

[(Hopefully) the final article about equals and hashCode for JPA entities with DB-generated IDs](https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/)

JPA Buddy le tudja generálni

```java
@Override
public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Employee employee = (Employee) o;
    return getId() != null && Objects.equals(getId(), employee.getId());
}

@Override
public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
}
```

---

class: inverse, center, middle

# Kapcsolatok

---

# Kapcsolatokról általában

- Azért mert használható, még nem feltétlen kell használni
- Nem szűrhető
- Nem lapozható
- A számosság növekedésével jelennek meg a performancia problémák

---

# Irány (direction)

- Egyirányú (unidirectional)
- Kétirányú (bidirectional)
  - Két egyirányú kapcsolat, összekötve
- Most frequent direction of traversal

---

# Számosság (cardinality)

- One
- Many
  - Java Collection

---

# Kötelezőség (ordinality)

- Mandatory
- Optional

---

# Mapping

- Egy-egy, `@OneToOne`
- Egy-több, `@OneToMany`
- Több-egy, `@ManyToOne`
- Több-több, `@ManyToMany`

---

# Egyirányú one-to-one

- Külső kulcs, owner ahol az annotáció van
- Owner oldal, ahol az annotáció

---

# @MapsId

- JPA 2.0 vezette be a derived id fogalmát
- `@OneToOne` és `@ManyToOne` annotáció esetén `@MapsId` annotáció használata
- Megosztja az id-t, azaz a owner side kulcsa egyben külső kulcs is
- Performancia szempontjából hatékonyabb [Vlad]

---

# Kétirányú one-to-one

- `mappedBy` az inverz oldalra
- Owner, mely a kapcsolatot vezérli
- Ez jelzi, hogy nem ő vezérel, ez csak a másik irány
- Használjunk beállító metódusokat
- Inverz oldalon `FetchType.LAZY`, nem veszi figyelembe
  - JPA Buddy hibaüzenet: Specifing FetchType.LAZY for the non-owning side of the `@OneToOne` association will not affect the loading. The related entity will still be
    loaded as if the FetchType.EAGER is defined.
  - Megoldás: Hibernate weaving - enable association management

```xml
<enableAssociationManagement>true</enableAssociationManagement>
```

---

# Orphan removal

- One-to-one vagy one-to-many esetén, ha az owner oldal, vagy a kapcsolat törlésre kerül
- Owner kerül törlésre: cascade
- Kapcsolat törlése: `orphanRemoval = true`

```java
employee.removeAddress(address);
```

`Employee` osztályban:

```java
public void removeAddress(Address address) {
        addresses.remove(address);
        address.setEmployee(null);
}
```

Mind a kettő kell! Ilyenkor az `Address` példányt nem kell explicit törölni.
(Csak a `address.setEmployee(null)` híváskor bennmarad az adatbázisban `null` külső kulccsal.)

---

# Egyirányú many-to-one

- Külső kulcs, join column
- Owning side, owner
- Külső kulcs, owner a many oldalon, ahol az annotáció van
- `@JoinColumn` annotációval személyre szabható
- Performancia szempontjából a leghatékonyabb [Vlad]

---

# Egyirányú one-to-many

- Az owning side-hoz tartozó tábla nem tud több referenciát tartalmazni a másik táblára
- Join table
- JPA 2.0-től `@JoinColumn`
- Amennyiben tudjuk, kerüljük [Vlad]
- Beszúráskor több SQL:

```sql
INSERT INTO EMPLOYEE (CV, EMP_NAME) VALUES (?, ?);
INSERT INTO PHONE (PHONE_NUMBER, PHONE_TYPE) VALUES (?, ?)
UPDATE PHONE SET EMPLOYEE_ID = ? WHERE (ID = ?)
```

---

# Kétirányú one-to-many

- one-to-many és many-to-one
- many-to-one az owning, ott a join column
- one-to-many az inverse side, `mappedBy` használandó
- Kapcsolatot az owner side vezérli
- Használjunk beállító metódusokat (`add()`, `remove()`)
- Akkor használjuk, ha kevés gyermek rekord van [Vlad]
- Attól, hogy szülő-gyermek kapcsolat, még nem biztos, hogy szükség van a kollekcióra,
  inkább használjunk egyirányú many-to-one-t [Vlad]

---

# Many-to-many

- Join table
- `CascadeType.DELETE` nem lehet
- Kerüljük, inkább két many-to-one kapcsolatot használjunk [Vlad]

---

# `getReference` metódus

- A `find()` metódus speciális formája
- Kapcsolatnál akarjuk az entitást használni, és ismerjük az id-ját
- Nem kell a teljes entitást betölteni
- Proxy, csak az elsődleges kulcsa lesz felhasználva külső kulcsként
- Shared cache esetén a `find()` elegendő

---

class: inverse, center, middle

# Lazy műveletek

---

# Lazy műveletek

- Performancia
- Nem minden kapcsolatot akarunk betölteni
- Újbóli adatbázishoz fordulás több erőforrást használhat
- Default
  - Single value esetén eager (one-to-one, one-to-many)
  - Collection value esetén lazy (one-to-many, many-to-many)
- Felülbírálható
  - `fetch` attribútum `FetchType.EAGER`, `FetchType.LAZY`
- Proxy object

---

# EclipseLink megoldás

- Weaving
- Bájtkód manipuláció
- Típusai
  - Statikus - build time, [eclipselink-staticweave-maven-plugin](https://github.com/craigday/eclipselink-staticweave-maven-plugin)
  - Dynamic weaving - agent, classloading time

---

# Statikus weaving Mavennel

```xml
<plugin>
    <groupId>de.empulse.eclipselink</groupId>
    <artifactId>staticweave-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <phase>process-classes</phase>
            <goals>
                <goal>weave</goal>
            </goals>
            <configuration>
                <persistenceXMLLocation>META-INF/persistence.xml</persistenceXMLLocation>
                <logLevel>ALL</logLevel>
            </configuration>
        </execution>
    </executions>
    <dependencies>
      <!-- org.eclipse.persistence:org.eclipse.persistence.jpa függőség -->
    </dependencies>
</plugin>
```

---

# Dinamikus weaving

- Java agent
- `-javaagent` kapcsoló

---

# EclipseLink weaving

- Lazy loading
- Change tracking
- Fetch groups
- Belső optimalizálások

---

# Lazy betöltése

- getter hívása, szükséges nyitott persistence context?
  - Hibernate esetén: `LazyInitializationException`
  - EclipseLink esetén persistence contextet nyit
- Hogyan ellenőrizhető?
  - `PersistenceUtil.isLoaded(Object entity, String attributeName)`
  - `org.hibernate.Hibernate.isInitialized(..)`
  - `org.eclipse.persistence.indirection.IndirectList` `isInstantiated()` metódus

---

# N + 1 probléma

- Getter hívás esetén
- `FetchType.EAGER` esetén
- One-to-many, N + 1 lekérdezés

---

# Megoldás join fetch használatával

- JPQL `join fetch`
- `distinct` használata

# Megoldás Entity Graph használatával

## Entity graph

- JPA 2.1 bevezetésével
- Explicit megadása
  - Annotációkkal
  - `EntityGraph` API
- Default entity graph
  - Összes eager mezőkből összeálló gráf

---

## Entity graph hint

- `javax.persistence.fetchgraph` csak az explicit gráfban megadott mezőket
- `javax.persistence.loadgraph` csak az explicit gráfban megadott, és a default gráfban mezőket
- Hint, mert további mezőket is betölthet (pl. id és version mezőket mindig betölti)

---

## Spring Data JPA Entity Graph

```java
@EntityGraph(
            attributePaths = {
                    "addresses"
            }
    )
    @Query("select e from Employee e")
    List<Employee> findAllWithAddresses();
```

---

# Megoldás egy DTO lekérdezésével és szétválogatással

```java
public class EmployeeDto {
    private Long id;
    private String name;
    private List<AddressDto> addresses = new ArrayList<>();

    public EmployeeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getterek, setterek, hashCode & equals (id alapján)
}

public class AddressDto {
    private Long id;
    private String city;

    public AddressDto(Long id, String city) {
        this.id = id;
        this.city = city;
    }

    // Getterek, setterek
}

public class EmployeeAddressDto {
    private Long employeeId;
    private String employeeName;
    private Long addressId;
    private String addressCity;

    // Konstruktor, getterek
}
```

```java
public static List<EmployeeDto> transform(List<EmployeeAddressDto> dtos) {
    Map<Long, EmployeeDto> employeeMap = new HashMap<>();

    for (EmployeeAddressDto dto : dtos) {
        // Employee objektum létrehozása, ha még nincs
        employeeMap.computeIfAbsent(dto.getEmployeeId(),
            id -> new EmployeeDto(dto.getEmployeeId(), dto.getEmployeeName()));

        // AddressDto hozzáadása az EmployeeDto-hoz
        employeeMap.get(dto.getEmployeeId())
                   .getAddresses()
                   .add(new AddressDto(dto.getAddressId(), dto.getAddressCity()));
    }

    return new ArrayList<>(employeeMap.values());
}
```

---

# Több one-to-many kapcsolat

- fetch join esetén
- descartes szorzat
- 1\. megoldás: több select, építve a first level cache-re
- 2\. megoldás: kézzel lekérdezni a különböző entitásokat, és összefésülni
  - `in` használata vagy azonosítók átadásával, vagy belső select használatával
  - `Map` használata

---

# Inheritance

- Single table inheritance strategy
  - Not null mezők kezelése problémás: check constraint vagy trigger segítségével kezelhető
  - Kevés leszármazottnál a legmegfelelőbb
- Joined inheritance
  - Lehetnek not null mezők
  - Beszúráskor két insert kell
  - Kell index a külső kulcsra
  - N subclass, N+1 join
  - Sok leszármazottnál, mikor ritkán van polymorphic join
- Table per class inheritance strategy
  - Identity generátor nem adható meg
  - Ha van gyerek tábla, nincs külső kulcsa, hiszen melyik táblára legyen a több közül?
  - UNION ALL
  - Nem javasolt használni

---

# Mapped Superclass

- Nem entity, így nem lehet polymorphic query-t futtatni

---

class: inverse, center, middle

# Projection query

---

# Projection query

- Entity query-t csak módosításkor használjunk, lekérdezéskor projection query [Vlad]

---

# Spring Data JPA Dynamic Projection

```java
<T> List<T> findAllBySalaryGreaterThan(long salary, Class<T> clazz);
```

---

class: inverse, center, middle

# Lapozás

---

# Lapozás

- Egy több kapcsolat esetén
- `WARN  o.h.h.i.ast.QueryTranslatorImpl - HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!`
- Megoldás: külön select a szülőre, majd `IN` select a gyermekekkel együtt

---

# Spring Data JPA lapozás

```java
@Query("select new employees.EmployeeModel(e.id, e.name) from Employee e")
Page<EmployeeModel> findAllResources(Pageable pageable);
```

```java
var employees = employeesRepository.findAllResources(PageRequest.of(2, 10));
```

- Lesz egy plusz count select

# Spring Data JPA Slice

- `Slice<Employee>`
- Nem ad ki egy plusz count select-et
- Csak azt vizsgálja meg, hogy van-e utána elem
  - `pageSize` értékéhez hozzáad egyet

---

# Cursor based pagination

```java
@Query("select e from Employee e where e.cardNumber > :cardNumber")
Page<Employee> findByCardNumberGreaterThan(String cardNumber, Pageable pageable);
```

```java
void cursorBasedPagination() {
    for (int i = 0; i < 100; i++) {
        repository.save(new Employee("emp%02d".formatted(i), "John"));
    }

    String last = "";
    for (int i = 0; i < 10; i++) {
        var employees = repository.findByCardNumberGreaterThan(last, Pageable.ofSize(10));
        System.out.println("%s - %s".formatted(employees.getContent().getFirst().getCardNumber(), employees.getContent().getLast().getCardNumber()));
        last =  employees.getContent().getLast().getCardNumber();
    }
}
```

---

class: inverse, center, middle

# Detach, merge

---

# Entitás életciklus

<img src="entity-lifecycle.gif" alt="Entitás életciklus" width="600"/>

---

# Persistence Context

- Gondolhatunk rá úgy, mint `Map<Id, Entity>`
- First level cache

---

# Write behind cache

- Először a cache-be, persistence contextbe ír, majd a végén az adatbázisba
- Így összegyűjthetőek a műveletek a végére
- Flush time, mikor a cache tartalmát szinkronizálni kell az adatbázissal
- Akkor módosít, mikor feltétlen szükséges
- Rövid ideig lockol
- Ezzel transparent statement batching
- Flush time deríti fel a Hibernate, hogy mi módosult: _dirty checking_
- Ha ugyanarra módosítjuk, nincs update
- A fejlesztő az entity állapotátmenetekre koncentrálhat, és nem az adatbázis műveletekre

---

# Példa kód

```java
var employee = new Employee("John");
repository.save(employee);
System.out.println("Saved employee with id: " + employee.getId());
```

Ha ez egy tranzakció, akkor a `GenerationType.SEQUENCE` esetén az ID-t már kiírja a konzolra,
hiszen az ID generálás független, de csak utána, a tranzakció
végén megy az insert. `GenerationType.IDENTITY` esetén azonnal megy az insert, ezért lassabb.

---

# Hibernate dirty check

- Hibernate alapértelmezett dirty checking: másolat készítése
- Object tömbbe menti, loaded vagy hydrated state

---

# Immutable

- Hibernate `@Immutable` annotáció
- Gyorsítja, hiszen nem kell dirty checking

---

# Spring Data JPA `@Immutable` annotáció

- Nem összekeverendő a Hibernate `@Immutable` annotációval
- JPA esetén ezt nem veszi figyelembe, más Spring Data projekteknél használatos

---

# Dirty checking gyorsítás

- Weaving
- Bájtkód manipuláció
- Típusai
  - Statikus - build time, `hibernate-enhance-maven-plugin`
  - Dynamic weaving - agent, classloading time

```xml
<plugin>
    <groupId>org.hibernate.orm.tooling</groupId>
    <artifactId>hibernate-enhance-maven-plugin</artifactId>
    <version>${hibernate.version}</version>
    <executions>
        <execution>
            <configuration>
                <enableDirtyTracking>true</enableDirtyTracking>
            </configuration>
            <goals>
                <goal>enhance</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

# Hibernate weaving

- Dirty tracking
- Lazy init `@Basic`
- Enable association management

---

# EclipseLink

- Támogatja?

---

# Hibernate read-only

```java
setHint(`QueryHints.HINT_READONLY`, true)
```

---

# Spring `read-only` transaction

---

# Flush

- Flush kell
  - Commit előtt
  - Query előtt: read-your-writes
- Automatikusan vagy manuálisan (`flush()` metódus)

---

# Query-k és a nem commitolt változások kapcsolata

- Lekérdezések SQL-t futtatnak
- Alapesetben a persistence provider gondoskodik a query előtt az adatbázisba szinkronizálásról
- Performancia problémák lehetségesek, hiszen feleslegesen akar flush-ölni
- `FlushModeType`
  - `AUTO` - persistence providerre bízva - ez az alapártelmezett, query előtt flush-ol
  - `COMMIT` - query előtt nem kell flush, csak a commit előtt
- `entityManager.setFlushMode(FlushModeType.COMMIT);`
- A Hibernate natívan használva az `AUTO` esetén optimalizál, ugyanis megvizsgálja, hogy a query-ben milyen entitások vannak, és csak akkor hív flush-t, ha egyezés van
  - Native query előtt nem flush-öl, hiszen nem parse-olja be
  - Query-n vagy `setFlushMode(FlushMode.ALWAYS)`, vagy Hibernate specific `.addSynchronizedEntityClass()` v. `.addSynchronizedQuerySpace()` metódussal meg lehet mondani, hogy mely entitást érint
- Lekérdezéseket érdemes a módosítások elé, illetve leghátra rendezni, mivel a commitnál úgyis kell flush

---

# Mikor kell explicit `flush()` hívás

- Már most meg akarom tudni, hogy nincs constraint sértés (pl. unique)
- Batch műveleteknél bizonyos időközönként: memória felszabadítás
- Natív query

---

# Példa

```java
var employee = new Employee("John");
repository.save(employee); // IDENTITY esetén mindenképp megy insert/flush

employee.setName("Jack"); // ez csak persistence contextben változik
// repository.flush(); // ha ez nincs, akkor John az eredmény, amúgy Jack

List<String> names = jdbcTemplate.query("select emp_name from employees", (rs, rowNum) -> rs.getString("emp_name"));
System.out.println("Names:" + names);
```

---

# Detach/merge

- Detach: entitás nincs a persistence contexthez rendelve
  - Amikor a persistence context bezárásra kerül
  - entity manager `clear()` metódus
  - Explicit detach, entity manager `detache()`
- Merge: persistence contexthez rendelés
  - Újra példányosított, de létező entitás visszacsatolása
  - Detach-elt entitás visszacsatolása
  - Id alapján
  - Nem a paramétert csatolja vissza, hanem visszatér egy új példánnyal

---

# Change tracking (EclipseLink)

- `@ChangeTracking` annotációval
- Deferred Change Detection Policy (`ChangeTrackingType.DEFERRED`)
  - alapbeállítás, készít egy másolatot, és mentéskor összehasonlítja attribútumonként a mentendőt és a másolatot
- Object-Level Change Tracking Policy (`ChangeTrackingType.OBJECT`)
  - objektum szinten menti, hogy történt-e módosítás, és csak ekkor ellenőrzi
  - weaving, nem működik reflection esetén
  - sok módosult attribútum estén
- Attribute Change Tracking Policy (`ChangeTrackingType.ATTRIBUTE`)
  - sok attribútumból csak kevés módosul

---

class: inverse, center, middle

# Basic lazy betöltés

---

# Basic lazy betöltés

- Weavinggel

```xml
<enableLazyInitialization>true</enableLazyInitialization>
```

```java
@Basic(fetch = FetchType.LAZY)
@Lob
private String description;
```

- Csak a `@Lob` értékkel együtt
- Helyette használható lazy `@OneToOne` kapcsolat

---

class: inverse, center, middle

# Batch

---

# Batch

- Ha beszúrok egyszerre több entitást, összegyűjti és commitban batch-ben szúrja be
- Hibernate-ben alapesetben kikapcsolva

```conf
hibernate.jdbc.batch_size=5
```

```java
entityManager.unwrap(Session.class).setJdbcBatchSize(5);
```

- `GenerationType.IDENTITY` esetén nem működik a batch insert

---

# Batch cascade insert esetén

- Ha szülő és gyermek rekordot is szúrok be, akkor nem tud batch műveletet végezni, mert felváltva insertál a táblákba

```conf
hibernate.order_inserts
```

- Update esetén is

```conf
hibernate.order_updates
```

- Delete esetén nincs ilyen
  - Workaround 1.: gyermek törlése, majd flush()
  - Workaround 2.: batch operation
  - Workaround 3.: database level cascading (`FOREIGN KEY ON DELETE CASCADE`)

---

# Batch update esetén

- Ha két különböző property-t update-elek, akkor is batch-be gyújti
- Ekkor viszont átküldi az összes (nem update-olt mezőt is újra)
  - Hálózat
  - Undo, redo log méret
  - Trigger

```java
@DynamicUpdate
```

Nem fűzi batch-be őket

# Merge batch esetén

- One-to-many esetén a merge visszaolvassa az entitásokat N+1-ben, és arra update-el rá
- Ezért nem hatékony batch esetén

---

class: inverse, center, middle

# Tömeges műveletek

---

# Tömeges (bulk) műveletek

- Átugorják a persistence contextet, direkt az adatbázison hajtódik végre
- Saját tranzakciója legyen, vagy a tranzakció elején fusson
- Shared cache megfelelő részét invalidálja a provider (performancia problémák jelentkezhetnek)
- Nem követi a kaszkádolt kapcsolatokat (bulk művelettel kell törölni azokat is, pl. `IN` klauza)
- Probléma a lockkal és verziózással (optimistic lock)

---

class: inverse, center, middle

# Query hintek

---

# Query hintek

- Szöveges név, objektum érték
- Új funkciók bevezetése új API nélkül
- Két fajtája
  - Szabványos, pl. query timeout
  - Gyártófüggő

---

class: inverse, center, middle

# Refresh

---

# Refresh

- Ha nem az entity manageren keresztül változott az adatbázis
- Hosszan megnyitott persistence contextnél érdekes
- `refresh()` metódus
- Managednek kell lennie az entitásnak
- Addigi változtatások elvesznek

---

class: inverse, center, middle

# Lock

---

# Lock

- Adatok konzisztenciájának megtartása
- Konkurrens hozzáférés
- Később jön, nyer

---

# Optimistic locking

- Olvasásnál nem biztos, hogy visszaírásra kerül, vagy ritka konkurrens módosítás
- Íráskor kell ellenőrizni, hogy történt-e az entitáson módosítás
  - Ha igen, rollback, és `OptimisticLockException`
  - Egész v. timestamp (lehetőleg egészt használjunk)
- Nagy terhelés esetén sok rollback
- `@Version` annotációval ellátott mező
  - Módosításkor ez is módosul
  - Nem minden provider követeli meg (saját cache vagy speciális SQL)
  - Adott tranzakción belül véd

---

# Optimistic lock példa

Spring Data JPA-val nem megy, mert két session kell

```java
var em1 = entityManagerFactory.createEntityManager();
var em2 = entityManagerFactory.createEntityManager();

em1.getTransaction().begin();
var employee = new Employee("John");
em1.persist(employee);
em1.getTransaction().commit();

// Két külön session lekéri ugyanazt az entitást
var e1 = em1.find(Employee.class, employee.getId());
var e2 = em2.find(Employee.class, employee.getId());

// Mindkettő módosítja
em1.getTransaction().begin();
e1.setName("Jack");
em1.getTransaction().commit();
System.out.println("em1 commit done: " + e1.getName());

em2.getTransaction().begin();
e2.setName("Jim");
try {
    em2.getTransaction().commit(); // 💥 OptimisticLockException várható
} catch (RollbackException ex) {
    System.err.println("Optimistic lock exception occurred: " + ex.getCause());
    em2.getTransaction().rollback();
}

em1.close();
em2.close();
```

---

# Optimistic locking felhasználói várakozással

- Service-ben össze kell hasonlítanunk a verziót, és nekünk `OptimisticLockException` kivételt dobni
- REST-en 409 - Conflict

```java
if (employee.getVersion() != employeeModel.getVersion()) {
    throw new OptimisticLockException("The employee has been modified by another user.");
}
```

---

# Advanced optimistic locking

- `LockModeType`
  - `OPTIMISTIC` - optimistic read lock (régen `READ`)
  - `OPTIMISTIC_FORCE_INCREMENT` - optimistic write lock (régen `WRITE`), kikényszeríti a verziónövelést, akkor is, ha nincs adatmódosítás: pl. kapcsolódó entitás módosul, de a fő entitásnak is növekedjen a verziója

---

# Pessimistic locking

- JPA 2.0 vezette be
- Az adott entitást szinkron módon zárolja
- Javasolt gyakori módosításkor
- `LockModeType`
  - `PESSIMISTIC_READ`: más tranzakciók nem módosíthatják, de olvashatják
  - `PESSIMISTIC_WRITE`: más tranzakciók sem olvasni, sem módosítani nem tudják
  - `PESSIMISTIC_FORCE_INCREMENT`: mint a `PESSIMISTIC_WRITE`, de még verziót is növel

---

# Locking API

- `EntityManager`
  - `lock(Object entity, LockModeType lockMode)`
  - `find(Class entityType,Object id, LockModeType lockMode)`
  - `refresh(Object entity, LockModeType lockMode)`
- `Query.setLockMode(LockModeType lockMode)`
- `javax.persistence.lock.timeout` legtöbb provider támogatja

---

# Lock Spring Data JPA-ban

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select e from Employee e where e.id = :id")
Optional<Employee> findByIdForUpdate(Long id);
```

---

class: inverse, center, middle

# Cache

---

# Cache szintjei

- Kétszintű
  - Persistence context, Entity manager szinten (first level)
  - Shared cached, Entity manager factory szinten (nem a legjobb elnevezés a second level)

---

# Shared cache

- `Cache` interface
  - `contains()`
  - evolve metódusok
- `EntityManagerFactory.getCache()`

---

# Deklaratív cache

- Persistence unit szinten
- Entitás szinten
- `javax.persistence.sharedCache.mode`
  - `NOT_SPECIFIED` - persistence provider dönt
  - `ALL`
  - `NONE`
  - `DISABLE_SELECTIVE`, `@Cacheable(false)`
  - `ENABLE_SELECTIVE`, `@Cacheable(true)`

---

# Dynamic cache management

- `find()` metódus vagy query hint (shared cache-re van hatással, nem a persistence contextre)
- `javax.persistence.cache.retrieveMode` és `javax.persistence.cache.storeMode` paraméter
- `CacheRetrieveMode` és `CacheStoreMode`

---

# Típusok

- `CacheRetrieveMode`
  - `USE` cache-ből olvas
  - `BYPASS` cache megkerülésével mindig adatbázisból olvas
- `CacheStoreMode`
  - `USE` cache-be beteszi a felolvasott entitásokat
  - `BYPASS` nem teszi cache-be a felolvasott entitásokat
  - `REFRESH` mindig frissíti a cache-t - ha az adatbázist más is írja
- `refresh()` metódusnál is használható a `CacheStoreMode.REFRESH`

---

# Best practices

- Alkalmazás ne módosítsa a cache-t explicit módon
- Cache törlése pl. automatizált tesztelésnél
- Több kliens módosítja az adatbázist
  - Nem jó megoldás a cache kikapcsolása
  - Helyette: lock, refresh

### Spring Boot Hibernate Second level cache

[Hibernate User Guide - Caching](https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#caching)

```sh
docker run -d -p 6379:6379 --name employees-redis redis
```

```xml
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-hibernate-6</artifactId>
    <version>3.39.0</version>
</dependency>
```

`application.properties`

```conf
spring.jpa.properties.hibernate.cache.use_query_cache=true
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.factory_class=org.redisson.hibernate.RedissonRegionFactory
spring.jpa.properties.hibernate.redisson.fallback=true
spring.jpa.properties.hibernate.redisson.config=redisson.yaml
```

`src/main/resources/redisson.yaml`

```yaml
singleServerConfig:
  address: "redis://localhost:6379"
```

````java
@org.hibernate.annotations.Cache(region = "employeeCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {
    // ...
}

```java
@QueryHints(
        @QueryHint(name = "org.hibernate.cacheable", value = "true")
    )
List<EmployeeResource> findAllResources();
````

IDEA DataSource

---

class: inverse, center, middle

# Adatbázis audit

---

# Adatbázis audit

- History vagy time variant data
- Adatbázis szinten pl. triggerekkel
- Entity listener
- Spring Data JPA [Auditing](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing), csak az aktuális entitáshoz hozzáfűzött információk
- Hibernate Envers
- EclipseLink [History](https://wiki.eclipse.org/EclipseLink/Examples/JPA/History)
- Javasolt megoldás:
  - Nem hisztorizált fej
  - Hisztorizált gyermek entitások (együtt változó attribútumok egyben)

---

class: inverse, center, middle

# SQL lekérdezések

---

# SQL lekérdezések

- Native query
- Adatbázis által nyújtott plusz funkcionalitás kihasználása
- Általa visszaadott entitások managed státuszúak
  - Vigyázni kell, hogy minden mezőt kérdezzünk le, különben üresen kerül vissza

---

class: inverse, center, middle

# Legjobb gyakorlatok

---

# Séma generálás

- Ne a JPA provider generálja a sémákat
- Flyway, Liquibase

---

# Séma verziózás eszköz tulajdonságok

- SQL/XML leírás
- Platform függetlenség
- Lightweight
- Visszaállás korábbi verzióra
- Indítás paranccssorból, alkalmazásból
- Cluster támogatás
- Placeholder támogatás
- Modularizáció
- Több séma támogatása
- Metadata table alapján

---

# Query best practices 1.

- Named query-k használata
  - Precompile, indításkor, gyorsabb
  - XML-ben felülbírálhatóak akár native query-vel
- Paraméterek használata
  - Gyors, biztonságos
- Report query
  - Tranzakción kívül
  - Csak amire szükség van: projection query

---

# Query best practices 2.

- Nem kell félni a gyártófüggő hintektől
- Folyamatosan figyelni a generált SQL utasításokat
- Lapozás

---

# Staging

- Native query-vel létrehozhatóak olyan entitások, melyek más táblához vannak rendelve
- Temporális táblából adatbetöltés

---

class: inverse, center, middle

# EclipseLink specialitások

---

# Read-only query

- `eclipselink.read-only` hint
- A visszaadott entitások nem managed státuszúak, nincs change tracking
- Pl. migrációnál, riportnál

---

# Join fetch

- Kapcsolt entitásokat egy query-ben kéri le
- `@JoinFetch` annotáció
- `eclipselink.join-fetch` hint

```java
@Entity public class Employee implements Serializable {

  ...

  @OneToMany(cascade=ALL, mappedBy="owner")
  @JoinFetch(value=OUTER)
  public Collection<Employee> getManagedEmployees() {
    return managedEmployees;
  }

  ...
}
```

---

# Batch reading

- `eclipselink.batch`
- A kapcsolt entitásokat külön query-ben kéri le
- Nem joinolja be az egész eredeti entitást, azt külön kéri le, így nincs adatduplikáció
- Típusai:
  - `JOIN` - csak az id az eredeti entitásból
  - `EXISTS` - allekérdezéssel
  - `IN` - id-k in paraméterként

[Batch fetching - optimizing object graph loading ](http://java-persistence-performance.blogspot.hu/2010/08/batch-fetching-optimizing-object-graph.html)

---

# Fetch size

- `eclipselink.jdbc.fetch-size` hint
- Egyszerre mennyi sort hozzon át a hálózaton
- Sok sor esetén
- Mindkét irányban eltérő sorok száma esetén a beállítása teljesítménycsökkenést okozhat

---

# Batch writing

- `eclipselink.jdbc.batch-writing` pu property
- Értékként csak a `JDBC` használatos
- `eclipselink.jdbc.batch-writing.size`, alapértelmezetten 100
- Paraméterezett SQL
- Előkészíti az insert és update műveleteket, utána egyben küldi

[Batch Writing, and Dynamic vs Parametrized SQL, how well does your database perform?](http://java-persistence-performance.blogspot.hu/2013/05/batch-writing-and-dynamic-vs.html)

---

# Query cache

- Shared cache csak id alapján tud lekérdezni a cache-ből
- A lekérdezés visszatérési értékét is tudja cache-elni
- `eclipselink.query-results-cache` query hint

---

# Esettanulmány

[How to improve JPA performance by 1,825%](http://java-persistence-performance.blogspot.hu/2011/06/how-to-improve-jpa-performance-by-1825.html)
