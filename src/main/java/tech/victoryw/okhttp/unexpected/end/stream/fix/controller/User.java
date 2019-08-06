package tech.victoryw.okhttp.unexpected.end.stream.fix.controller;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Data
@NoArgsConstructor
public class User {
    private String name;

    public User(String name) {
        this.setName(name);
    }
}
