Her er en **kort og ryddelig note-version** af øvelsen:

---

## Dolphin JPA Exercise – Noter

### Lombok annotationer

* `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
* `@ToString`, `@EqualsAndHashCode`
* `@ToString.Exclude` (for at undgå cirkulære relationer)
* `@Builder.Default` (nødvendig til collections)

### JPA annotationer

* `@Entity`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
* `@OneToMany(mappedBy="…", cascade=CascadeType.ALL)`
* `@ManyToOne`, `@JoinColumn`
* Husk `HashSet<>` til `@OneToMany`

### Eksempel – Dolphin (One side)

```java
@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode
public class Dolphin {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "dolphin", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private Set<Fee> fees = new HashSet<>();

  public void addFee(Fee fee) {
    this.fees.add(fee);
    if (fee != null) fee.setDolphin(this);
  }
}
```

### Eksempel – Fee (Many side)

```java
@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode
public class Fee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String type;
  private double amount;

  @ManyToOne
  @JoinColumn(name = "dolphin_id")
  private Dolphin dolphin;
}
```

### Brug

```java
Dolphin d = Dolphin.builder().name("Flipper").build();
Fee f1 = Fee.builder().type("Membership").amount(100).build();
Fee f2 = Fee.builder().type("Training").amount(250).build();

d.addFee(f1);
d.addFee(f2);

entityManager.persist(d); // Fees følger med pga. CascadeType.ALL
```

### Hibernate config

```xml
<mapping class="com.example.Dolphin"/>
<mapping class="com.example.Fee"/>
```

### config.properties

```
DB_NAME=dolphin
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

---
