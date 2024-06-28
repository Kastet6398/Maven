package org.hillel.homework.main;

import lombok.Getter;
import lombok.Setter;
import org.hillel.homework.db.dao.AppStoreDao;
import org.hillel.homework.entity.AppEntity;
import org.hillel.homework.exception.DuplicateException;
import org.hillel.homework.exception.NotFoundException;
import org.hillel.homework.constant.Constant;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class AppStoreApp implements App {
    private static final SimpleDateFormat releaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Scanner scanner = new Scanner(System.in);
    private AppStoreDao dao;

    public AppStoreApp(AppStoreDao dao) {
        this.dao = dao;
    }

    @Override
    public void start() {
        try {
            dao.createTable();
        } catch (SQLException e) {
            display(Constant.ERROR);
            return;
        }
        int operation;

        while ((operation = menu()) != 8) {
            switch (operation) {
                case -1:
                    continue;
                case 1:
                    add();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    filterByPrice();
                    break;
                case 5:
                    filterByType();
                    break;
                case 6:
                    sortByCreationDate();
                    break;
                case 7:
                    listApps();
                    break;
            }
        }

        display(Constant.FAREWELL);
    }

    @Override
    public void interrupt() {
        display(Constant.INTERRUPTED);
        System.exit(1);
    }

    public void sortByCreationDate() {
        try {
            List<AppEntity> apps = dao.sortByCreationDate();
            if (apps.isEmpty()) {
                display(Constant.NO_APPS);
            } else {
                for (AppEntity app : apps) {
                    display(app);
                }
            }
        } catch (SQLException e) {
            display(Constant.ERROR);
        }
    }

    public void listApps() {
        try {
            List<AppEntity> apps = dao.fetchAll();
            if (apps.isEmpty()) {
                display(Constant.NO_APPS);
            } else {
                for (AppEntity app : apps) {
                    display(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            display(Constant.ERROR);
        }
    }

    public void searchByName() {
        display(Constant.NAME_PROMPT, false);
        String name = input();
        try {
            display(dao.getByName(name));
        } catch (SQLException e) {
            display(Constant.ERROR);
        } catch (NotFoundException e) {
            display(Constant.NOT_FOUND);
        }
    }

    public void filterByType() {
        display(Constant.TYPE_PROMPT, false);
        String type = input();

        try {
            List<AppEntity> apps = dao.filterByType(type);
            if (apps.isEmpty()) {
                display(Constant.NO_APPS);
            } else {
                for (AppEntity app : apps) {
                    display(app);
                }
            }
        } catch (SQLException e) {
            display(Constant.ERROR);
        }
    }

    public void filterByPrice() {
        double cost;
        while (true) {
            display(Constant.COST_PROMPT, false);
            try {
                cost = Double.parseDouble(input());
                if (cost < 0.01) {
                    display(Constant.TRY_AGAIN);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                display(Constant.TRY_AGAIN);
            }
        }

        try {
            List<AppEntity> apps = dao.filterByPrice(cost);
            if (apps.isEmpty()) {
                display(Constant.NO_APPS);
            } else {
                for (AppEntity app : apps) {
                    display(app);
                }
            }
        } catch (SQLException e) {
            display(Constant.ERROR);
        }
    }

    public int menu() {
        display(Constant.MENU);
        display(Constant.OPERATION_ID_PROMPT, false);
        try {
            return Integer.parseInt(input());
        } catch (NumberFormatException ignored) {
            display(Constant.TRY_AGAIN);
            return -1;
        }
    }

    public void add() {
        display(Constant.NAME_PROMPT, false);
        String name = input();
        try {
            dao.getByName(name);
            display(Constant.DUPLICATE);
        } catch (SQLException e) {
            display(Constant.ERROR);
        } catch (NotFoundException e) {
            double rating;
            while (true) {
                display(Constant.RATING_PROMPT, false);
                try {
                    rating = Double.parseDouble(input());
                    break;
                } catch (NumberFormatException ignored) {
                    display(Constant.TRY_AGAIN);
                }
            }
            double cost;
            while (true) {
                display(Constant.COST_PROMPT, false);
                try {
                    cost = Double.parseDouble(input());
                    break;
                } catch (NumberFormatException ignored) {
                    display(Constant.TRY_AGAIN);
                }
            }

            display(Constant.DESCRIPTION_PROMPT, false);
            String description = input();
            display(Constant.TYPE_PROMPT, false);
            String type = input();
            Date releaseDate;
            while (true) {
                display(Constant.RELEASE_DATE_PROMPT, false);
                try {
                    releaseDate = Date.valueOf(LocalDate.parse(input()));
                    break;
                } catch (DateTimeParseException e1) {
                    display(Constant.TRY_AGAIN);
                }
            }
            try {
                dao.create(new AppEntity(name, releaseDate, rating, cost, description, null, type, -1), false);
            } catch (SQLException e1) {
                display(Constant.ERROR);
            } catch (DuplicateException e1) {
                display(Constant.DUPLICATE);
            }
        }
    }

    public void remove() {
        long id;
        while (true) {
            display(Constant.ID_PROMPT, false);
            try {
                id = Long.parseLong(input());
                if (id < 1) {
                    display(Constant.TRY_AGAIN);
                } else {
                    break;
                }
            } catch (NumberFormatException ignored) {
                display(Constant.TRY_AGAIN);
            }
        }

        try {
            try {
                dao.delete(id);
            } catch (NotFoundException e) {
                display(Constant.NOT_FOUND);
            }
        } catch (SQLException e) {
            display(Constant.ERROR);
        }
    }

    public void display(Object o, boolean isNewline) {
        System.out.print(o);

        if (isNewline) {
            System.out.println();
        }
    }

    public void display(Object o) {
        display(o, true);
    }

    public String input() {
        return getScanner().nextLine();
    }
}
