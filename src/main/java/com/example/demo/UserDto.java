
package com.example.demo;

//  Это наш DTO - то, что увидит клиент
// Никакой связи с БД, просто конверт для данных
public class UserDto {

    // 🎯 Только те поля, которые МОЖНО показывать клиенту
    private Long id;
    private String name;
    private String email;
    private Integer age;
    // ❗ createdAt нет - клиент его не увидит!

    // ⚙️ Конструктор без параметров (требуется для Spring)
    public UserDto() {
    }

    // ⚙️ Конструктор для удобного создания DTO из User
    public UserDto(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // 🚪 Геттеры - через них Spring берет данные для JSON
    // Только для полей, которые хотим показать!

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    // Сеттеры ?


}