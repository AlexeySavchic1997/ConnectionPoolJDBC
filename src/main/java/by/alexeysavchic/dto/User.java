package by.alexeysavchic.dto;

import java.util.UUID;

public record User(
        UUID id,
        String name,
        String email,
        Integer counter,
        UUID department_id
) {
}
