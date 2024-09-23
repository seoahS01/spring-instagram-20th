# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

## DB 모델링

<img width="973" alt="스크린샷 2024-09-23 오전 10 00 41" src="https://github.com/user-attachments/assets/16eb74ea-0603-469f-adc4-9f8fc935a89d">

User
- User - Post: 한 명의 유저는 여러 개의 게시글을 작성할 수 있음
- User - Comment: 한 명의 유저는 여러 개의 댓글을 작성할 수 있음
- User - Like: 한 명의 유저는 여러 개의 게시글에 좋아요를 누를 수 있음
- User - DirectMessage: 한 명의 유저는 여러 개의 DM을 보낼 수 있음

Post
- Post - User: 여러 개의 게시글이 하나의 유저에 연결됨 (N:1 관계)
- Post - Comment: 하나의 게시글에는 여러 개의 댓글이 달릴 수 있음
- Post - Like: 하나의 게시글에는 여러 명의 유저가 좋아요를 누를 수 있

Comment
- Comment - Post: 여러 개의 댓글이 하나의 게시글에 달림 (N:1 관계)
- Comment - User: 여러 개의 댓글이 하나의 유저에 연결됨
- Comment - Comment: 하나의 댓글이 다른 댓글의 대댓글이 될 수 잇음

Like
- Like - Post: 여러 개의 좋아요가 하나의 게시글에 달림 (N:1 관계)
- Like - User: 여러 개의 좋아요가 하나의 유저에 연결됨

DirectMessage
- DM - User: 여러 개의 메시지가 하나의 유저에 연결됨

## JPA
자바에서 관계형 데이터베이스를 다루기 위한 표준 인터페이스
데이터베이스와 자바 객체 간의 변환을 도와주는 역할
- Entity
  
    데이터베이스 테이블과 매핑되는 클래스 e.g. User 라는 클래스가 있으면, 이는 데이터베이스의 user 테이블과 연결됨
- Repository
    
    JPA에서 제공하는 인터페이스를 상속받아 데이터베이스에 접근하는 메소드를 자동으로 생성
- JPQL (Java Persistence Query Language)
    
    데이터베이스 테이블이 아닌 자바 객체를 대상으로 쿼리 작성

## Entity마다 클래스 생성 + 필요한 필드 추가

- @Entity

  해당 클래스가 JPA 엔티티임을 명시

  @Entity가 붙은 클래스는 반드시 기본 생성자를 가져야 하며, 기본 키를 하나 이상 정의해야함
  ```java
  @Entity
  public class User{
  }
  ```
  
- @Table

  Entity 클래스가 매핑될 데이터베이스 테이블 이름을 지정

  기본적으로는 클래스 이름이 테이블 이름으로 사용되지만, 특정 테이블 이름을 명시하려면 @Table을 사용
  ```java
    @Entity
    @Table(name = "users") //매핑할 테이블 이름을 "users"로 지정
    public class User{
    }
    ```
  
- @Id
  
  해당 필드가 기본 키 역할을 한다는 것을 명시
  ```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  ```
  
- @GeneratedValue
  
  기본 키값을 자동으로 생성하는 방식을 지정하는 어노테이션

  - GenerationType.IDENTITY
    
    기본 키 생성을 데이터베이스에 맡김. 주로 MySQL에서 자동 증가 방식으로 기본 키를 생성
  - GenerationType.SEQUENCE
    
    시퀀스를 사용하여 기본 키를 생성
  - GenerationType.TABLE
    
    별도의 테이블을 이용해 기본 키를 생성
  - GenerationType.AUTO
  
    데이터베이스에 맞춰 자동으로 생성 전략을 선택

- @Column
  
  Entity의 필드가 데이터베이스 테이블의 열과 매핑됨을 명시
  
  - name: 매핑될 열의 이름을 지정
  - nullable: true일 경우 해당 열에 null 값이 허용됨. 기본값은 true
  - length: 문자열 필드의 길이를 지정
  - unique: true일 경우 해당 컬럼이 unique key 제약 조건을 가짐
  ```java
  @Column(name = "user_name", nullable = false, length = 50)
  private String userName;
  ```

- @NotNull

  null 값이 허용되지 않음을 명시

  데이터베이스 수준에서 nullanle = false 와 같은 역할을 함
  ```java
  @NotNull
  @Column(name = "email", nullable = false)
  private String email;
  ```
  
- @Size

  문자열 또는 컬렉션의 최대 및 최소 길이를 지정

  보통 문자열 필드에 사용해 입력 값의 길이를 제한하는 데 사용
  ```java
  @Size(min = 2, max = 50)
  @Column(name = "user_name", nullable = false)
  private String userName;
  ```
  
- @Enumerated
  
  enum 타입을 매핑하는 데 사용됨
  
  - EnumType.ORDINAL: enum 값의 순서(숫자)를 지정
  - EnumType.STRING: (권장) enum의 이름을 저장
  ```java
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;
  ```
  
- @ManyToOne
  
  N:1 관계
  
  - fetch: 연관된 엔티티를 어떻게 가져올지 지정
  
    FetchType.LAZY(지연로딩) 혹은 FetchType.EAGER(즉시 로딩)으로 설정

  ```java
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;
  ```
    
- @JoinColumn

  외래키를 설정
  ```java
    @ManyToOne
    @JoinColumn(name = "user_id")  // 외래 키 설정
    private User user;
    ```


## JPA Mapping
객체와 관계형 데이터베이스의 데이터를 매핑하는 작업을 의미

### 기본 엔티티 매핑

- @Entity
- @Table

### 필드 매핑

JPA는 엔티티 클래스의 필드를 데이터베이스 테이블의 컬럼에 매핑

이때, JPA는 기본적으로 필드의 이름을 데이터베이스 컬럼 이름으로 사용하지만, 이를 직접 제어할 수 있음

- @Id
- @GeneratedValue
- @Column
- @Enumerated

### 관계 매핑(Association Mapping)
테이블 간의 관계를 정의하는 외래 키를 사용

- @ManyToOne (N:1)
- @OneToMany(1)
- @ManyToMany (N)
- @OneToOne (1:1)

### Cascade (영속성 전이)
엔티티가 저장, 수정, 삭제될 때 그와 연관된 엔티티도 자동으로 같은 작업을 수행하는 기능

- CascadeType.PERSIST: 엔티티 저장 시 연관된 엔티티도 함께 저장
- CascadeType.MERGE: 엔티티 병합 시 연관된 엔티티도 함께 병합
- CascadeType.REMOVE: 엔티티 삭제 시 연관된 엔티티도 함께 삭제
- CascadeType.ALL: 모든 작업에 대해 전이 적용

```java
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Post> posts;
```

## Repository
Spring Data JPA를 사용해 데이터베이스와 상호작용하는 역할을 함

각 Repository는 특정 엔티틸르 기반으로 데이터베이스에서 CRUD 작업을 처리하는 기능을 제공

Spring Data JPA는 메서드 이름만으로 조회, 조건 검색, 페이징 등의 기능을 제공할 수 있음



    
