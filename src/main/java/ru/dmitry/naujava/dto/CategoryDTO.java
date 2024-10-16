package ru.dmitry.naujava.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO категории
 */
@Schema(description = "сущность категории")
public record CategoryDTO(
        @Schema(description = "Идентификатор.") long id,
        @Schema(description = "Название. Уникально в рамках одного пользователя.") String name,
        @Schema(description = "Описание категории.") String description,
        @Schema(description = "Идентификатор категории, куда вложена данная категория." +
                " Если нужно создать корневой, передать *null*.", nullable = true) long parentId,
        @Schema(description = "Идентификатор пользователя, который создает категорию.") long userId) {
}