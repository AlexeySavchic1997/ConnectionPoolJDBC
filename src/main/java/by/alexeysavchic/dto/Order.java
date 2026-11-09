package by.alexeysavchic.dto;

import java.util.UUID;

public record Order(UUID id,
                    String name,
                    Integer summaryCost
) {
}
