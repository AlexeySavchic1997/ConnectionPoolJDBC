package by.alexeysavchic.dto;

import java.util.UUID;

public record Department(
        UUID uuid,
        String departmentName
) {
}
