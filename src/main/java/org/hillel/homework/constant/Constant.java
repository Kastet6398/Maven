package org.hillel.homework.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constant {
    FAREWELL("Bye!"),
    NAME_PROMPT("Please provide name: "),
    COST_PROMPT("Please provide cost: "),
    TRY_AGAIN("Please provide a valid value"),
    DESCRIPTION_PROMPT("Please provide description: "),
    ERROR("Something went wrong"),
    MENU("""
            ----------- MENU -----------
            [ 1. Add                   ]
            [ 2. Remove                ]
            [ 3. Search by name        ]
            [ 4. Filter by price       ]
            [ 5. Filter by type        ]
            [ 6. Sort by creation date ]
            [ 7. Show app list         ]
            [ 8. Exit                  ]
            """),
    OPERATION_ID_PROMPT("Please provide operation number: "),
    ID_PROMPT("Please provide ID: "),
    TYPE_PROMPT("Please provide type: "),
    NO_APPS("No apps found."),
    RELEASE_DATE_PROMPT("Please provide release date (yyyy-MM-dd): "),
    NOT_FOUND("App not found"),
    DUPLICATE("Name is not unique"),
    INTERRUPTED("Sorry, the app was interrupted"),
    RATING_PROMPT("Please provide rating: "),;

    private String val;

    @Override
    public String toString() {
        return val;
    }
}
